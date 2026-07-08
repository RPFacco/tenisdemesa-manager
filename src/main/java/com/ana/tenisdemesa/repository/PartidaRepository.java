package com.ana.tenisdemesa.repository;

import com.ana.tenisdemesa.model.Partida;
import com.ana.tenisdemesa.model.enums.ResultadoPartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findTop5ByOrderByDataDesc();
    List<Partida> findByDataBetween(LocalDate inicio, LocalDate fim);
    List<Partida> findByDataBetweenOrderByDataDesc(LocalDate inicio, LocalDate fim);

    @Query("SELECT COUNT(p) FROM Partida p WHERE p.resultado = :resultado AND p.data BETWEEN :inicio AND :fim")
    long countByResultadoAndDataBetween(@Param("resultado") ResultadoPartida resultado, @Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("SELECT COUNT(p) FROM Partida p WHERE p.resultado = :resultado")
    long countByResultado(@Param("resultado") ResultadoPartida resultado);
}
