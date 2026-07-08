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
}
