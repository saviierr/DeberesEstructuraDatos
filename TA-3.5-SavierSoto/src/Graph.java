package src;

import java.util.List;

public interface Graph {
    /**
     * Agrega una arista no dirigida entre u y v.
     */
    void addEdge(int u, int v);

    /**
     * Realiza una búsqueda en anchura (BFS) desde un nodo de inicio.
     */
    List<Integer> bfs(int startNode);

    /**
     * Realiza una búsqueda en profundidad (DFS) desde un nodo de inicio.
     */
    List<Integer> dfs(int startNode);

    /**
     * Devuelve el número de vértices en el grafo.
     */
    int getNumVertices();
}
