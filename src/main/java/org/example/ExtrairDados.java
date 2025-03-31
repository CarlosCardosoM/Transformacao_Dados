package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.extractors.BasicExtractionAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ExtrairDados {

    public static List<String[]> extrairTabela(String caminhoPDF) throws Exception {
        List<String[]> todasTabelas = new ArrayList<>();
        File arquivoPDF = new File(caminhoPDF);

        // Verifica se o arquivo PDF existe antes de processar
        if (!arquivoPDF.exists()) {
            throw new FileNotFoundException("Arquivo PDF não encontrado: " + caminhoPDF);
        }

        // Abre o documento PDF para leitura
        try (PDDocument documento = PDDocument.load(arquivoPDF)) {
            System.out.println("🔍 Iniciando extração de tabelas do PDF...");

            // Inicializa os algoritmos de extração
            ObjectExtractor extrator = new ObjectExtractor(documento);
            SpreadsheetExtractionAlgorithm algoritmoTabela = new SpreadsheetExtractionAlgorithm(); // Melhor para tabelas bem estruturadas
            BasicExtractionAlgorithm algoritmoBasico = new BasicExtractionAlgorithm(); // Alternativa para tabelas irregulares

            PageIterator pageIterator = extrator.extract(); // Obtém todas as páginas do PDF
            int totalPaginas = documento.getNumberOfPages(); // Conta o total de páginas

            // Percorre cada página do PDF
            while (pageIterator.hasNext()) {
                Page pagina = pageIterator.next();
                System.out.println("📄 Analisando página " + pagina.getPageNumber() + " de " + totalPaginas);

                // Tenta extrair tabelas usando o algoritmo mais preciso (Spreadsheet)
                List<Table> tabelas = algoritmoTabela.extract(pagina);

                // Se não encontrar tabelas bem estruturadas, tenta um método alternativo
                if (tabelas.isEmpty()) {
                    tabelas = algoritmoBasico.extract(pagina);
                }

                System.out.println("📊 Encontradas " + tabelas.size() + " tabelas nesta página");

                // Percorre cada tabela encontrada na página
                for (Table t : tabelas) {
                    if (t != null && !t.getRows().isEmpty()) {
                        System.out.println("  ✅ Tabela com " + t.getRows().size() + " linhas");

                        // Verifica se a tabela contém o cabeçalho esperado antes de processar
                        if (contemCabecalhoEsperado(t)) {
                            System.out.println("  ★ Tabela válida detectada!");

                            // Percorre todas as linhas da tabela
                            for (List<RectangularTextContainer> linha : t.getRows()) {
                                if (linha != null) {
                                    String[] dadosLinha = new String[linha.size()];

                                    // Percorre cada célula da linha e extrai o texto
                                    for (int i = 0; i < linha.size(); i++) {
                                        dadosLinha[i] = (linha.get(i) != null) ? linha.get(i).getText().trim() : "";
                                    }

                                    // Adiciona a linha extraída à lista de tabelas
                                    todasTabelas.add(dadosLinha);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Se nenhuma tabela for encontrada, lança um erro
        if (todasTabelas.isEmpty()) {
            throw new RuntimeException("⚠ Nenhuma tabela foi encontrada no PDF.");
        }

        return todasTabelas;
    }

    private static boolean contemCabecalhoEsperado(Table tabela) {
        if (tabela == null || tabela.getRows().isEmpty()) {
            return false; // Retorna falso se a tabela estiver vazia
        }

        List<RectangularTextContainer> primeiraLinha = tabela.getRows().get(0);
        if (primeiraLinha == null || primeiraLinha.isEmpty()) {
            return false; // Retorna falso se a primeira linha (cabeçalho) estiver vazia
        }

        StringBuilder cabecalho = new StringBuilder();

        // Percorre as primeiras 5 colunas da linha do cabeçalho para verificar palavras-chave
        for (int i = 0; i < Math.min(5, primeiraLinha.size()); i++) {
            if (primeiraLinha.get(i) != null) {
                cabecalho.append(primeiraLinha.get(i).getText().toLowerCase()).append(" ");
            }
        }

        // Verifica se o cabeçalho contém palavras relacionadas à tabela desejada
        String textoCabecalho = cabecalho.toString();
        return textoCabecalho.contains("procedimento") &&
                textoCabecalho.contains("vigência");
    }
}
