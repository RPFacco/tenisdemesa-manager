package com.ana.tenisdemesa.controller;

import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.repository.PartidaRepository;
import com.ana.tenisdemesa.repository.TreinoRepository;
import com.ana.tenisdemesa.service.CampeonatoService;
import com.ana.tenisdemesa.service.EstatisticaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@Controller
public class GeralController {

    private final EstatisticaService estatisticaService;
    private final CampeonatoService campeonatoService;
    private final PartidaRepository partidaRepository;
    private final TreinoRepository treinoRepository;

    public GeralController(EstatisticaService estatisticaService, CampeonatoService campeonatoService, PartidaRepository partidaRepository, TreinoRepository treinoRepository) {
        this.estatisticaService = estatisticaService;
        this.campeonatoService = campeonatoService;
        this.partidaRepository = partidaRepository;
        this.treinoRepository = treinoRepository;
    }

    @GetMapping("/")
    public String redirectParaGeral() {
        return "redirect:/geral";
    }

    @GetMapping("/geral")
    public String geral(@RequestParam(defaultValue = "ano") String periodo, Model model) {
        LocalDate inicio = null;
        LocalDate fim = null;
        String periodoLabel = "Ano";

        if ("ano".equals(periodo) || periodo.matches("\\d{4}")) {
            int ano = "ano".equals(periodo) ? LocalDate.now().getYear() : Integer.parseInt(periodo);
            inicio = LocalDate.of(ano, 1, 1);
            fim = LocalDate.of(ano, 12, 31);
            periodoLabel = String.valueOf(ano);
        } else if ("30d".equals(periodo)) {
            fim = LocalDate.now();
            inicio = fim.minusDays(30);
            periodoLabel = "30 dias";
        }

        model.addAttribute("totalVitorias", estatisticaService.totalVitorias(inicio, fim));
        model.addAttribute("totalDerrotas", estatisticaService.totalDerrotas(inicio, fim));
        model.addAttribute("taxaVitoria", estatisticaService.taxaVitoria(inicio, fim));
        model.addAttribute("sequenciaAtual", estatisticaService.sequenciaAtual(inicio, fim));
        model.addAttribute("medalhasPorTipo", estatisticaService.medalhasPorTipo());
        model.addAttribute("totalCampeonatos", estatisticaService.totalCampeonatos());
        model.addAttribute("tiposMedalha", TipoMedalha.values());
        var campeonatoAtual = campeonatoService.buscarAtual();
        model.addAttribute("ultimasPartidas", partidaRepository.findTop5ByOrderByDataDesc());
        model.addAttribute("ultimosTreinos", treinoRepository.findTop5ByOrderByDataDesc());
        model.addAttribute("campeonatoAtual", campeonatoAtual);
        if (campeonatoAtual != null) {
            model.addAttribute("progressoCampeonato", campeonatoService.calcularProgresso(campeonatoAtual));
        }
        model.addAttribute("periodo", periodo);
        model.addAttribute("periodoLabel", periodoLabel);
        return "geral";
    }
}
