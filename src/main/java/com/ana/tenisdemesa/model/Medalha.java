package com.ana.tenisdemesa.model;

import com.ana.tenisdemesa.model.enums.Modalidade;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Medalha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonato;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoMedalha tipo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Modalidade modalidade;

    public Medalha() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Campeonato getCampeonato() { return campeonato; }
    public void setCampeonato(Campeonato campeonato) { this.campeonato = campeonato; }
    public TipoMedalha getTipo() { return tipo; }
    public void setTipo(TipoMedalha tipo) { this.tipo = tipo; }
    public Modalidade getModalidade() { return modalidade; }
    public void setModalidade(Modalidade modalidade) { this.modalidade = modalidade; }
}
