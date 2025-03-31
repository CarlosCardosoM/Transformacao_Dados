package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Zip {
    public static String extrairPdfDoZip(String caminhoZip, String destinoTemp) throws IOException {
        try (ZipFile zipFile = new ZipFile(caminhoZip)) {
            // Procura por um arquivo PDF no ZIP (assumindo que há apenas um)
            ZipEntry pdfEntry = null;
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".pdf")) {
                    pdfEntry = entry;
                    break;
                }
            }

            if (pdfEntry == null) {
                throw new FileNotFoundException("Nenhum arquivo PDF encontrado no ZIP");
            }

            // Cria o diretório temporário se não existir
            new File(destinoTemp).mkdirs();

            // Extrai o PDF para um arquivo temporário
            Path tempPdfPath = Paths.get(destinoTemp, pdfEntry.getName());
            try (InputStream is = zipFile.getInputStream(pdfEntry)) {
                Files.copy(is, tempPdfPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return tempPdfPath.toString();
        }
    }
}