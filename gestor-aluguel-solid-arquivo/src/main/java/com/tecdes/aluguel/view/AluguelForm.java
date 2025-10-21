package com.tecdes.aluguel.view;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tecdes.aluguel.controller.AluguelController;
import com.tecdes.aluguel.model.Aluguel;
import com.tecdes.aluguel.model.AluguelApartamento;
import com.tecdes.aluguel.model.AluguelCasa;
import com.tecdes.aluguel.model.AluguelComercial;
import com.tecdes.aluguel.model.AluguelTemporada;

public class AluguelForm extends JFrame {

    private final AluguelController controller;

    private JTextField txtValor;
    private JTextField txtMesesOuDias;
    private JComboBox<String> cmbTipo;
    private JTextArea txtResultado;
    private JLabel lblMesesOuDias;

    public AluguelForm(AluguelController controller) {
        this.controller = controller;
        setTitle("Gestor de Aluguéis");
        setSize(500, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JLabel titulo = new JLabel("Gestor de Aluguéis");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBounds(150, 10, 250, 30);
        add(titulo);

        JLabel lblValor = new JLabel("Valor mensal (R$):");
        lblValor.setBounds(40, 60, 140, 25);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(190, 60, 240, 25);
        add(txtValor);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(40, 100, 140, 25);
        add(lblTipo);

        cmbTipo = new JComboBox<>(new String[]{"Casa", "Apartamento", "Comercial", "Temporada"});
        cmbTipo.setBounds(190, 100, 240, 25);
        add(cmbTipo);

        lblMesesOuDias = new JLabel("Meses:");
        lblMesesOuDias.setBounds(40, 140, 140, 25);
        add(lblMesesOuDias);

        txtMesesOuDias = new JTextField();
        txtMesesOuDias.setBounds(190, 140, 240, 25);
        add(txtMesesOuDias);

        JButton btnProcessar = new JButton("Processar Aluguel");
        btnProcessar.setBounds(150, 190, 200, 35);
        add(btnProcessar);

        JButton btnRecarregar = new JButton("Recarregar Histórico");
        btnRecarregar.setBounds(150, 235, 200, 30);
        add(btnRecarregar);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setBounds(40, 290, 410, 210);
        add(scroll);

        // Eventos
        cmbTipo.addActionListener(e -> atualizarRotuloPeriodo());
        btnProcessar.addActionListener(e -> processar());
        btnRecarregar.addActionListener(e -> recarregar());
    }

    private void atualizarRotuloPeriodo() {
        String tipo = (String) cmbTipo.getSelectedItem();
        if ("Temporada".equals(tipo)) {
            lblMesesOuDias.setText("Dias:");
        } else {
            lblMesesOuDias.setText("Meses:");
        }
    }

    private void processar() {
        try {
            double valor = Double.parseDouble(txtValor.getText().trim());
            String tipo = (String) cmbTipo.getSelectedItem();
            int periodo = Integer.parseInt(txtMesesOuDias.getText().trim());
            if (valor <= 0 || periodo <= 0) throw new IllegalArgumentException();

            Aluguel aluguel = criarAluguel(tipo, valor, periodo);
            String msg = controller.processarAluguel(aluguel);
            txtResultado.append(msg + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe números válidos para valor e período.", 
                                          "Entrada inválida", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Valor e período devem ser maiores que zero.", 
                                          "Entrada inválida", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao processar: " + ex.getMessage(), 
                                          "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Aluguel criarAluguel(String tipo, double valorMensal, int periodo) {
        return switch (tipo) {
            case "Casa" -> new AluguelCasa(valorMensal, periodo);
            case "Apartamento" -> new AluguelApartamento(valorMensal, periodo);
            case "Comercial" -> new AluguelComercial(valorMensal, periodo);
            case "Temporada" -> new AluguelTemporada(valorMensal, periodo); // dias
            default -> throw new IllegalArgumentException("Tipo inválido: " + tipo);
        };
    }

    private void recarregar() {
        try {
            List<String> linhas = controller.listarAlugueis();
            if (linhas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Histórico vazio.");
                return;
            }
            txtResultado.append("\n--- Histórico Recarregado ---\n");
            for (String l : linhas) {
                txtResultado.append(l + "\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ler histórico: " + ex.getMessage(), 
                                          "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
