package com.ana.tenisdemesa.repository;

import com.ana.tenisdemesa.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findTop5ByOrderByDataDesc();
}
