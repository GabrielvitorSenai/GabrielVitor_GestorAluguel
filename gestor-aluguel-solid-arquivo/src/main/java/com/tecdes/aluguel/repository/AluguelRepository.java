package com.tecdes.aluguel.repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AluguelRepository {

    private final File arquivo;

    public AluguelRepository(String caminhoArquivo) {
        this.arquivo = new File(caminhoArquivo);
        criarSeNecessario();
    }

    private void criarSeNecessario() {
        try {
            File dir = arquivo.getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível preparar o arquivo de dados: " + e.getMessage(), e);
        }
    }

    public void salvar(String linha) {
        try (var fw = new FileWriter(arquivo, true);
             var bw = new BufferedWriter(fw)) {
            bw.write(linha);
            if (!linha.endsWith("\n")) bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar histórico: " + e.getMessage(), e);
        }
    }

    public List<String> listar() {
        List<String> linhas = new ArrayList<>();
        try (var fr = new FileReader(arquivo, StandardCharsets.UTF_8);
             var br = new BufferedReader(fr)) {
            String l;
            while ((l = br.readLine()) != null) {
                linhas.add(l);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler histórico: " + e.getMessage(), e);
        }
        return linhas;
    }
}
