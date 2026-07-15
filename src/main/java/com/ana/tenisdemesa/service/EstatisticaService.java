package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import com.ana.tenisdemesa.repository.MedalhaRepository;
import com.ana.tenisdemesa.repository.PartidaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Vitórias, derrotas, taxa e sequência calculados sobre uma única consulta,
     * em vez de uma ida ao banco por estatística.
     */
    public Map<String, Object> resumoPeriodo(LocalDate inicio, LocalDate fim) {
        List<Partida> ordenadas = (inicio != null && fim != null)
                ? partidaRepository.findByDataBetweenOrderByDataDesc(inicio, fim)
                : partidaRepository.findAllByOrderByDataDesc();

        long vitorias = ordenadas.stream().filter(Partida::isVitoria).count();
        long derrotas = ordenadas.stream().filter(Partida::isDerrota).count();
        long total = vitorias + derrotas;
        String taxa = total == 0 ? "—" : Math.round((double) vitorias / total * 100) + "%";

        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("totalVitorias", vitorias);
        resumo.put("totalDerrotas", derrotas);
        resumo.put("taxaVitoria", taxa);
        resumo.put("sequenciaAtual", calcularSequencia(ordenadas));
        return resumo;
    }

    private String calcularSequencia(List<Partida> ordenadasDesc) {
        if (ordenadasDesc.isEmpty()) return "—";

        Partida primeira = ordenadasDesc.get(0);
        if (primeira.getResultado() == null) return "—";
        boolean ehVitoria = primeira.isVitoria();

        long count = 1;
        for (int i = 1; i < ordenadasDesc.size(); i++) {
            Partida p = ordenadasDesc.get(i);
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
}
