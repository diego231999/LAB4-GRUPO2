package com.example.laboratorio4.repository;

import com.example.laboratorio4.dto.SalMaxdto;
import com.example.laboratorio4.entity.Departments;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments,Integer> {

    @Query(value = "select d.department_id, d.department_name, \n" +
            "    truncate(avg(e.salary),0) as 'promedio'\n" +
            "from employees e, departments d\n" +
            "where e.department_id = d.department_id\n" +
            "group by d.department_id;", nativeQuery = true)
    List<SalMaxdto> reporteSalarioPromedio();
}
