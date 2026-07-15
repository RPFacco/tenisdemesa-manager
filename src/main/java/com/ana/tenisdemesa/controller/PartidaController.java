package com.ana.tenisdemesa.controller;

import com.ana.tenisdemesa.exception.NotFoundException;
import com.ana.tenisdemesa.model.Campeonato;
import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.SetPartida;
import com.ana.tenisdemesa.model.enums.FasePartida;
import com.ana.tenisdemesa.service.CampeonatoService;
import com.ana.tenisdemesa.service.PartidaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/campeonatos/{campeonatoId}/partidas")
public class PartidaController {

    private final PartidaService partidaService;
    private final CampeonatoService campeonatoService;

    public PartidaController(PartidaService partidaService, CampeonatoService campeonatoService) {
        this.partidaService = partidaService;
        this.campeonatoService = campeonatoService;
    }

    @GetMapping("/nova")
    public String novoForm(@PathVariable Long campeonatoId, Model model) {
        Campeonato c = campeonatoService.buscarPorId(campeonatoId);
        Partida p = new Partida();
        p.setCampeonato(c);
        p.getSets().add(new SetPartida(1, 0, 0));
        model.addAttribute("partida", p);
        model.addAttribute("fases", FasePartida.values());
        return "partidas/form";
    }

    @PostMapping
    public String salvar(@PathVariable Long campeonatoId,
                         @RequestParam(name = "modoPlacar", defaultValue = "detalhado") String modoPlacar,
                         Partida partida, RedirectAttributes redirect) {
        try {
            Campeonato c = campeonatoService.buscarPorId(campeonatoId);
            partida.setCampeonato(c);
            aplicarModoPlacar(partida, modoPlacar);
            partidaService.salvar(partida);
            redirect.addFlashAttribute("mensagem", "Partida salva com sucesso!");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/campeonatos/" + campeonatoId + "/partidas/nova";
        }
        return "redirect:/campeonatos/" + campeonatoId;
    }

    private void aplicarModoPlacar(Partida partida, String modoPlacar) {
        if ("simples".equals(modoPlacar)) {
            partida.getSets().clear();
        } else {
            partida.setSetsAtleta(null);
            partida.setSetsAdversario(null);
            int i = 1;
            for (SetPartida set : partida.getSets()) {
                set.setNumeroSet(i++);
            }
        }
    }

    @GetMapping("/{partidaId}/editar")
    public String editarForm(@PathVariable Long campeonatoId, @PathVariable Long partidaId, Model model) {
        Campeonato c = campeonatoService.buscarPorId(campeonatoId);
        Partida p = c.getPartidas().stream()
                .filter(part -> part.getId().equals(partidaId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Partida não encontrada"));
        model.addAttribute("partida", p);
        model.addAttribute("fases", FasePartida.values());
        return "partidas/form";
    }

    @PostMapping("/{partidaId}")
    public String atualizar(@PathVariable Long campeonatoId, @PathVariable Long partidaId,
                            @RequestParam(name = "modoPlacar", defaultValue = "detalhado") String modoPlacar,
                            Partida partida, RedirectAttributes redirect) {
        try {
            Campeonato c = campeonatoService.buscarPorId(campeonatoId);
            partida.setId(partidaId);
            partida.setCampeonato(c);
            aplicarModoPlacar(partida, modoPlacar);
            partidaService.salvar(partida);
            redirect.addFlashAttribute("mensagem", "Partida atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/campeonatos/" + campeonatoId + "/partidas/" + partidaId + "/editar";
        }
        return "redirect:/campeonatos/" + campeonatoId;
    }

    @PostMapping("/{partidaId}/excluir")
    public String excluir(@PathVariable Long campeonatoId, @PathVariable Long partidaId, RedirectAttributes redirect) {
        partidaService.excluir(partidaId);
        redirect.addFlashAttribute("mensagem", "Partida excluída com sucesso!");
        return "redirect:/campeonatos/" + campeonatoId;
    }
}
