package org.example;

import java.util.List;

public class Substituir {
    // Método que substitui valores das colunas OD e AMB na tabela extraída
    public static List<String[]> substituir(List<String[]> tabela) {
        if (tabela == null || tabela.isEmpty()) {
            throw new IllegalArgumentException("⚠ Erro: Tabela vazia ou nula.");
        }

        // Obtém o cabeçalho para encontrar as colunas OD e AMB
        String[] cabecalho = tabela.get(0);
        int colunaOD = -1, colunaAMB = -1;

        // Percorre as colunas para encontrar os índices de OD e AMB
        for (int i = 0; i < cabecalho.length; i++) {
            String coluna = cabecalho[i].trim().toLowerCase();
            if (coluna.contains("od")) colunaOD = i;
            if (coluna.contains("amb")) colunaAMB = i;
        }

        // Se nenhuma coluna relevante for encontrada, a substituição não será feita
        if (colunaOD == -1 && colunaAMB == -1) {
            System.out.println("⚠ Nenhuma coluna relevante encontrada. Nenhuma substituição realizada.");
            return tabela;
        }

        // Percorre todas as linhas da tabela, começando após o cabeçalho
        for (int linhaIndex = 1; linhaIndex < tabela.size(); linhaIndex++) {
            String[] linha = tabela.get(linhaIndex);

            // Substitui os valores da coluna OD, se a coluna existir
            if (colunaOD != -1 && linha.length > colunaOD) {
                linha[colunaOD] = traduzirOD(linha[colunaOD]);
            }

            // Substitui os valores da coluna AMB, se a coluna existir
            if (colunaAMB != -1 && linha.length > colunaAMB) {
                linha[colunaAMB] = traduzirAMB(linha[colunaAMB]);
            }
        }

        System.out.println("✅ Substituições concluídas com sucesso!");
        return tabela;
    }

    // Método para traduzir os valores da coluna OD (Odontológico)
    private static String traduzirOD(String valor) {
        valor = valor.trim(); // Remove espaços extras
        return valor.equalsIgnoreCase("S") ? "Seg. Odontológica" :
                valor.equalsIgnoreCase("N") ? "Não Seg. Odontológica" : valor;
    }

    // Método para traduzir os valores da coluna AMB (Ambulatorial)
    private static String traduzirAMB(String valor) {
        valor = valor.trim(); // Remove espaços extras
        return valor.equalsIgnoreCase("S") ? "Seg. Ambulatorial" :
                valor.equalsIgnoreCase("N") ? "Não Seg. Ambulatorial" : valor;
    }
}
