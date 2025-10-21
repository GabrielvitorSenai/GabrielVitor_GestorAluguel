package com.tecdes.aluguel;

import javax.swing.SwingUtilities;

import com.tecdes.aluguel.controller.AluguelController;
import com.tecdes.aluguel.repository.AluguelRepository;
import com.tecdes.aluguel.view.AluguelForm;

public class AppAluguel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            var repo = new AluguelRepository("data/alugueis.txt");
            var controller = new AluguelController(repo);
            new AluguelForm(controller).setVisible(true);
        });
    }
}
