package com.example.laboratorio4.controller;


import com.example.laboratorio4.entity.Employees;
import com.example.laboratorio4.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/Search")
public class SearchController {

    @Autowired
    EmployeesRepository employeesRepository;

    @GetMapping(value = {"","/"})
    public String indice(){
        return "Search/indice";
    }

    @GetMapping(value = {"/Salario"})
    public String listaEmpleadosMayorSalrio (Model model,
                                             @RequestParam(value = "searchField", defaultValue = "") String searchField){

        BigDecimal salary = BigDecimal.valueOf(Long.parseLong(searchField));


        // if doesnt equals
        //List<Employees> employeesList = employeesRepository.findAllBySalaryEquals(salary);
        List<Employees> employeesList = employeesRepository.findAll();

        for (Employees employees: employeesList){
            System.out.println(employees.getSalary());
        }

      //COMPLETAR
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
    public String cantidadEmpleadosPorPais (){

        //COMPLETAR
        return "/Search/salario";
    }

    @GetMapping("/listar")
    public String listarEmpleadoDep() {
        //COMPLETAR
        return "/Search/lista3";

    }


}
