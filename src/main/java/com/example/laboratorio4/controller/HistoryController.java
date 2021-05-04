package com.example.laboratorio4.controller;

import com.example.laboratorio4.entity.History;
import com.example.laboratorio4.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/history")
public class HistoryController {
    @Autowired

    HistoryRepository historyRepository;

    @GetMapping(value = {"","/"})
    public String historialEmpleado(Model model){

        model.addAttribute("listaEmpleados", historyRepository.lista_employees_time());
        return "/history/lista";
    }
}
