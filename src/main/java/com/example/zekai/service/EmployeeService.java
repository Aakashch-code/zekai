package com.example.zekai.service;

import com.example.zekai.entity.Employee;
import com.example.zekai.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> getEmployees(String name, String department, BigDecimal minSalary, BigDecimal maxSalary,
                                       int page, int size, String sortBy, String direction) {

        String searchName = (name != null) ? name : "";
        String searchDept = (department != null) ? department : "";
        BigDecimal searchMin = (minSalary != null) ? minSalary : BigDecimal.ZERO;
        BigDecimal searchMax = (maxSalary != null) ? maxSalary : new BigDecimal("999999999.00");

        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findWithFilters(searchName, searchDept, searchMin, searchMax, pageable);
    }
}