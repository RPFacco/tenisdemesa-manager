package com.ana.tenisdemesa.util;

import org.springframework.stereotype.Component;

@Component
public class TreinoHelper {

    public String formatDuracao(Double horas) {
        if (horas == null) return "—";
        int h = horas.intValue();
        int min = (int) Math.round((horas - h) * 60);
        if (h == 0) return min + "min";
        if (min == 0) return h + "h";
        return h + "h" + min + "min";
    }

    public int horasPart(Double horas) {
        if (horas == null) return 0;
        return horas.intValue();
    }

    public int minutosPart(Double horas) {
        if (horas == null) return 0;
        return (int) Math.round((horas - horas.intValue()) * 60);
    }
}
