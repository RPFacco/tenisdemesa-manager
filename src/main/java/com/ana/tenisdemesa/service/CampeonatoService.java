package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.exception.NotFoundException;
import com.ana.tenisdemesa.model.Campeonato;
import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CampeonatoService {

    private final CampeonatoRepository repository;

    public CampeonatoService(CampeonatoRepository repository) {
        this.repository = repository;
    }

    public List<Campeonato> listar() {
        return repository.findAllByOrderByDataInicioDesc();
    }

    public Campeonato buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Campeonato não encontrado"));
    }

    public Campeonato salvar(Campeonato campeonato) {
        return repository.save(campeonato);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Campeonato buscarAtual() {
        LocalDate hoje = LocalDate.now();

        // Prioridade 1: Campeonato em andamento (dataInicio ≤ hoje ≤ dataFim)
        Optional<Campeonato> emAndamento = repository
                .findFirstByDataInicioLessThanEqualAndDataFimGreaterThanEqualOrderByDataInicioDesc(hoje, hoje);
        if (emAndamento.isPresent()) return emAndamento.get();

        // Prioridade 2: Próximo campeonato futuro (dataInicio > hoje)
        Optional<Campeonato> proximoFuturo = repository
                .findFirstByDataInicioGreaterThanEqualOrderByDataInicioAsc(hoje);
        if (proximoFuturo.isPresent()) return proximoFuturo.get();

        // Prioridade 3: Campeonato mais recente do passado (dataInicio ≤ hoje)
        return repository.findFirstByDataInicioLessThanEqualOrderByDataInicioDesc(hoje).orElse(null);
    }

    public Map<String, Long> calcularProgresso(Campeonato campeonato) {
        List<Partida> partidas = campeonato.getPartidas();
        long total = partidas.size();
        long realizadas = partidas.stream()
                .filter(p -> p.getResultado() != null)
                .count();
        return Map.of("total", total, "realizadas", realizadas);
    }

    public Map<String, Object> calcularEstatisticas(Campeonato campeonato) {
        List<Partida> partidas = campeonato.getPartidas();
        long vitorias = partidas.stream()
                .filter(p -> p.isVitoria())
                .count();
        long derrotas = partidas.stream()
                .filter(p -> p.isDerrota())
                .count();
        String taxa = (vitorias + derrotas) > 0
                ? Math.round((double) vitorias / (vitorias + derrotas) * 100) + "%"
                : "—";
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("vitorias", vitorias);
        stats.put("derrotas", derrotas);
        stats.put("taxaVitoria", taxa);
        return stats;
    }

    public void validarDatas(Campeonato campeonato, org.springframework.validation.BindingResult result) {
        if (campeonato.getDataFim() != null && campeonato.getDataInicio() != null
                && campeonato.getDataFim().isBefore(campeonato.getDataInicio())) {
            result.rejectValue("dataFim", "", "Data fim deve ser maior ou igual à data início");
        }
    }
}
