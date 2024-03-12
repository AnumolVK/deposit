package com.svadhan.deposit.service;

import com.svadhan.deposit.banking.entity.Emi;
import com.svadhan.deposit.banking.repository.EmiRepository;
import com.svadhan.deposit.entity.*;
import com.svadhan.deposit.exception.customexception.RequiredEntityNotFoundException;
import com.svadhan.deposit.model.CollectionDetailsResponse;
import com.svadhan.deposit.model.DepositDetailsDTO;
import com.svadhan.deposit.model.DepositRequest;
import com.svadhan.deposit.repository.*;
import com.svadhan.deposit.repository.EmployeeRepository;
import com.svadhan.deposit.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.svadhan.deposit.util.DepositMapper;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.*;

@Service
@Slf4j
public class DepositService{

    private final EmployeeRepository employeeRepository;
    private final CustomerOcrDataRepository customerOcrDataRepository;
    private final LoanRepository loanRepository;
    private final VillagePinCodeRepository villagePinCodeRepository;
    private final EmiRepository emiRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final DepositRepository depositRepository;
    @Value("${svadhan.collection.agent.deposit-time}")
    private Integer agentPaymentPeriod;

    public DepositService(EmployeeRepository employeeRepository, CustomerOcrDataRepository customerOcrDataRepository, LoanRepository loanRepository, VillagePinCodeRepository villagePinCodeRepository, EmiRepository emiRepository, TransactionRepository transactionRepository, CustomerRepository customerRepository,DepositRepository depositRepository) {
        this.employeeRepository = employeeRepository;
        this.customerOcrDataRepository = customerOcrDataRepository;
        this.loanRepository = loanRepository;
        this.villagePinCodeRepository = villagePinCodeRepository;
        this.emiRepository = emiRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.depositRepository=depositRepository;
    }


    public void confirmCollection() {
        //TODO call lender API 8
    }


    public CollectionDetailsResponse getCompletedCollections(Long agentId) {
        CollectionDetailsResponse collectionDetailsResponse = new CollectionDetailsResponse();

        Employee employee = employeeRepository.findById(agentId).orElseThrow(() -> {
            throw new RequiredEntityNotFoundException(String.format("Entity 'Employee' is not found with ID '%d'", agentId));
        });
        List<Loan> loans = this.getAllCustomerAssignedToAgent(employee).stream()
                .map(this::getAllLoans)
                .reduce(new ArrayList<>(), (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
        log.info("loans of the customers : "+loans.size());
        List<Emi> totalAgentCollectedEMIs = loans.stream()
                .map(this::getAllEMIsPaidToAgent)
                .reduce(new ArrayList<>(), (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });
        log.info("Emi for the agent :"+totalAgentCollectedEMIs.size());
        Double totalCollectedAmount = totalAgentCollectedEMIs.stream()
                .map(Emi::getTransactionId)
                .map(transactionId -> {
                    Optional<Transaction> transactionOptional = transactionRepository.findTransactionById(transactionId);
                    if (transactionOptional.isEmpty()) {
                        log.info("Couldn't find a transaction with the Id: " + transactionId);
                        return null;
                    }
                    return transactionOptional.get();
                }).filter(Objects::nonNull)
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
        //Long totalCollectedAmount = totalAgentCollectedEMIs.stream().map(Emi::getDueAmount).reduce(0L, Long::sum);
        Double totalDepositedAmount = this.getTotalDepositedAmount();
        if (totalDepositedAmount == null){
            totalDepositedAmount = 0.0;
        }
        Double depositPendingAmount = totalCollectedAmount - totalDepositedAmount;
        collectionDetailsResponse.setTotalAmountCollected(totalCollectedAmount);
        collectionDetailsResponse.setDepositedSum(totalDepositedAmount);
        collectionDetailsResponse.setDepositPendingSum(depositPendingAmount);
        //TODO: Change the timeLeftToDeposit after implementing collection repayment using Airtel payment bank
        LocalDateTime firstCollectionTime = null;
        for (Emi emi: totalAgentCollectedEMIs) {
//            Optional<Transaction> transactionOptional = transactionRepository.findById(emi.getTransactionId());
            //new change - need to change later
            Optional<Transaction> transactionOptional = transactionRepository.findTransactionById(emi.getTransactionId());
            if (transactionOptional.isEmpty()) continue;
            Transaction transaction = transactionOptional.get();
            log.info("first collection time first :"+transaction.getCreatedOn());
            if (transaction.getCreatedOn() == null) continue;
            if (firstCollectionTime == null) {
                firstCollectionTime = transaction.getCreatedOn();
            }
            else if (firstCollectionTime.isAfter(transaction.getCreatedOn())) {
                firstCollectionTime = transaction.getCreatedOn();
            }
        }

        if (firstCollectionTime != null) {
            LocalTime newTime = LocalTime.of(agentPaymentPeriod, 0); //5.00 pm
            LocalDateTime modifiedDateTime = firstCollectionTime.with(newTime);
            log.info("First transaction date after looping :"+modifiedDateTime);

            Duration duration = Duration.between(LocalDateTime.now(), modifiedDateTime);
            log.info("The Hours remaining :"+duration);
            collectionDetailsResponse.setTimeLeftToDeposit(duration.toHours());
            collectionDetailsResponse.setFirstCollectionTime(modifiedDateTime.toString());
        }

        List<DepositDetailsDTO> depositDetailsDTOList = new ArrayList<>();
        List<DepositDetailsDTO> depositDetailsPendingDTOList = new ArrayList<>();
        List<Customer> customersList = new ArrayList<>();
        customersList = customerRepository.findAllByAssociatedAgentIdAndHasRegisterProcessCompleted(agentId,true);
        log.info("Customer size : {}",customersList.size());
        for (Customer customer : customersList){
            DepositDetailsDTO depositDetailsDTO = new DepositDetailsDTO();
            DepositDetailsDTO depositDetailsPendingDTO = new DepositDetailsDTO();
            log.info("The customer id : "+customer.getId());
            depositDetailsPendingDTO.setCustomerId(customer.getId());
            depositDetailsPendingDTO.setCustomerName(customer.getName());
            depositDetailsPendingDTO.setMobileNumber(customer.getMobile());
            depositDetailsPendingDTO.setVillage(this.getVillage(customer.getId()));
            depositDetailsPendingDTO.setDuePending(this.getDuePending(customer, Optional.empty()));
            depositDetailsPendingDTO.setDueDatePassed(true);
            depositDetailsPendingDTO.setAmount(this.getCollectedAmount(customer.getId()));

            //Deposited
            depositDetailsDTO.setCustomerId(customer.getId());
            depositDetailsDTO.setCustomerName(customer.getName());
            depositDetailsDTO.setMobileNumber(customer.getMobile());
            depositDetailsDTO.setVillage(depositDetailsPendingDTO.getVillage());
            depositDetailsDTO.setDuePending(false); // todo
            depositDetailsDTO.setDueDatePassed(true); //todo



            // Get the current timestamp using Instant
            Instant currentInstant = Instant.ofEpochMilli(DateTimeUtil.getCurrentTimeLong());

// Convert Instant to LocalDate
            LocalDate currentDate = currentInstant.atZone(ZoneId.systemDefault()).toLocalDate();

// Call getTodaysDepositedAmount only once to avoid redundant calls
            Double amount = this.getTodaysDepositedAmount(customer.getId(),LocalDate.now());

// Check if the amount is not null before setting the deposit details
            if (amount != null) {
                depositDetailsDTO.setAmount(amount);
                depositDetailsDTOList.add(depositDetailsDTO);
            }

//
            if (depositDetailsPendingDTO.isDuePending()) {
                depositDetailsPendingDTOList.add(depositDetailsPendingDTO);
            }

        }
        //TODO : Implement
        collectionDetailsResponse.setDepositedDetailsDTOS(depositDetailsDTOList);
        collectionDetailsResponse.setDepositPendingDetailsDTOS(depositDetailsPendingDTOList);
        return collectionDetailsResponse;
    }
    private List<Customer> getAllCustomerAssignedToAgent(Employee employee) {
        Set<String> agentAssignedPinCodes = employee.getAssignedPins();
        if (agentAssignedPinCodes == null || agentAssignedPinCodes.isEmpty()) {
            log.info("There are no pin codes assigned to the agent: " + employee.getId());
            return new ArrayList<>();
        }
        return customerOcrDataRepository.findAllByPinCodeIn(agentAssignedPinCodes.stream().toList()).stream()
                .map(CustomerOcrData::getCustomer).toList();
    }


    private List<Loan> getAllLoans(Customer customer) {
        List<Loan> loans = loanRepository.findAllByCustomer(customer);
        if (loans.isEmpty()) {
            log.info("There are no loans found for the customer: " + customer.getId());
        }
        return loans;
    }

    boolean getDuePending(Customer customer, Optional<LocalDate> optionalDate) {
        // Get the date to use for processing
        LocalDate dateToUse = optionalDate.orElse(LocalDate.now().minusDays(1)); // Use yesterday's date if not provided
        if (!optionalDate.isPresent()) {
            dateToUse = LocalDate.now().minusDays(2); // Use day before yesterday's date
        }

        List<Loan> customerLoans = loanRepository.findAllByCustomer(customer);

        if (CollectionUtils.isEmpty(customerLoans)) {
            return false;
        }

        // Adjust the format pattern to match the date string format
        String dateString = dateToUse.format(DateTimeFormatter.ofPattern("dd-MM-yy"));

        for (Loan loan : customerLoans) {
            List<Emi> emis = emiRepository.findAllByLoanIdOrderByCreatedOnDesc(loan.getId());

            for (Emi emi : emis) {
                if (StringUtils.equalsIgnoreCase(emi.getStatus(), "PAYMENT_DUE")) {
                    return false; // Return false if any payment is due
                } else {
                    try {
                        // Parse the date string using the correct format pattern
                        Double nonProcessedAmount = customerRepository.findTodaysNonProcessedAmountByCustomerID(customer.getId(), LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yy")));

                        if (nonProcessedAmount != null && nonProcessedAmount > 0) {
                            return true; // Return true if there is any non-processed amount
                        }
                    } catch (DateTimeParseException e) {
                        // Handle parsing error
                        System.err.println("Error parsing date: " + e.getMessage());
                        // Add appropriate error handling mechanism here
                    }
                }
            }
        }

        return false; // Return false if no payment is due or non-processed amount is found
    }


    private Double getCollectedAmount(Long customerId) {
        Double amount = customerRepository.findCollectedAmountByCustomerID(customerId);
        if (amount==null || amount==0.0) {
            log.info("The amount is empty for the customer : " + customerId);
        }
        return amount;
    }

    private Double getDepositedAmount(Long customerId) {
        Double amount = customerRepository.findProcessedAmountByCustomerID(customerId);
        if (amount==null || amount==0.0) {
            log.info("The amount is empty for the customer : " + customerId);
        }
        return amount;
    }


    private Double getTodaysDepositedAmount(Long customerId, LocalDate date) {
        try {
            // Your logic to retrieve the deposited amount for the given customer and date
            Double amount = customerRepository.findTodaysProcessedAmountByCustomerID(customerId, date);

            if (amount == null || amount == 0.0) {
                log.info("The amount is empty for the customer: " + customerId);
            }

            return amount;
        } catch (Exception e) {
            log.error("Error retrieving amount for customer " + customerId + " on date " + date, e);
            return null;
        }
    }



    private Double getTotalDepositedAmount() {
        Double amount = customerRepository.findTotalProcessedAmountByCustomerID();
        if (amount==null || amount==0.0) {
            log.info("The amount is empty for all the customer "+amount);
        }
        return amount;
    }

    private String getVillage(Long customerId) {
        String villageName = customerRepository.findVillageNameByCustomerID(customerId);
        if (villageName==null) {
            log.info("The village name is not found for the customer: " + customerId);
        }
        return villageName;
    }

    private List<Emi> getAllEMIsPaidToAgent(Loan loan) {
        return emiRepository.findAllByLoanIdAndStatus(loan.getId(), "PAID").stream()
                .filter(this::isPaidToAgent).toList();
    }

    private boolean isPaidToAgent(Emi emi) {
        if (emi == null) return false;
        Transaction transaction = transactionRepository.findTransactionById(emi.getTransactionId()).orElse(null);
        if (transaction == null) return false;
        return transaction.getPgw() == null;
    }


    @Transactional
    public boolean saveDeposit(DepositRequest request) {
        try {
            // Use DepositMapper to map DepositRequest to Payment entity
            Payment deposit = DepositMapper.mapToPaymentEntity(request);

            // Save the deposit entity to the database
            depositRepository.save(deposit);

            // You can perform additional operations here if needed

            return true; // Return true if the deposit is successfully saved
        } catch (Exception e) {
            // Log error message
            log.error("Error saving deposit: {}", e.getMessage());
            return false; // Return false if an error occurs while saving the deposit
        }
    }




}

