package org.example;

import org.example.Csv;
import org.example.ExtrairDados;
import org.example.Substituir;
import org.example.Zip;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String zipPath = "D:\\WebScraping\\anexos_ans.zip"; // Caminho para o arquivo ZIP
        String tempDir = "temp"; // Diretório temporário para extração
        String csvPath = "Rol_de_Procedimentos.csv";
        String outputZipPath = "Teste_Carlos.zip";

        try {
            // Extrai o PDF do ZIP
            String pdfPath = Zip.extrairPdfDoZip(zipPath, tempDir);
            System.out.println("PDF extraído para: " + pdfPath);

            // Processa o PDF como antes
            List<String[]> dados = ExtrairDados.extrairTabela(pdfPath);
            List<String[]> dadosProcessados = Substituir.substituir(dados);
            Csv.salvarCSV(dadosProcessados, csvPath);
            Csv.compactarParaZip(csvPath, outputZipPath);

            System.out.println("Pronto! Arquivo criado: " + outputZipPath);

            // Limpa os arquivos temporários (opcional)
            Files.deleteIfExists(Path.of(pdfPath));
            Files.deleteIfExists(Path.of(csvPath));
            new File(tempDir).delete();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}