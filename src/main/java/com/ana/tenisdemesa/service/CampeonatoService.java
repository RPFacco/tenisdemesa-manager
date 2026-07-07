package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Campeonato;
import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class CampeonatoService {

    private final CampeonatoRepository repository;

    public CampeonatoService(CampeonatoRepository repository) {
        this.repository = repository;
    }

    public List<Campeonato> listar() {
        return repository.findAll();
    }

    public Campeonato buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
    }

    public Campeonato salvar(Campeonato campeonato) {
        return repository.save(campeonato);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Campeonato buscarAtual() {
        List<Campeonato> todos = repository.findAll();
        if (todos.isEmpty()) return null;

        LocalDate hoje = LocalDate.now();

        Campeonato emAndamento = todos.stream()
                .filter(c -> !c.getDataFim().isBefore(hoje))
                .min(Comparator.comparing(Campeonato::getDataInicio).reversed())
                .orElse(null);

        if (emAndamento != null) return emAndamento;

        return todos.stream()
                .max(Comparator.comparing(Campeonato::getDataFim))
                .orElse(null);
    }

    public Map<String, Long> calcularProgresso(Campeonato campeonato) {
        List<Partida> partidas = campeonato.getPartidas();
        long total = partidas.size();
        long realizadas = partidas.stream()
                .filter(p -> p.getResultado() != null)
                .count();
        return Map.of("total", total, "realizadas", realizadas);
    }
}
