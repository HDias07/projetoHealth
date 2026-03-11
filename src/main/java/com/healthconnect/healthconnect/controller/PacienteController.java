package com.healthconnect.healthconnect.controller;

import com.healthconnect.healthconnect.entity.Paciente;
import com.healthconnect.healthconnect.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository repository;

    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", repository.findAll());
        return "lista-pacientes";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "form-paciente";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Paciente paciente, BindingResult result) {
        if (result.hasErrors()) {
            return "form-paciente";
        }
        repository.save(paciente);
        return "redirect:/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Paciente paciente = repository.findById(id).orElseThrow();
        model.addAttribute("paciente", paciente);
        return "form-paciente";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/pacientes";
    }
}