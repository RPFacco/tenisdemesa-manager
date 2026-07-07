package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Medalha;
import com.ana.tenisdemesa.repository.MedalhaRepository;
import org.springframework.stereotype.Service;

@Service
public class MedalhaService {

    private final MedalhaRepository repository;

    public MedalhaService(MedalhaRepository repository) {
        this.repository = repository;
    }

    public Medalha salvar(Medalha medalha) {
        return repository.save(medalha);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
