package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.SetPartida;
import com.ana.tenisdemesa.repository.PartidaRepository;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    private final PartidaRepository repository;

    public PartidaService(PartidaRepository repository) {
        this.repository = repository;
    }

    public Partida salvar(Partida partida) {
        validarPlacar(partida);
        return repository.save(partida);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public void validarPlacar(Partida partida) {
        if (partida.getSets().isEmpty()) {
            validarPlacarSimples(partida);
        } else {
            validarSets(partida);
        }
    }

    private void validarPlacarSimples(Partida partida) {
        Integer atleta = partida.getSetsAtleta();
        Integer adversario = partida.getSetsAdversario();
        if (atleta == null || adversario == null) {
            throw new IllegalArgumentException("Informe quantos sets cada um venceu.");
        }
        if (atleta < 0 || adversario < 0) {
            throw new IllegalArgumentException("O número de sets não pode ser negativo.");
        }
        if (atleta.equals(adversario)) {
            throw new IllegalArgumentException("A partida não pode terminar empatada no total de sets. Verifique os resultados.");
        }
    }

    public void validarSets(Partida partida) {
        for (SetPartida set : partida.getSets()) {
            int maior = Math.max(set.getPontosAtleta(), set.getPontosAdversario());
            int menor = Math.min(set.getPontosAtleta(), set.getPontosAdversario());
            if (maior < 11) {
                throw new IllegalArgumentException("Set inválido: vencedor precisa de no mínimo 11 pontos.");
            }
            if (maior - menor < 2) {
                throw new IllegalArgumentException("Set inválido: vantagem mínima de 2 pontos é necessária.");
            }
        }
        long setsVencidos = partida.getSets().stream()
                .filter(s -> s.getPontosAtleta() > s.getPontosAdversario())
                .count();
        long setsPerdidos = partida.getSets().size() - setsVencidos;
        if (setsVencidos == setsPerdidos) {
            throw new IllegalArgumentException("A partida não pode terminar empatada no total de sets. Verifique os resultados.");
        }
    }
}
