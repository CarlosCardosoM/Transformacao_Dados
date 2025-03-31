# Projeto de Processamento de Arquivos CSV e ZIP

Este projeto é uma aplicação em Java que realiza a extração, processamento e compactação de arquivos CSV, incluindo a substituição de valores em colunas específicas. O fluxo de trabalho do projeto envolve a extração de um arquivo PDF de um arquivo ZIP, o processamento dos dados extraídos, a substituição de valores nas colunas e a geração de um arquivo CSV, que é posteriormente compactado em um arquivo ZIP.

## Funcionalidades

- **Extrair PDF de um arquivo ZIP**: O projeto pode extrair um arquivo PDF de um arquivo ZIP.
- **Processar o PDF**: Os dados do PDF extraído são processados, convertidos em formato de tabela e podem ter valores específicos substituídos.
- **Salvar Dados em CSV**: Os dados processados são salvos em um arquivo CSV.
- **Compactar o CSV em ZIP**: O arquivo CSV gerado é compactado em um arquivo ZIP.

## Dependências

Este projeto utiliza apenas a biblioteca padrão do Java. Certifique-se de ter o Java 8 ou superior instalado no seu sistema.

## Estrutura do Projeto

src/
  ├── org/example/
        ├── Csv.java             # Classe responsável por salvar CSV e compactar em ZIP
        ├── Zip.java             # Classe para manipulação de arquivos ZIP
        ├── ExtrairDados.java    # Classe para extrair dados do PDF
        ├── Substituir.java      # Classe para substituir valores nas colunas
        ├── Main.java            # Classe principal que orquestra a execução


## Como Usar

1. **Extrair o PDF de um arquivo ZIP**:
    - A classe `Zip` fornece um método `extrairPdfDoZip()` que pode ser utilizado para extrair um arquivo PDF de um arquivo ZIP.
    
    ```java
    String pdfPath = Zip.extrairPdfDoZip("caminho/do/arquivo.zip", "diretorio/temp");
    ```

2. **Processar os Dados**:
    - O arquivo PDF extraído é processado pela classe `ExtrairDados`, que converte os dados em formato de tabela.
    
    ```java
    List<String[]> dados = ExtrairDados.extrairTabela(pdfPath);
    ```

3. **Substituir Valores**:
    - A classe `Substituir` permite substituir valores específicos nas colunas `OD` e `AMB` da tabela de dados.
    
    ```java
    List<String[]> dadosProcessados = Substituir.substituir(dados);
    ```

4. **Salvar os Dados em um Arquivo CSV**:
    - Após o processamento, os dados são salvos em um arquivo CSV pela classe `Csv`.
    
    ```java
    Csv.salvarCSV(dadosProcessados, "Rol_de_Procedimentos.csv");
    ```

5. **Compactar o Arquivo CSV em ZIP**:
    - O arquivo CSV gerado pode ser compactado em um arquivo ZIP usando a classe `Csv`.
    
    ```java
    Csv.compactarParaZip("Rol_de_Procedimentos.csv", "ArquivoCompactado.zip");
    ```

## Exemplo de Execução

No arquivo `Main.java`, o fluxo completo de extração, processamento, substituição e compactação é demonstrado.

```java
public class Main {
    public static void main(String[] args) {
        try {
            // Extrai o PDF do arquivo ZIP
            String pdfPath = Zip.extrairPdfDoZip("D:\\WebScraping\\anexos_ans.zip", "temp");

            // Processa o PDF e gera a tabela de dados
            List<String[]> dados = ExtrairDados.extrairTabela(pdfPath);

            // Substitui valores nas colunas
            List<String[]> dadosProcessados = Substituir.substituir(dados);

            // Salva os dados em um arquivo CSV
            Csv.salvarCSV(dadosProcessados, "Rol_de_Procedimentos.csv");

            // Compacta o arquivo CSV em um arquivo ZIP
            Csv.compactarParaZip("Rol_de_Procedimentos.csv", "Teste_Carlos.zip");

            System.out.println("Processo concluído com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```
