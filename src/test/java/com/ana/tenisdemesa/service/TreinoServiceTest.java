package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Treino;
import com.ana.tenisdemesa.model.enums.TipoTreino;
import com.ana.tenisdemesa.repository.TreinoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TreinoServiceTest {

    @Mock
    private TreinoRepository repository;

    @InjectMocks
    private TreinoService service;

    @Test
    void buildResumo_agregaTipos() {
        Treino tecnico = new Treino();
        tecnico.setDuracaoHoras(1.5);
        tecnico.setTipo(TipoTreino.TECNICA);

        Treino fisico = new Treino();
        fisico.setDuracaoHoras(1.0);
        fisico.setTipo(TipoTreino.FISICO);

        Treino tecnico2 = new Treino();
        tecnico2.setDuracaoHoras(2.0);
        tecnico2.setTipo(TipoTreino.TECNICA);

        List<Treino> treinos = List.of(tecnico, fisico, tecnico2);

        Map<String, Object> resumo = service.buildResumo(treinos);

        assertEquals(4.5, (Double) resumo.get("total"), 0.001);

        Map<TipoTreino, Double> breakdown = (Map<TipoTreino, Double>) resumo.get("breakdown");
        assertEquals(3.5, breakdown.get(TipoTreino.TECNICA), 0.001);
        assertEquals(1.0, breakdown.get(TipoTreino.FISICO), 0.001);
    }
}
