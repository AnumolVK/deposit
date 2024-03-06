package com.svadhan.deposit.service;

import com.svadhan.deposit.model.DepositDetailsRequest;
import com.svadhan.deposit.model.DepositDetailsResponse;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class DepositDetailsService {
    public DepositDetailsResponse saveDepositDetails(DepositDetailsRequest depositDetailsRequest) {
        public CustomerOtherDetailsResponse saveCustomerDetails(CustomerDetailsReq customerDetailsReq) {
            //save Customer Details to Db
            Customer customer = getCustomerById(customerDetailsReq.getCusId());
            CustomerOtherDetails customerOtherDetails = customerOtherDetailsRepository.findByCustomer(customer);
            if (customerOtherDetails == null) {
                customerOtherDetails = new CustomerOtherDetails();
            }
            BeanUtils.copyProperties(customerDetailsReq, customerOtherDetails);
            customerOtherDetails.setCustomer(customer);
            customerOtherDetailsRepository.save(customerOtherDetails);

            /*Calling QleCheck for CUSTOMER*/
            CheckQleResponse checkQleResponse = qleCheckService.getRequestObjectForQleCheckForCustomer(customerDetailsReq, customer);
            /*Setting eligibility for loan and loan amount*/
            if (checkQleResponse.isEligible()) {
                customer.setEligible(true);
                customer.setEligibleLoanAmount(checkQleResponse.getEligibleLoanAmount());
            } else {
                /*Setting ineligibility reason and rejection date time*/
                customer.setRejectionDateTime(LocalDateTime.now());
                customer.setEligible(false);
                customer.setIneligibilityReason(checkQleResponse.getIneligibilityReason());
            }
            customerRepository.save(customer);
            /*Setting current stage of onboarding of a customer*/
            OnBoardingProcessTracker onBoardingProcessTracker;
            if(customer.getOnBoardingProcessTracker()==null){
                onBoardingProcessTracker=new OnBoardingProcessTracker();
                onBoardingProcessTracker.setCustomer(customer);
                onBoardingProcessTracker.setCurrentStage(OnBoardingProcessStage.UPDATE_OCR);
            }else{
                onBoardingProcessTracker=customer.getOnBoardingProcessTracker();
                onBoardingProcessTracker.setCurrentStage(OnBoardingProcessStage.UPDATE_OCR);
            }
            if(customerDetailsReq.getMaritalStatus() != null){
                if (customerDetailsReq.getMaritalStatus().equals(MaritalStatus.MARRIED)) {
                    onBoardingProcessTracker.setNextStage(OnBoardingProcessStage.UPLOAD_AADHAAR_SPOUSE);
                }
            }
            onBoardingProcessTracker= onBoardingProcessTrackerRepository.save(onBoardingProcessTracker);

            /*For non-married customer(DIVORCEE,WIDOW) this will be the end point for onboarding process*/
            if (customerDetailsReq.getMaritalStatus() != null) {
                if (!customerDetailsReq.getMaritalStatus().equals(MaritalStatus.MARRIED)) {
                    //Set hasRegisterProcessCompleted to true to mark onBoarding process completion and save to DB
                    customer.setRegisterProcessCompleted(true);
                    customerRepository.save(customer);
                    log.info(" Calling 'informAgentAboutCompletionOfCustomerOnboardingProcess()' method from 'saveCustomerDetails()' method ");
                    /*Informing agent-app about completion of onboarding process of customer*/
                    String str = InformAgentAboutCustomerOnboardingCompletionUtil.informAgentAboutCompletionOfCustomerOnboardingProcess(customer, agentAppUrl);
                }
            }
            CustomerOtherDetailsResponse customerOtherDetailsResponse = CustomerOtherDetailsResponse.builder().customerId(customer.getId()).code(200).isEligible(customer.isEligible()).message("Details saved successfully").status(true).build();
            if (customer.getEligibleLoanAmount() != null) {
                customerOtherDetailsResponse.setEligibleLoanAmount(customer.getEligibleLoanAmount());
            }
            return depositDetailsResponse;
        }


}
