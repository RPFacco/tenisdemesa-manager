package com.ana.tenisdemesa.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class SetPartida {

    private int numeroSet;
    private int pontosAtleta;
    private int pontosAdversario;

    public SetPartida() {}

    public SetPartida(int numeroSet, int pontosAtleta, int pontosAdversario) {
        this.numeroSet = numeroSet;
        this.pontosAtleta = pontosAtleta;
        this.pontosAdversario = pontosAdversario;
    }

    public int getNumeroSet() { return numeroSet; }
    public void setNumeroSet(int numeroSet) { this.numeroSet = numeroSet; }
    public int getPontosAtleta() { return pontosAtleta; }
    public void setPontosAtleta(int pontosAtleta) { this.pontosAtleta = pontosAtleta; }
    public int getPontosAdversario() { return pontosAdversario; }
    public void setPontosAdversario(int pontosAdversario) { this.pontosAdversario = pontosAdversario; }
}
