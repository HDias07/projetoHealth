package com.healthconnect.healthconnect.controller;

import com.healthconnect.healthconnect.DTO.ContatoDTO;
import com.healthconnect.healthconnect.DTO.PacienteDTO;
import com.healthconnect.healthconnect.exception.CpfJaCadastradoException;
import com.healthconnect.healthconnect.exception.PacienteNaoEncontradoException;
import com.healthconnect.healthconnect.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", service.listarTodos());
        return "lista-pacientes";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("paciente", new PacienteDTO());
        return "form-paciente";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("paciente") PacienteDTO dto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "form-paciente";
        }

        try {
            service.salvar(dto);
        } catch (CpfJaCadastradoException ex) {
            result.rejectValue("cpf", "cpf.duplicado", ex.getMessage());
            return "form-paciente";
        } catch (PacienteNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/pacientes";
        }

        redirectAttributes.addFlashAttribute("sucesso", "Paciente salvo com sucesso.");
        return "redirect:/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("paciente", service.buscarPorId(id));
            return "form-paciente";
        } catch (PacienteNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/pacientes";
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Paciente removido com sucesso.");
        } catch (PacienteNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
        }

        return "redirect:/pacientes";
    }

    @GetMapping("/editar-contato/{id}")
    public String editarContato(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("pacienteId", id);
            model.addAttribute("contato", service.buscarContato(id));
            return "form-contato";
        } catch (PacienteNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/pacientes";
        }
    }

    @PostMapping("/atualizar-contato/{id}")
    public String atualizarContato(
            @PathVariable Long id,
            @Valid @ModelAttribute("contato") ContatoDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("pacienteId", id);
            return "form-contato";
        }

        try {
            service.atualizarContato(id, dto);
        } catch (PacienteNaoEncontradoException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/pacientes";
        }

        redirectAttributes.addFlashAttribute("sucesso", "Contato atualizado com sucesso.");
        return "redirect:/pacientes";
    }
}
