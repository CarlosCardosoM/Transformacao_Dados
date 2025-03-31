package org.example;

import java.io.*;
import java.util.List;
import java.util.zip.*;

public class Csv {
    // Método para salvar os dados em um arquivo CSV
    public static void salvarCSV(List<String[]> dados, String caminho) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Obtém o número esperado de colunas com base no cabeçalho
            int numColunasEsperadas = dados.get(0).length;

            // Percorre todas as linhas da tabela
            for (String[] linha : dados) {
                // Ajusta o tamanho das colunas se necessário
                if (linha.length < numColunasEsperadas) {
                    String[] linhaCorrigida = new String[numColunasEsperadas];
                    System.arraycopy(linha, 0, linhaCorrigida, 0, linha.length);
                    linha = linhaCorrigida;
                }

                // Escreve a linha no arquivo CSV, separando os valores por vírgula
                writer.write(String.join(",", linha));
                writer.newLine(); // Adiciona quebra de linha
            }
        }
    }

    // Método para compactar um arquivo CSV em formato ZIP
    public static void compactarParaZip(String arquivoOrigem, String arquivoDestino) throws IOException {
        try (FileInputStream fis = new FileInputStream(arquivoOrigem);
             FileOutputStream fos = new FileOutputStream(arquivoDestino);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            // Cria uma entrada no ZIP para armazenar o CSV
            ZipEntry entrada = new ZipEntry(new File(arquivoOrigem).getName());
            zos.putNextEntry(entrada);

            byte[] buffer = new byte[1024];
            int tamanho;

            // Lê o arquivo CSV e escreve no ZIP
            while ((tamanho = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, tamanho);
            }
        }
    }
}
