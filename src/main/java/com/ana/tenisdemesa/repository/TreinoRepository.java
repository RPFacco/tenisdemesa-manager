package com.ana.tenisdemesa.repository;

import com.ana.tenisdemesa.model.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<Treino> findTop5ByOrderByDataDesc();
}
