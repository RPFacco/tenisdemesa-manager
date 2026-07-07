package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Medalha;
import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.enums.ResultadoPartida;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import com.ana.tenisdemesa.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstatisticaService {

    private final CampeonatoRepository campeonatoRepository;
    private final PartidaRepository partidaRepository;

    public EstatisticaService(CampeonatoRepository campeonatoRepository, PartidaRepository partidaRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.partidaRepository = partidaRepository;
    }

    public long totalCampeonatos() {
        return campeonatoRepository.count();
    }

    public long totalVitorias() {
        return totalVitorias(null, null);
    }

    public long totalVitorias(LocalDate inicio, LocalDate fim) {
        return partidasNoPeriodo(inicio, fim).stream()
                .filter(p -> p.getResultado() == ResultadoPartida.VITORIA)
                .count();
    }

    public long totalDerrotas() {
        return totalDerrotas(null, null);
    }

    public long totalDerrotas(LocalDate inicio, LocalDate fim) {
        return partidasNoPeriodo(inicio, fim).stream()
                .filter(p -> p.getResultado() == ResultadoPartida.DERROTA)
                .count();
    }

    public String taxaVitoria() {
        return taxaVitoria(null, null);
    }

    public String taxaVitoria(LocalDate inicio, LocalDate fim) {
        long vitorias = totalVitorias(inicio, fim);
        long derrotas = totalDerrotas(inicio, fim);
        long total = vitorias + derrotas;
        if (total == 0) return "—";
        long percentual = Math.round((double) vitorias / total * 100);
        return percentual + "%";
    }

    public Map<TipoMedalha, Long> medalhasPorTipo() {
        return campeonatoRepository.findAll().stream()
                .flatMap(c -> c.getMedalhas().stream())
                .collect(Collectors.groupingBy(
                        Medalha::getTipo,
                        Collectors.counting()
                ));
    }

    public String sequenciaAtual() {
        return sequenciaAtual(null, null);
    }

    private List<Partida> partidasNoPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            return partidaRepository.findByDataBetween(inicio, fim);
        }
        return partidaRepository.findAll();
    }

    public String sequenciaAtual(LocalDate inicio, LocalDate fim) {
        List<Partida> ordenadas;
        if (inicio != null && fim != null) {
            ordenadas = partidaRepository.findByDataBetweenOrderByDataDesc(inicio, fim);
        } else {
            ordenadas = partidaRepository.findAll().stream()
                    .sorted(Comparator.comparing(Partida::getData).reversed())
                    .toList();
        }
        if (ordenadas.isEmpty()) return "—";

        ResultadoPartida primeiro = ordenadas.get(0).getResultado();
        if (primeiro == null) return "—";

        long count = 1;
        for (int i = 1; i < ordenadas.size(); i++) {
            if (ordenadas.get(i).getResultado() == primeiro) {
                count++;
            } else {
                break;
            }
        }
        String label = primeiro == ResultadoPartida.VITORIA ? "V" : "D";
        return count + label;
    }
}
