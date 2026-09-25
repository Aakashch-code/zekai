package com.example.zekai.repository;

import com.example.zekai.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE " +
            "(LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:department = '' OR LOWER(e.department) = LOWER(:department)) AND " +
            "(e.salary >= :minSalary) AND " +
            "(e.salary <= :maxSalary)")
    Page<Employee> findWithFilters(
            @Param("name") String name,
            @Param("department") String department,
            @Param("minSalary") BigDecimal minSalary,
            @Param("maxSalary") BigDecimal maxSalary,
            Pageable pageable
    );
}