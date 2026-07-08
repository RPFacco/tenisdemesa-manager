package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import com.ana.tenisdemesa.repository.MedalhaRepository;
import com.ana.tenisdemesa.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        return contarPorResultado(null, true);
    }

    public long totalVitorias(LocalDate inicio, LocalDate fim) {
        return contarPorResultadoPeriodo(inicio, fim, true);
    }

    public long totalDerrotas() {
        return contarPorResultado(null, false);
    }

    public long totalDerrotas(LocalDate inicio, LocalDate fim) {
        return contarPorResultadoPeriodo(inicio, fim, false);
    }

    private long contarPorResultadoPeriodo(LocalDate inicio, LocalDate fim, boolean vitoria) {
        if (inicio == null || fim == null) return contarPorResultado(null, vitoria);
        return partidaRepository.findByDataBetween(inicio, fim).stream()
                .filter(vitoria ? Partida::isVitoria : Partida::isDerrota)
                .count();
    }

    private long contarPorResultado(LocalDate inicioFim, boolean vitoria) {
        List<Partida> todas = partidaRepository.findAll();
        return vitoria
                ? todas.stream().filter(Partida::isVitoria).count()
                : todas.stream().filter(Partida::isDerrota).count();
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
            ordenadas = partidaRepository.findAllByOrderByDataDesc();
        }
        if (ordenadas.isEmpty()) return "—";

        Partida primeira = ordenadas.get(0);
        if (primeira.getResultado() == null) return "—";
        boolean ehVitoria = primeira.isVitoria();

        long count = 1;
        for (int i = 1; i < ordenadas.size(); i++) {
            Partida p = ordenadas.get(i);
            if (p.getResultado() == null) break;
            if (ehVitoria ? p.isVitoria() : p.isDerrota()) {
                count++;
            } else {
                break;
            }
        }
        String label = ehVitoria ? "V" : "D";
        return count + label;
    }
}
