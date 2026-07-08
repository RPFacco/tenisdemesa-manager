package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.enums.ResultadoPartida;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import com.ana.tenisdemesa.repository.MedalhaRepository;
import com.ana.tenisdemesa.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class EstatisticaService {

    private final CampeonatoRepository campeonatoRepository;
    private final MedalhaRepository medalhaRepository;
    private final PartidaRepository partidaRepository;

    public EstatisticaService(CampeonatoRepository campeonatoRepository, MedalhaRepository medalhaRepository, PartidaRepository partidaRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.medalhaRepository = medalhaRepository;
        this.partidaRepository = partidaRepository;
    }

    public long totalCampeonatos() {
        return campeonatoRepository.count();
    }

    public long totalVitorias() {
        return totalVitorias(null, null);
    }

    public long totalVitorias(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            return partidaRepository.countByResultadoAndDataBetween(ResultadoPartida.VITORIA, inicio, fim);
        }
        return partidaRepository.countByResultado(ResultadoPartida.VITORIA);
    }

    public long totalDerrotas() {
        return totalDerrotas(null, null);
    }

    public long totalDerrotas(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            return partidaRepository.countByResultadoAndDataBetween(ResultadoPartida.DERROTA, inicio, fim);
        }
        return partidaRepository.countByResultado(ResultadoPartida.DERROTA);
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
        List<Object[]> resultados = medalhaRepository.countGroupedByTipo();
        Map<TipoMedalha, Long> mapa = new LinkedHashMap<>();
        for (TipoMedalha tipo : TipoMedalha.values()) {
            mapa.put(tipo, 0L);
        }
        for (Object[] row : resultados) {
            mapa.put((TipoMedalha) row[0], (Long) row[1]);
        }
        return mapa;
    }

    public String sequenciaAtual() {
        return sequenciaAtual(null, null);
    }

    public String sequenciaAtual(LocalDate inicio, LocalDate fim) {
        List<Partida> ordenadas;
        if (inicio != null && fim != null) {
            ordenadas = partidaRepository.findByDataBetweenOrderByDataDesc(inicio, fim);
        } else {
            ordenadas = partidaRepository.findAll();
            ordenadas.sort(Comparator.comparing(Partida::getData).reversed());
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
