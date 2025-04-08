package busca;

import java.io.*;
import java.util.concurrent.Semaphore;

public class BuscaThread implements Runnable {
    private final File arquivo;
    private final String termo;
    private final Semaphore semaforo;

    public BuscaThread(File arquivo, String termo, Semaphore semaforo) {
        this.arquivo = arquivo;
        this.termo = termo.toLowerCase();
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        try {
            semaforo.acquire();
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                String linha;
                int numLinha = 0;
                while ((linha = reader.readLine()) != null) {
                    numLinha++;
                    if (linha.toLowerCase().contains(termo)) {
                        System.out.printf("%s - linha: %02d - %s%n",
                                arquivo.getName(), numLinha, linha);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler " + arquivo.getName());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaforo.release();
        }
    }
}
