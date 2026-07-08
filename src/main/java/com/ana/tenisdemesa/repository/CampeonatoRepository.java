package com.ana.tenisdemesa.repository;

import com.ana.tenisdemesa.model.Campeonato;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {
    @EntityGraph(attributePaths = {"medalhas", "partidas"})
    List<Campeonato> findAll();

    List<Campeonato> findByDataFimGreaterThanEqualOrderByDataInicioDesc(LocalDate data);
    Optional<Campeonato> findTopByOrderByDataFimDesc();
}
