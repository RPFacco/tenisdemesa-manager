package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.SetPartida;
import com.ana.tenisdemesa.model.enums.ResultadoPartida;
import com.ana.tenisdemesa.repository.CampeonatoRepository;
import com.ana.tenisdemesa.repository.MedalhaRepository;
import com.ana.tenisdemesa.repository.PartidaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstatisticaServiceTest {

    @Mock
    private CampeonatoRepository campeonatoRepository;

    @Mock
    private MedalhaRepository medalhaRepository;

    @Mock
    private PartidaRepository partidaRepository;

    @InjectMocks
    private EstatisticaService service;

    @Test
    void resumoPeriodo_3V1D_retornaSequencia3VeTotais() {
        Partida v1 = partidaComResultado(ResultadoPartida.VITORIA, LocalDate.of(2026, 7, 7));
        Partida v2 = partidaComResultado(ResultadoPartida.VITORIA, LocalDate.of(2026, 7, 6));
        Partida v3 = partidaComResultado(ResultadoPartida.VITORIA, LocalDate.of(2026, 7, 5));
        Partida d1 = partidaComResultado(ResultadoPartida.DERROTA, LocalDate.of(2026, 7, 4));

        when(partidaRepository.findAllByOrderByDataDesc()).thenReturn(new ArrayList<>(List.of(v1, v2, v3, d1)));

        Map<String, Object> resumo = service.resumoPeriodo(null, null);

        assertEquals("3V", resumo.get("sequenciaAtual"));
        assertEquals(3L, resumo.get("totalVitorias"));
        assertEquals(1L, resumo.get("totalDerrotas"));
        assertEquals("75%", resumo.get("taxaVitoria"));
    }

    private Partida partidaComResultado(ResultadoPartida resultado, LocalDate data) {
        Partida p = new Partida();
        if (resultado == ResultadoPartida.VITORIA) {
            p.setSets(List.of(new SetPartida(1, 11, 5)));
        } else {
            p.setSets(List.of(new SetPartida(1, 5, 11)));
        }
        p.setData(data);
        return p;
    }
}
