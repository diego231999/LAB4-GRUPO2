package com.example.laboratorio4.controller;


import com.example.laboratorio4.entity.Departments;
import com.example.laboratorio4.entity.Employees;
import com.example.laboratorio4.repository.DepartmentsRepository;
import com.example.laboratorio4.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @GetMapping(value = {"","/"})
    public String indice(){
        return "Search/indice";
    }

    @GetMapping(value = {"/Salario"})
    public String listaEmpleadosMayorSalrio (Model model,
                                             @RequestParam(value = "searchField", defaultValue = "") String searchField){

        List<Employees> employeesList;

        if(searchField.isEmpty()){
            employeesList = employeesRepository.findAllBySalaryGreaterThan(BigDecimal.valueOf(8000));
        } else{
            BigDecimal salary = BigDecimal.valueOf(Long.parseLong(searchField));
            employeesList = employeesRepository.findAllBySalaryEqualsAndSalaryGreaterThan(salary, BigDecimal.valueOf(8000));
        }

        model.addAttribute("employeesList", employeesList);

        return "Search/lista2";
    }

    @PostMapping("/busqueda")
    public String buscar (
            @RequestParam(value = "searchField", defaultValue = "") String searchField,
            Model model,
            RedirectAttributes redirectAttributes
    ){

        redirectAttributes.addAttribute("searchField", searchField);
        return "redirect:/Search/Salario";
    }

    @GetMapping(value = "/Filtro2")
    public String cantidadEmpleadosPorPais (Model model){

        model.addAttribute("reporteSalarioPromedio",  departmentsRepository.reporteSalarioPromedio());
        return "/Search/salario";
    }

    @GetMapping("/listar")
    public String listarEmpleadoDep(@RequestParam("id") int id, Model model) {
        model.addAttribute("listaEmpleadosXDep", employeesRepository.reporteEmpleadosPorDepartamentos(id));
        return "/Search/lista3";

    }


}
