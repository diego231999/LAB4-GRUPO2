package com.example.laboratorio4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name="employees")
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "employee_id")
    private int employeeid;

    @Size(max = 20, message = "Ingrese como máximo 20 caractéres")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "first_name")
    private String firstname;

    @Column(nullable = false, name = "last_name")
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 25, message = "Ingrese como máximo 25 caractéres")
    private String lastname;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El email no puede estar vacío")
    @Size(max = 25, message = "Ingrese como máximo 25 caractéres")
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "El formato debe ser: nombre@dominio.com")
   // @Email(message = "El formato debe ser: nombre@dominio.com")
    private String email;

    @Size(max = 65, min = 8, message = "Ingrese como máximo 65 caractéres y como mínimo 8 carácteres")
    private String password;

   /* @Size(max = 20, message = "Ingrese como máximo 20 caractéres") */
    @Column(name = "phone_number")
    private String phonenumber;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs jobs;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Departments departments;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employees manager;

    private int enabled;

    @Digits(integer = 8, fraction = 2, message = "Ingrese un valor con 2 decimales y un máximo de 8 digitos")
    @Min(value = 1, message = "Tiene que ser un valor mayor que 0")
    private BigDecimal salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "hire_date")
    private LocalDateTime hiredate;

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Employees getManager() {
        return manager;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Jobs getJobs() {
        return jobs;
    }

    public void setJobs(Jobs jobs) {
        this.jobs = jobs;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDateTime getHiredate() {
        return hiredate;
    }

    public void setHiredate(LocalDateTime hiredate) {
        this.hiredate = hiredate;
    }
}
