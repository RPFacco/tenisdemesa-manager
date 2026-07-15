package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.SetPartida;
import com.ana.tenisdemesa.repository.PartidaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PartidaServiceTest {

    @Mock
    private PartidaRepository repository;

    @InjectMocks
    private PartidaService service;

    @Test
    void validarSets_quandoSet10x8_lancaExcecao() {
        Partida partida = new Partida();
        partida.setSets(List.of(new SetPartida(1, 10, 8)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.validarSets(partida));
        assertTrue(ex.getMessage().contains("mínimo 11 pontos"));
    }

    @Test
    void validarSets_quandoSet11x9_ok() {
        Partida partida = new Partida();
        partida.setSets(List.of(new SetPartida(1, 11, 9)));

        assertDoesNotThrow(() -> service.validarSets(partida));
    }

    @Test
    void validarPlacar_simples3x1_ok() {
        Partida partida = new Partida();
        partida.setSetsAtleta(3);
        partida.setSetsAdversario(1);

        assertDoesNotThrow(() -> service.validarPlacar(partida));
        assertTrue(partida.isVitoria());
        assertEquals("3-1", partida.getPlacar());
    }

    @Test
    void validarPlacar_simplesEmpatado_lancaExcecao() {
        Partida partida = new Partida();
        partida.setSetsAtleta(2);
        partida.setSetsAdversario(2);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.validarPlacar(partida));
        assertTrue(ex.getMessage().contains("empatada"));
    }

    @Test
    void validarPlacar_simplesSemValores_lancaExcecao() {
        Partida partida = new Partida();

        assertThrows(IllegalArgumentException.class, () -> service.validarPlacar(partida));
    }
}
