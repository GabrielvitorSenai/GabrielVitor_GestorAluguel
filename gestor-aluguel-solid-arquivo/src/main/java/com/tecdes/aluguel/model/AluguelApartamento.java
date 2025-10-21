package com.tecdes.aluguel.model;

import java.text.NumberFormat;
import java.util.Locale;

public class AluguelApartamento implements Aluguel {
    private final double valorMensal;
    private final int meses;

    public AluguelApartamento(double valorMensal, int meses) {
        this.valorMensal = valorMensal;
        this.meses = meses;
    }

    @Override
    public String calcular() {
        double total = (valorMensal + 250.0) * meses; // condominio R$250/mês
        return "[APARTAMENTO] " + formatar(total) + " (" + meses + " meses, base R$ " 
                + formatar(valorMensal) + " + cond. R$ 250/mês)";
    }

    private String formatar(double v) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(v);
    }
}
