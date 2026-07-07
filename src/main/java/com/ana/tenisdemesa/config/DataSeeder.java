package com.ana.tenisdemesa.config;

import com.ana.tenisdemesa.model.*;
import com.ana.tenisdemesa.model.enums.*;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import com.ana.tenisdemesa.repository.TreinoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CampeonatoRepository campeonatoRepository;
    private final TreinoRepository treinoRepository;

    public DataSeeder(CampeonatoRepository campeonatoRepository, TreinoRepository treinoRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.treinoRepository = treinoRepository;
    }

    @Override
    public void run(String... args) {
        if (campeonatoRepository.count() > 0) return;

        Campeonato c1 = new Campeonato();
        c1.setNome("Campeonato Gaúcho de Tênis de Mesa");
        c1.setLocal("Porto Alegre, RS");
        c1.setDataInicio(LocalDate.of(2026, 1, 10));
        c1.setDataFim(LocalDate.of(2026, 1, 12));
        c1.setCategoria("Estadual");

        Partida p1 = new Partida();
        p1.setCampeonato(c1);
        p1.setAdversario("Maria Silva");
        p1.setData(LocalDate.of(2026, 1, 10));
        p1.setFase(FasePartida.FASE_DE_GRUPOS);
        p1.setSets(List.of(
                new SetPartida(1, 11, 8),
                new SetPartida(2, 11, 5)
        ));
        p1.setLinkYoutube("https://youtube.com/watch?v=exemplo1");

        Partida p2 = new Partida();
        p2.setCampeonato(c1);
        p2.setAdversario("João Oliveira");
        p2.setData(LocalDate.of(2026, 1, 11));
        p2.setFase(FasePartida.QUARTAS_DE_FINAL);
        p2.setSets(List.of(
                new SetPartida(1, 12, 10),
                new SetPartida(2, 9, 11),
                new SetPartida(3, 11, 6)
        ));

        Partida p3 = new Partida();
        p3.setCampeonato(c1);
        p3.setAdversario("Ana Costa");
        p3.setData(LocalDate.of(2026, 1, 11));
        p3.setFase(FasePartida.SEMIFINAL);
        p3.setSets(List.of(
                new SetPartida(1, 11, 9),
                new SetPartida(2, 8, 11),
                new SetPartida(3, 11, 7),
                new SetPartida(4, 11, 13),
                new SetPartida(5, 11, 8)
        ));
        p3.setLinkYoutube("https://youtu.be/exemplo2");

        Partida p4 = new Partida();
        p4.setCampeonato(c1);
        p4.setAdversario("Pedro Santos");
        p4.setData(LocalDate.of(2026, 1, 12));
        p4.setFase(FasePartida.FINAL);
        p4.setSets(List.of(
                new SetPartida(1, 11, 7),
                new SetPartida(2, 11, 9),
                new SetPartida(3, 14, 12)
        ));
        p4.setLinkYoutube("https://youtube.com/watch?v=exemplo3");

        c1.setPartidas(List.of(p1, p2, p3, p4));

        Medalha m1 = new Medalha();
        m1.setCampeonato(c1);
        m1.setTipo(TipoMedalha.OURO);
        m1.setModalidade(Modalidade.SIMPLES);

        Medalha m2 = new Medalha();
        m2.setCampeonato(c1);
        m2.setTipo(TipoMedalha.PRATA);
        m2.setModalidade(Modalidade.DUPLAS);

        c1.setMedalhas(List.of(m1, m2));

        Campeonato c2 = new Campeonato();
        c2.setNome("Copa da Cidade de Santa Maria");
        c2.setLocal("Santa Maria, RS");
        c2.setDataInicio(LocalDate.of(2026, 3, 5));
        c2.setDataFim(LocalDate.of(2026, 3, 6));
        c2.setCategoria("Municipal");

        Partida p5 = new Partida();
        p5.setCampeonato(c2);
        p5.setAdversario("Lucas Pereira");
        p5.setData(LocalDate.of(2026, 3, 5));
        p5.setFase(FasePartida.FASE_DE_GRUPOS);
        p5.setSets(List.of(
                new SetPartida(1, 11, 6),
                new SetPartida(2, 11, 4)
        ));

        Partida p6 = new Partida();
        p6.setCampeonato(c2);
        p6.setAdversario("Carla Fernandes");
        p6.setData(LocalDate.of(2026, 3, 5));
        p6.setFase(FasePartida.FASE_DE_GRUPOS);
        p6.setSets(List.of(
                new SetPartida(1, 7, 11),
                new SetPartida(2, 11, 9),
                new SetPartida(3, 9, 11)
        ));

        Partida p7 = new Partida();
        p7.setCampeonato(c2);
        p7.setAdversario("Roberto Lima");
        p7.setData(LocalDate.of(2026, 3, 6));
        p7.setFase(FasePartida.SEMIFINAL);
        p7.setSets(List.of(
                new SetPartida(1, 11, 8),
                new SetPartida(2, 11, 13),
                new SetPartida(3, 12, 10)
        ));

        c2.setPartidas(List.of(p5, p6, p7));

        Medalha m3 = new Medalha();
        m3.setCampeonato(c2);
        m3.setTipo(TipoMedalha.BRONZE);
        m3.setModalidade(Modalidade.SIMPLES);

        c2.setMedalhas(List.of(m3));

        campeonatoRepository.save(c1);
        campeonatoRepository.save(c2);

        LocalDate hoje = LocalDate.now();
        List<Treino> treinos = List.of(
                new Treino(hoje.minusDays(1), 1.5, TipoTreino.TECNICA),
                new Treino(hoje.minusDays(2), 2.0, TipoTreino.JOGO),
                new Treino(hoje.minusDays(3), 1.0, TipoTreino.FISICO),
                new Treino(hoje.minusDays(5), 1.5, TipoTreino.TECNICA),
                new Treino(hoje.minusDays(7), 2.5, TipoTreino.JOGO),
                new Treino(hoje.minusDays(8), 1.0, TipoTreino.FISICO),
                new Treino(hoje.minusDays(10), 2.0, TipoTreino.TECNICA),
                new Treino(hoje.minusDays(12), 1.5, TipoTreino.JOGO),
                new Treino(hoje.minusDays(14), 1.0, TipoTreino.FISICO),
                new Treino(hoje.minusDays(18), 2.0, TipoTreino.TECNICA)
        );
        treinoRepository.saveAll(treinos);
    }
}
