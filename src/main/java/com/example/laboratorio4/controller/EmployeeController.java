package com.example.laboratorio4.controller;
import com.example.laboratorio4.entity.Employees;
import com.example.laboratorio4.repository.DepartmentsRepository;
import com.example.laboratorio4.repository.EmployeesRepository;
import com.example.laboratorio4.repository.JobsRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @GetMapping(value = {"","/list"})
    public String listaEmployee(Model model){
        model.addAttribute("listaEmployee", employeesRepository.findAll());
        model.addAttribute("listaJobs", jobsRepository.findAll());
        model.addAttribute("listaDepartments", departmentsRepository.findAll());
        return "employee/lista";
    }

    @GetMapping("/new")
    public String nuevoEmployeeForm(@ModelAttribute("employees")  Employees employees, Model model) {
        model.addAttribute("listaJobs", jobsRepository.findAll());
        model.addAttribute("listaJefes", employeesRepository.findAll());
        model.addAttribute("listaDepartments", departmentsRepository.findAll());
        return "employee/form";
    }

    @PostMapping("/save")
    public String guardarEmployee(@ModelAttribute("employees") @Valid Employees employees, BindingResult bindingResult,
                                  RedirectAttributes attr,
                                  @RequestParam(name="fechaContrato", required=false) String fechaContrato, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaJefes", employeesRepository.findAll());
            model.addAttribute("listaDepartments", departmentsRepository.findAll());
            return "employee/form";
        } else {

            if (employees.getEmployeeid() == 0) {
                attr.addFlashAttribute("msg", "Empleado creado exitosamente");
                employees.setHiredate(LocalDateTime.now());
                employeesRepository.save(employees);
                return "redirect:/employee/list";
            } else {
                /* Hay que validar que se haya cambiado el cargo, para esto se buscará en base al
                id, el cargo del empleado. Por lo que si encuentra su nombre y su cargo es igual al
                mismo que ingresa en el form, la hire_date no cambiara y será la misma. En cambio si no
                quiere decir que cambio de empleo por lo que debera actualizarla
                 */
                Optional<Employees> employeesOptional = employeesRepository.findById(employees.getEmployeeid());

                if(employeesOptional.isPresent()){
                    // Se llama al employee con el que se validará
                    Employees employeesVal = employeesOptional.get();

                    if(employeesVal.getJobs().getJobid().equals(employees.getJobs().getJobid())){
                        employees.setHiredate(employees.getHiredate());
                    }else{
                        employees.setHiredate(LocalDateTime.now());
                    }

                }
               /* try{
                    employeesRepository.save(employees);
                }catch (MysqlDat e){
                    model.addAttribute("msgSal","No puede ingreser un salario mayor a 8 decimales");
                    return "/employee/form";
                }*/

                attr.addFlashAttribute("msg", "Empleado actualizado exitosamente");
                return "redirect:/employee/list";
            }
        }
    }



    @GetMapping("/edit")
    public String editarEmployee(@ModelAttribute("employees") Employees employees,
                                    @RequestParam("id") int id,
                                 Model model) {
        Optional<Employees> employeesOptional = employeesRepository.findById(id);
        if (employeesOptional.isPresent()) {
            employees = employeesOptional.get();
            model.addAttribute("employees", employees);
            model.addAttribute("listaJobs", jobsRepository.findAll());
            model.addAttribute("listaJefes", employeesRepository.findAll());
            model.addAttribute("listaDepartments", departmentsRepository.findAll());
            return "/employee/form";
        } else {
            return "redirect:/employee/list";
        }
    }



    @GetMapping("/delete")
    public String borrarEmpleado(Model model,
                                      @RequestParam("id") int id,
                                      RedirectAttributes attr) {

        Optional<Employees> optEmployees = employeesRepository.findById(id);

        if (optEmployees.isPresent()) {
            employeesRepository.deleteById(id);
            attr.addFlashAttribute("msg","Empleado borrado exitosamente");
        }
        return "redirect:/employee/list";
    }

  /*  @PostMapping("/search")
    public String buscar (){

        //COMPLETAR
    }*/

}
