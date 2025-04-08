package busca;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip {
    public static void extrairZip(String zipPath, String destino) throws IOException {
        File pastaDestino = new File(destino);
        if (!pastaDestino.exists()) {
            pastaDestino.mkdirs();
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entrada;
            while ((entrada = zis.getNextEntry()) != null) {
                File novoArquivo = new File(destino, entrada.getName());
                if (entrada.isDirectory()) {
                    novoArquivo.mkdirs();
                    continue;
                }
                // Garante que o diretório pai do arquivo existe
                File parent = novoArquivo.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                try (FileOutputStream fos = new FileOutputStream(novoArquivo)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
            }
        }
    }
}
