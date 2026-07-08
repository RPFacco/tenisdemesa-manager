package com.ana.tenisdemesa.model;

import com.ana.tenisdemesa.model.enums.FasePartida;
import com.ana.tenisdemesa.model.enums.ResultadoPartida;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
    @Index(name = "idx_partida_data", columnList = "data"),
    @Index(name = "idx_partida_campeonato", columnList = "campeonato_id")
})
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonato;

    @NotBlank
    private String adversario;

    @Enumerated(EnumType.STRING)
    private FasePartida fase;

    @NotNull
    private LocalDate data;

    @ElementCollection
    @CollectionTable(name = "partida_sets", joinColumns = @JoinColumn(name = "partida_id"))
    @OrderColumn(name = "set_order")
    private List<SetPartida> sets = new ArrayList<>();

    private String linkYoutube;

    @Transient
    public ResultadoPartida getResultado() {
        long setsVencidos = sets.stream()
                .filter(s -> s.getPontosAtleta() > s.getPontosAdversario())
                .count();
        long setsPerdidos = sets.size() - setsVencidos;
        if (setsVencidos > setsPerdidos) return ResultadoPartida.VITORIA;
        if (setsPerdidos > setsVencidos) return ResultadoPartida.DERROTA;
        return null;
    }

    @Transient
    public String getPlacar() {
        long setsVencidos = sets.stream()
                .filter(s -> s.getPontosAtleta() > s.getPontosAdversario())
                .count();
        long setsPerdidos = sets.size() - setsVencidos;
        return setsVencidos + "-" + setsPerdidos;
    }

    @Transient
    public boolean isVitoria() {
        return getResultado() == ResultadoPartida.VITORIA;
    }

    @Transient
    public boolean isDerrota() {
        return getResultado() == ResultadoPartida.DERROTA;
    }

    public Partida() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Campeonato getCampeonato() { return campeonato; }
    public void setCampeonato(Campeonato campeonato) { this.campeonato = campeonato; }
    public String getAdversario() { return adversario; }
    public void setAdversario(String adversario) { this.adversario = adversario; }
    public FasePartida getFase() { return fase; }
    public void setFase(FasePartida fase) { this.fase = fase; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public List<SetPartida> getSets() { return sets; }
    public void setSets(List<SetPartida> sets) { this.sets = sets; }
    public String getLinkYoutube() { return linkYoutube; }
    public void setLinkYoutube(String linkYoutube) { this.linkYoutube = linkYoutube; }
}
