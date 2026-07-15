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

    /** Placar simples (sem pontuação por set): total de sets da atleta e do adversário. */
    private Integer setsAtleta;

    private Integer setsAdversario;

    @Transient
    public boolean isPlacarDetalhado() {
        return !sets.isEmpty();
    }

    @Transient
    private long contarSetsVencidos() {
        if (!sets.isEmpty()) {
            return sets.stream()
                    .filter(s -> s.getPontosAtleta() > s.getPontosAdversario())
                    .count();
        }
        return setsAtleta != null ? setsAtleta : 0;
    }

    @Transient
    private long contarSetsPerdidos() {
        if (!sets.isEmpty()) {
            return sets.size() - contarSetsVencidos();
        }
        return setsAdversario != null ? setsAdversario : 0;
    }

    @Transient
    public ResultadoPartida getResultado() {
        if (sets.isEmpty() && setsAtleta == null && setsAdversario == null) return null;
        long setsVencidos = contarSetsVencidos();
        long setsPerdidos = contarSetsPerdidos();
        if (setsVencidos > setsPerdidos) return ResultadoPartida.VITORIA;
        if (setsPerdidos > setsVencidos) return ResultadoPartida.DERROTA;
        return null;
    }

    @Transient
    public String getPlacar() {
        return contarSetsVencidos() + "-" + contarSetsPerdidos();
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
    public Integer getSetsAtleta() { return setsAtleta; }
    public void setSetsAtleta(Integer setsAtleta) { this.setsAtleta = setsAtleta; }
    public Integer getSetsAdversario() { return setsAdversario; }
    public void setSetsAdversario(Integer setsAdversario) { this.setsAdversario = setsAdversario; }
}
