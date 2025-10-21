package com.tecdes.aluguel.model;

import java.text.NumberFormat;
import java.util.Locale;

public class AluguelComercial implements Aluguel {
    private final double valorMensal;
    private final int meses;

    public AluguelComercial(double valorMensal, int meses) {
        this.valorMensal = valorMensal;
        this.meses = meses;
    }

    @Override
    public String calcular() {
        double totalBase = valorMensal * meses;
        double total = totalBase;
        return "[COMERCIAL] " + formatar(total) + " (" + meses + " meses, R$ " 
                + formatar(valorMensal) + "/mês)";
    }

    private String formatar(double v) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(v);
    }
}
