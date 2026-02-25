package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Locale;

public class Benchmark {
    private static final int NUM_VERTICES = 10000;
    private static final double DENSE_PROB = 0.5; // 50% de probabilidad de aristas
    private static final double SPARSE_PROB = 0.005; // 0.5% de probabilidad de aristas

    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando Benchmarks de Grafos...");

        FileWriter memWriter = new FileWriter("memory_results.csv");
        memWriter.write("Implementation,Density,Vertices,MemoryUsageMB\n");

        FileWriter timeWriter = new FileWriter("time_results.csv");
        timeWriter.write("Implementation,Density,Operation,TimeNS\n");

        System.out.println("\n--- Midiendo Memoria ---");

        // Benchmark de Memoria
        measureMemory(memWriter, "AdjacencyMatrix", "Dense", DENSE_PROB);
        measureMemory(memWriter, "AdjacencyList", "Dense", DENSE_PROB);
        measureMemory(memWriter, "AdjacencyMatrix", "Sparse", SPARSE_PROB);
        measureMemory(memWriter, "AdjacencyList", "Sparse", SPARSE_PROB);

        memWriter.close();

        System.out.println("\n--- Midiendo Tiempo ---");
        // Benchmark de Tiempo
        // Denso
        System.out.println("Generando grafos densos...");
        Graph matrixDense = createGraph("AdjacencyMatrix", DENSE_PROB);
        Graph listDense = createGraph("AdjacencyList", DENSE_PROB);

        benchmarkTime(timeWriter, matrixDense, "AdjacencyMatrix", "Dense");
        benchmarkTime(timeWriter, listDense, "AdjacencyList", "Dense");

        // Liberar memoria para evitar sobrecarga del Heap
        matrixDense = null;
        listDense = null;
        System.gc();

        // Disperso
        System.out.println("\nGenerando grafos dispersos...");
        Graph matrixSparse = createGraph("AdjacencyMatrix", SPARSE_PROB);
        Graph listSparse = createGraph("AdjacencyList", SPARSE_PROB);

        benchmarkTime(timeWriter, matrixSparse, "AdjacencyMatrix", "Sparse");
        benchmarkTime(timeWriter, listSparse, "AdjacencyList", "Sparse");

        timeWriter.close();
        System.out.println("\nBenchmarks completados con exito.");
    }

    private static void measureMemory(FileWriter writer, String implType, String densityType, double prob)
            throws IOException {
        System.gc();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }

        long memBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        Graph g = createGraph(implType, prob);

        long memAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        double memUsedMB = (memAfter - memBefore) / (1024.0 * 1024.0);

        // Escribimos con Locale.US para asegurar que el separador decimal sea un punto
        writer.write(String.format(Locale.US, "%s,%s,%d,%.4f\n", implType, densityType, NUM_VERTICES,
                Math.max(0, memUsedMB)));
        System.out.printf(Locale.US, "Memoria %s (%s): %.4f MB\n", implType, densityType, memUsedMB);

        g = null;
        System.gc(); // Forzar limpieza
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    private static Graph createGraph(String implType, double edgeProb) {
        Graph g;
        if (implType.equals("AdjacencyMatrix")) {
            g = new GraphAdjacencyMatrix(NUM_VERTICES);
        } else {
            g = new GraphAdjacencyList(NUM_VERTICES);
        }

        Random rand = new Random(42); // Semilla fija para consistencia
        for (int i = 0; i < NUM_VERTICES; i++) {
            for (int j = i + 1; j < NUM_VERTICES; j++) {
                if (rand.nextDouble() < edgeProb) {
                    g.addEdge(i, j);
                }
            }
        }
        return g;
    }

    private static void benchmarkTime(FileWriter writer, Graph g, String implType, String densityType)
            throws IOException {
        int startNode = 0;

        // BFS
        System.gc();
        long startBFS = System.nanoTime();
        g.bfs(startNode);
        long timeBFS = System.nanoTime() - startBFS;
        writer.write(String.format(Locale.US, "%s,%s,BFS,%d\n", implType, densityType, timeBFS));
        System.out.printf(Locale.US, "Tiempo BFS para %s (%s): %.2f ms\n", implType, densityType,
                timeBFS / 1_000_000.0);

        // DFS
        System.gc();
        long startDFS = System.nanoTime();
        g.dfs(startNode);
        long timeDFS = System.nanoTime() - startDFS;
        writer.write(String.format(Locale.US, "%s,%s,DFS,%d\n", implType, densityType, timeDFS));
        System.out.printf(Locale.US, "Tiempo DFS para %s (%s): %.2f ms\n", implType, densityType,
                timeDFS / 1_000_000.0);
    }
}
