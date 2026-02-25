package src;

import java.util.*;

public class GraphAdjacencyList implements Graph {
    private List<List<Integer>> adjList;
    private int numVertices;

    public GraphAdjacencyList(int numVertices) {
        this.numVertices = numVertices;
        this.adjList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    @Override
    public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u); // Grafo no dirigido
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

            for (int neighbor : adjList.get(curr)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
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
        for (int neighbor : adjList.get(u)) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor, visited, result);
            }
        }
    }

    @Override
    public int getNumVertices() {
        return numVertices;
    }
}
