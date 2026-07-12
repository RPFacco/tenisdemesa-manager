package com.ana.tenisdemesa.repository;

import com.ana.tenisdemesa.model.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {

    List<Campeonato> findByDataFimGreaterThanEqualOrderByDataInicioDesc(LocalDate data);
    Optional<Campeonato> findTopByOrderByDataFimDesc();

    List<Campeonato> findAllByOrderByDataInicioDesc();

    Optional<Campeonato> findFirstByDataInicioLessThanEqualAndDataFimGreaterThanEqualOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim);

    Optional<Campeonato> findFirstByDataInicioGreaterThanEqualOrderByDataInicioAsc(LocalDate data);

    Optional<Campeonato> findFirstByDataInicioLessThanEqualOrderByDataInicioDesc(LocalDate data);
}
