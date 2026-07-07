package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Campeonato;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
}
