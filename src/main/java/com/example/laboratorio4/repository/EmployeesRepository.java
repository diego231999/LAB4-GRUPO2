package com.example.laboratorio4.repository;

import com.example.laboratorio4.dto.EmpDepdto;
import com.example.laboratorio4.dto.EmpMaxSaldto;
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

    @Query(value = "select e.first_name, e.last_name, DATE_FORMAT(h.start_date, '%d-%m-%Y') as 'start_date', DATE_FORMAT(h.end_date, '%d-%m-%Y') as 'end_date', j.job_title, e.salary\n" +
            "from employees e, job_history h, jobs j\n" +
            "where h.employee_id = e.employee_id and e.job_id = j.job_id and e.salary > 8000;\n", nativeQuery = true)
    List<EmpMaxSaldto> listaEmpleadosMaxSalario();

    @Query(value = "select e.first_name, e.last_name, DATE_FORMAT(e.hire_date, '%d-%m-%Y') as 'start_date', DATE_FORMAT(jh.end_date, '%d-%m-%Y') as 'end_date', j.job_title\n" +
            "from employees e\n" +
            "inner join job_history jh on (e.employee_id=jh.employee_id)\n" +
            "inner join jobs j on (e.job_id=j.job_id)\n" +
            "where salary = ?1 ", nativeQuery = true)
    List<EmpMaxSaldto> listaEmpleadosMaxSalarioEncs(Double salary);

}
