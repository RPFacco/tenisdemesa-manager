package com.ana.tenisdemesa.service;

import com.ana.tenisdemesa.model.Treino;
import com.ana.tenisdemesa.model.enums.TipoTreino;
import com.ana.tenisdemesa.repository.TreinoRepository;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TreinoService {

    private final TreinoRepository repository;

    public TreinoService(TreinoRepository repository) {
        this.repository = repository;
    }

    public List<Treino> listar() {
        return repository.findAll();
    }

    public Treino salvar(Treino treino) {
        return repository.save(treino);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Map<String, Object> resumoDiario() {
        LocalDate hoje = LocalDate.now();
        List<Treino> treinosHoje = listar().stream()
                .filter(t -> t.getData().equals(hoje))
                .toList();
        return buildResumo(treinosHoje);
    }

    public Map<String, Object> resumoSemanal() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate fimSemana = hoje.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<Treino> treinosSemana = listar().stream()
                .filter(t -> !t.getData().isBefore(inicioSemana) && !t.getData().isAfter(fimSemana))
                .toList();
        return buildResumo(treinosSemana);
    }

    public Map<String, Object> resumoMensal() {
        YearMonth mesCorrente = YearMonth.now();
        List<Treino> treinosMes = listar().stream()
                .filter(t -> YearMonth.from(t.getData()).equals(mesCorrente))
                .toList();
        return buildResumo(treinosMes);
    }

    private Map<String, Object> buildResumo(List<Treino> treinos) {
        double total = treinos.stream().mapToDouble(Treino::getDuracaoHoras).sum();
        Map<TipoTreino, Double> breakdown = treinos.stream()
                .collect(Collectors.groupingBy(
                        Treino::getTipo,
                        Collectors.summingDouble(Treino::getDuracaoHoras)
                ));
        Map<String, Object> resumo = new LinkedHashMap<>();
        resumo.put("total", total);
        resumo.put("breakdown", breakdown);
        return resumo;
    }
}
