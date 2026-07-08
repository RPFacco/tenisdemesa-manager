package com.ana.tenisdemesa.controller;

import com.ana.tenisdemesa.model.Campeonato;
import com.ana.tenisdemesa.model.Medalha;
import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.enums.FasePartida;
import com.ana.tenisdemesa.model.enums.Modalidade;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.service.CampeonatoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
@RequestMapping("/campeonatos")
public class CampeonatoController {

    private final CampeonatoService service;

    public CampeonatoController(CampeonatoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("campeonatos", service.listar());
        return "campeonatos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("campeonato", new Campeonato());
        return "campeonatos/form";
    }

    @PostMapping
    public String salvar(@Valid Campeonato campeonato, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) return "campeonatos/form";
        service.validarDatas(campeonato, result);
        if (result.hasErrors()) return "campeonatos/form";
        service.salvar(campeonato);
        redirect.addFlashAttribute("mensagem", "Campeonato salvo com sucesso!");
        return "redirect:/campeonatos";
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable Long id, Model model) {
        Campeonato c = service.buscarPorId(id);
        Map<String, Object> stats = service.calcularEstatisticas(c);
        model.addAttribute("campeonato", c);
        model.addAttribute("vitorias", stats.get("vitorias"));
        model.addAttribute("derrotas", stats.get("derrotas"));
        model.addAttribute("taxaVitoria", stats.get("taxaVitoria"));
        return "campeonatos/detalhe";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("campeonato", service.buscarPorId(id));
        return "campeonatos/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid Campeonato campeonato, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) return "campeonatos/form";
        service.validarDatas(campeonato, result);
        if (result.hasErrors()) return "campeonatos/form";
        campeonato.setId(id);
        service.salvar(campeonato);
        redirect.addFlashAttribute("mensagem", "Campeonato atualizado com sucesso!");
        return "redirect:/campeonatos/" + id;
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.excluir(id);
        redirect.addFlashAttribute("mensagem", "Campeonato excluído com sucesso!");
        return "redirect:/campeonatos";
    }
}
