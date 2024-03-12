package com.svadhan.deposit.repository;

import com.svadhan.deposit.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByMobile(String mobNumber);

    List<Customer> findByDeviceId(String deviceId);

    List<Customer> findTop10ByIdInOrderByCreateDateTimeDesc(List<Long> ids);

    @Query(nativeQuery = true, value = "SELECT * FROM (SELECT * FROM CUSTOMER WHERE ID IN (:ids) AND LOWER(NAME) LIKE %:name% ORDER BY CREATED_ON DESC) WHERE ROWNUM <= 10")
    List<Customer> findTop10ByIdInAndNameLikeOrderByCreateDateTimeDesc(@Param("ids") List<Long> ids, @Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM (SELECT * FROM CUSTOMER WHERE ID IN (:ids) AND MOBILE LIKE %:mobile% ORDER BY CREATED_ON DESC) WHERE ROWNUM <= 10")
    List<Customer> findTop10ByIdInAndNumberLikeOrderByCreateDateTimeDesc(@Param("ids") List<Long> ids, @Param("mobile") String mobile);

    List<Customer> findAllByAssociatedAgentIdAndHasRegisterProcessCompleted(Long associatedAgentId, Boolean hasRegisterProcessCompleted);

    @Query(nativeQuery = true, value = "SELECT VILLAGE_NAME FROM VILLAGE_PIN_CODE_LIST vpcl WHERE PIN_CODE IN (SELECT COD.PIN_CODE FROM CUSTOMER_OCR_DATA cod WHERE CUSTOMER_ID = :ids) AND ROWNUM<=1")
    String findVillageNameByCustomerID(@Param("ids") Long ids);

    //    @Query(nativeQuery = true, value = "SELECT SUM(t.AMOUNT)  FROM TRANSACTION t JOIN LOAN l ON l.ID = t.LOAN_ID WHERE l.CUSTOMER_ID = :ids AND TRANSACTION_STATUS_ID = 3 ")
//    Double findAmountByCustomerID(@Param("ids") Long ids);
    @Query(nativeQuery = true, value = "SELECT SUM(t.AMOUNT)  FROM TRANSACTION t JOIN LOAN l ON l.ID = t.LOAN_ID " +
            "WHERE l.CUSTOMER_ID = :ids AND TRANSACTION_STATUS_ID = 3 AND t.PGW_ID IS NULL")
    Double findCollectedAmountByCustomerID(@Param("ids") Long ids);

    @Query(nativeQuery = true, value = "SELECT SUM(t.AMOUNT)  FROM TRANSACTION t JOIN LOAN l ON l.ID = t.LOAN_ID JOIN AGENT_TRANSACTION at2 ON AT2.TRANSACTION_ID = t.ID WHERE l.CUSTOMER_ID = :ids AND TRANSACTION_STATUS_ID = 3 AND AT2.STATUS = 'PROCESSED' ")
    Double findProcessedAmountByCustomerID(@Param("ids") Long ids);

    @Query(nativeQuery = true, value = "SELECT SUM(t.AMOUNT) FROM TRANSACTION t JOIN LOAN l ON l.ID = t.LOAN_ID " +
            "JOIN AGENT_TRANSACTION at2 ON AT2.TRANSACTION_ID = t.ID WHERE TRANSACTION_STATUS_ID = 3 " +
            "AND AT2.STATUS = 'PROCESSED' ")
    Double findTotalProcessedAmountByCustomerID();

    @Query(nativeQuery = true, value = "SELECT SUM(t.AMOUNT) FROM TRANSACTION t JOIN LOAN l ON l.ID = t.LOAN_ID " +
            "JOIN AGENT_TRANSACTION at2 ON AT2.TRANSACTION_ID = t.ID WHERE l.CUSTOMER_ID = :ids " +
            "AND TRANSACTION_STATUS_ID = 3 AND AT2.STATUS = 'PROCESSED' AND TRUNC(t.MODIFIED_ON) = :dt")
    Double findTodaysProcessedAmountByCustomerID(@Param("ids") Long ids, @Param("dt") LocalDate dt);

    @Query(nativeQuery = true, value = "SELECT SUM(t.AMOUNT) FROM TRANSACTION t JOIN LOAN l ON l.ID = t.LOAN_ID " +
            "JOIN AGENT_TRANSACTION at2 ON AT2.TRANSACTION_ID = t.ID WHERE l.CUSTOMER_ID = :ids " +
            "AND TRANSACTION_STATUS_ID = 3 AND NOT AT2.STATUS = 'PROCESSED' AND TRUNC(t.MODIFIED_ON) = TO_DATE(:dt, 'DD-MM-YY')")
    Double findTodaysNonProcessedAmountByCustomerID(@Param("ids") Long ids, @Param("dt") LocalDate dt);


}
