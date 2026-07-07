package com.ana.tenisdemesa.controller;

import com.ana.tenisdemesa.model.Campeonato;
import com.ana.tenisdemesa.model.Medalha;
import com.ana.tenisdemesa.model.enums.Modalidade;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.service.CampeonatoService;
import com.ana.tenisdemesa.service.MedalhaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/campeonatos/{campeonatoId}/medalhas")
public class MedalhaController {

    private final MedalhaService medalhaService;
    private final CampeonatoService campeonatoService;

    public MedalhaController(MedalhaService medalhaService, CampeonatoService campeonatoService) {
        this.medalhaService = medalhaService;
        this.campeonatoService = campeonatoService;
    }

    @GetMapping("/nova")
    public String novoForm(@PathVariable Long campeonatoId, Model model) {
        Campeonato c = campeonatoService.buscarPorId(campeonatoId);
        Medalha m = new Medalha();
        m.setCampeonato(c);
        model.addAttribute("medalha", m);
        model.addAttribute("tipos", TipoMedalha.values());
        model.addAttribute("modalidades", Modalidade.values());
        return "medalhas/form";
    }

    @PostMapping
    public String salvar(@PathVariable Long campeonatoId, Medalha medalha, RedirectAttributes redirect) {
        Campeonato c = campeonatoService.buscarPorId(campeonatoId);
        medalha.setCampeonato(c);
        medalhaService.salvar(medalha);
        redirect.addFlashAttribute("mensagem", "Medalha adicionada com sucesso!");
        return "redirect:/campeonatos/" + campeonatoId;
    }

    @PostMapping("/{medalhaId}/excluir")
    public String excluir(@PathVariable Long campeonatoId, @PathVariable Long medalhaId, RedirectAttributes redirect) {
        medalhaService.excluir(medalhaId);
        redirect.addFlashAttribute("mensagem", "Medalha excluída com sucesso!");
        return "redirect:/campeonatos/" + campeonatoId;
    }
}
