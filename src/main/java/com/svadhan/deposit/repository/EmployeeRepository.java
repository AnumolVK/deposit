package com.svadhan.deposit.repository;


import com.svadhan.deposit.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    List<Employee> findAllByAssignedPinCode(String assignedPinCode);
}
