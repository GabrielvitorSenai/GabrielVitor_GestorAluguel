package com.tecdes.aluguel.model;

import java.text.NumberFormat;
import java.util.Locale;

public class AluguelTemporada implements Aluguel {
    private final double valorMensal;
    private final int dias;

    public AluguelTemporada(double valorMensal, int dias) {
        this.valorMensal = valorMensal;
        this.dias = dias;
    }

    @Override
    public String calcular() {
        double valorDia = valorMensal / 30.0;
        double total = valorDia * dias + 150.0; // taxa limpeza R$150
        return "[TEMPORADA] " + formatar(total) + " (" + dias + " dias, " 
                + formatar(valorDia) + "/dia + limpeza R$ 150)";
    }

    private String formatar(double v) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(v);
    }
}
