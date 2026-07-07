package com.ana.tenisdemesa.model;

import com.ana.tenisdemesa.model.enums.TipoTreino;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(indexes = @Index(name = "idx_treino_data", columnList = "data"))
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate data;

    @NotNull
    private Double duracaoHoras;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoTreino tipo;

    public Treino() {}

    public Treino(LocalDate data, Double duracaoHoras, TipoTreino tipo) {
        this.data = data;
        this.duracaoHoras = duracaoHoras;
        this.tipo = tipo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Double getDuracaoHoras() { return duracaoHoras; }
    public void setDuracaoHoras(Double duracaoHoras) { this.duracaoHoras = duracaoHoras; }
    public TipoTreino getTipo() { return tipo; }
    public void setTipo(TipoTreino tipo) { this.tipo = tipo; }
}
