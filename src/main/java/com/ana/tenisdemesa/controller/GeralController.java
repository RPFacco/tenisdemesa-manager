package com.ana.tenisdemesa.controller;

import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.service.EstatisticaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeralController {

    private final EstatisticaService estatisticaService;

    public GeralController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping("/")
    public String redirectParaGeral() {
        return "redirect:/geral";
    }

    @GetMapping("/geral")
    public String geral(Model model) {
        model.addAttribute("totalVitorias", estatisticaService.totalVitorias());
        model.addAttribute("totalDerrotas", estatisticaService.totalDerrotas());
        model.addAttribute("taxaVitoria", estatisticaService.taxaVitoria());
        model.addAttribute("medalhasPorTipo", estatisticaService.medalhasPorTipo());
        model.addAttribute("totalCampeonatos", estatisticaService.totalCampeonatos());
        model.addAttribute("sequenciaAtual", estatisticaService.sequenciaAtual());
        model.addAttribute("tiposMedalha", TipoMedalha.values());
        return "geral";
    }
}
