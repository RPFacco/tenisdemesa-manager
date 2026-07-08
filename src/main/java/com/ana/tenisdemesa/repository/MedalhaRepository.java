package com.ana.tenisdemesa.repository;

import com.ana.tenisdemesa.model.Medalha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface MedalhaRepository extends JpaRepository<Medalha, Long> {
    @Query("SELECT m.tipo, COUNT(m) FROM Medalha m GROUP BY m.tipo")
    List<Object[]> countGroupedByTipo();
}
