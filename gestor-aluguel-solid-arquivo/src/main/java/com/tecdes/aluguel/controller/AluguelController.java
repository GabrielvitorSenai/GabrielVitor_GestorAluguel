package com.tecdes.aluguel.controller;

import java.util.List;

import com.tecdes.aluguel.model.Aluguel;
import com.tecdes.aluguel.repository.AluguelRepository;

public class AluguelController {

    private final AluguelRepository repository;

    public AluguelController(AluguelRepository repository) {
        this.repository = repository;
    }

    public String processarAluguel(Aluguel aluguel) {
        String resultado = aluguel.calcular();
        repository.salvar(resultado);
        return resultado;
    }

    public List<String> listarAlugueis() {
        return repository.listar();
    }
}
