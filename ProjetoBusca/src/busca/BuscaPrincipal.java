package busca;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuscaPrincipal {
    public static void main(String[] args) throws IOException, InterruptedException {
        String termoBusca = "di";

        // Define caminhos (supondo que a execução seja da raiz do projeto)
        Path basePath = Paths.get("").toAbsolutePath();
        String zipPath = basePath.resolve("src").resolve("arquivosNomes.zip").toString();
        String pastaDestino = basePath.resolve("src").resolve("arquivos").toString();

        // Extrai o ZIP para a pasta de destino
        Zip.extrairZip(zipPath, pastaDestino);

        // Lista recursivamente arquivos .txt usando Files.walk()
        List<File> arquivosTxt;
        try (Stream<Path> caminhos = Files.walk(Paths.get(pastaDestino))) {
            arquivosTxt = caminhos.filter(Files::isRegularFile)
                                  .filter(p -> p.toString().toLowerCase().endsWith(".txt"))
                                  .map(Path::toFile)
                                  .collect(Collectors.toList());
        }

        if (arquivosTxt.isEmpty()) {
            System.out.println("Nenhum arquivo .txt encontrado em: " + pastaDestino);
            return;
        }

        // Cria um semáforo que limita a 2 threads simultâneas
        Semaphore semaforo = new Semaphore(2);

        // Registra o tempo de início (em milissegundos)
        long tempoInicio = System.currentTimeMillis();

        // Usa ExecutorService para executar a busca em paralelo
        ExecutorService executor = Executors.newFixedThreadPool(arquivosTxt.size());
        for (File arquivo : arquivosTxt) {
            executor.execute(new BuscaThread(arquivo, termoBusca, semaforo));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Registra o tempo de término e calcula o tempo total de execução
        long tempoFim = System.currentTimeMillis();
        long tempoTotal = tempoFim - tempoInicio;

        System.out.println("Busca finalizada.");
        System.out.println("Tempo de execução: " + tempoTotal + " ms");
    }
}

