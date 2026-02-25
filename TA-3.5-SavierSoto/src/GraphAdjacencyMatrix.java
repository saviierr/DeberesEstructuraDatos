package src;

import java.util.*;

public class GraphAdjacencyMatrix implements Graph {
    private boolean[][] adjMatrix;
    private int numVertices;

    public GraphAdjacencyMatrix(int numVertices) {
        this.numVertices = numVertices;
        this.adjMatrix = new boolean[numVertices][numVertices];
    }

    @Override
    public void addEdge(int u, int v) {
        adjMatrix[u][v] = true;
        adjMatrix[v][u] = true; // Grafo no dirigido
    }

    @Override
    public List<Integer> bfs(int startNode) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();

        visited[startNode] = true;
        queue.add(startNode);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            result.add(curr);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[curr][i] && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
        return result;
    }

    @Override
    public List<Integer> dfs(int startNode) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        dfsHelper(startNode, visited, result);
        return result;
    }

    private void dfsHelper(int u, boolean[] visited, List<Integer> result) {
        visited[u] = true;
        result.add(u);
        for (int i = 0; i < numVertices; i++) {
            if (adjMatrix[u][i] && !visited[i]) {
                dfsHelper(i, visited, result);
            }
        }
    }

    @Override
    public int getNumVertices() {
        return numVertices;
    }
}
