package com.ana.tenisdemesa.model;

import com.ana.tenisdemesa.model.enums.Modalidade;
import com.ana.tenisdemesa.model.enums.TipoMedalha;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(indexes = @Index(name = "idx_medalha_campeonato", columnList = "campeonato_id"))
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

    @Transient
    public String getTipoCssClass() {
        return switch (tipo) {
            case OURO -> "bi-star-fill text-warning";
            case PRATA -> "bi-star-fill text-secondary";
            case BRONZE -> "bi-star-fill";
        };
    }

    @Transient
    public String getTipoLabel() {
        return switch (tipo) {
            case OURO -> "OURO";
            case PRATA -> "PRATA";
            case BRONZE -> "BRONZE";
        };
    }

    @Transient
    public String getTipoColorStyle() {
        return tipo == TipoMedalha.BRONZE ? "color: #cd7f32" : "";
    }

    @Transient
    public boolean isOuro() {
        return tipo == TipoMedalha.OURO;
    }

    @Transient
    public boolean isPrata() {
        return tipo == TipoMedalha.PRATA;
    }

    @Transient
    public boolean isBronze() {
        return tipo == TipoMedalha.BRONZE;
    }

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
