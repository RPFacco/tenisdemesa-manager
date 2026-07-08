package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Campeonato;
import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
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
        LocalDate hoje = LocalDate.now();
        List<Campeonato> emAndamento = repository.findByDataFimGreaterThanEqualOrderByDataInicioDesc(hoje);
        if (!emAndamento.isEmpty()) return emAndamento.get(0);
        return repository.findTopByOrderByDataFimDesc().orElse(null);
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
