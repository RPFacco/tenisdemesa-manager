package com.ana.tenisdemesa.controller;

import com.ana.tenisdemesa.model.Treino;
import com.ana.tenisdemesa.model.enums.TipoTreino;
import com.ana.tenisdemesa.service.TreinoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/treinos")
public class TreinoController {

    private final TreinoService service;

    public TreinoController(TreinoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("treinos", service.listar());
        model.addAttribute("resumoHoje", service.resumoDiario());
        model.addAttribute("resumoSemana", service.resumoSemanal());
        model.addAttribute("resumoMes", service.resumoMensal());
        model.addAttribute("tipos", TipoTreino.values());
        return "treinos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("treino", new Treino());
        model.addAttribute("tipos", TipoTreino.values());
        return "treinos/form";
    }

    @PostMapping
    public String salvar(Treino treino,
                         @RequestParam(defaultValue = "0") Integer horas,
                         @RequestParam(defaultValue = "0") Integer minutos,
                         RedirectAttributes redirect) {
        treino.setDuracaoHoras(horas + minutos / 60.0);
        redirect.addFlashAttribute("mensagem", "Treino salvo com sucesso!");
        return "redirect:/treinos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.excluir(id);
        redirect.addFlashAttribute("mensagem", "Treino excluído com sucesso!");
        return "redirect:/treinos";
    }
}
