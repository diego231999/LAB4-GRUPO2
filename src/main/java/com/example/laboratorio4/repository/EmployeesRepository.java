package com.example.laboratorio4.repository;

import com.example.laboratorio4.dto.EmpDepdto;
import com.example.laboratorio4.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Integer> {


    List<Employees> findAllBySalaryEqualsAndSalaryGreaterThan(BigDecimal salary, BigDecimal minSalary);
    List<Employees> findAllBySalaryGreaterThan(BigDecimal salary);

    @Query(value = "select e.employee_id, e.first_name, e.last_name, j.job_title, \n" +
            "\t\ttruncate(e.salary,0) as 'salario'\n" +
            "from employees e, jobs j, departments d\n" +
            "where e.job_id = j.job_id and e.department_id = d.department_id and d.department_id = ?1 \n" +
            "order by e.salary DESC;", nativeQuery = true)
    List<EmpDepdto> reporteEmpleadosPorDepartamentos(int id);
}
