import java.util.*;

public class Grafo {
    private Map<Integer, List<Aresta>> adjacencias;
    private int numVertices;

    public Grafo() {
        adjacencias = new HashMap<>();
        numVertices = 0;
    }

    // 1. Inserir Vértice
    public void inserirVertice(int id) {
        if (!adjacencias.containsKey(id)) {
            adjacencias.put(id, new ArrayList<>());
            numVertices++;
        }
    }

    // 2. Inserir Aresta
    public void inserirAresta(int origem, int destino, int peso) {
        adjacencias.computeIfAbsent(origem, k -> new ArrayList<>()).add(new Aresta(destino, peso));
        adjacencias.computeIfAbsent(destino, k -> new ArrayList<>()).add(new Aresta(origem, peso)); // Grafo não direcionado
    }

    // 3. Remover Vértice
    public void removerVertice(int id) {
        adjacencias.values().forEach(list -> list.removeIf(aresta -> aresta.destino == id));
        adjacencias.remove(id);
        numVertices--;
    }

    // 4. Remover Aresta
    public void removerAresta(int origem, int destino) {
        adjacencias.getOrDefault(origem, new ArrayList<>()).removeIf(aresta -> aresta.destino == destino);
        adjacencias.getOrDefault(destino, new ArrayList<>()).removeIf(aresta -> aresta.destino == origem);
    }

    // 5. Visualizar Grafo
    public void visualizarGrafo() {
        for (Map.Entry<Integer, List<Aresta>> entry : adjacencias.entrySet()) {
            int vertice = entry.getKey();
            List<Aresta> arestas = entry.getValue();
            System.out.print(vertice + ": ");
            for (Aresta aresta : arestas) {
                System.out.print("(" + aresta.destino + ", " + aresta.peso + ") ");
            }
            System.out.println();
        }
    }

    // 6. Informar Grau de um Vértice
    public int grauVertice(int vertice) {
        return adjacencias.getOrDefault(vertice, Collections.emptyList()).size();
    }

    // 7. Verificar se o Grafo é Conexo
    public boolean ehConexo() {
        if (numVertices == 0) return true;

        Set<Integer> visitados = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        int primeiroVertice = adjacencias.keySet().iterator().next();

        stack.push(primeiroVertice);

        while (!stack.isEmpty()) {
            int vertice = stack.pop();
            if (!visitados.contains(vertice)) {
                visitados.add(vertice);
                for (Aresta aresta : adjacencias.get(vertice)) {
                    if (!visitados.contains(aresta.destino)) {
                        stack.push(aresta.destino);
                    }
                }
            }
        }

        return visitados.size() == numVertices;
    }

    // 8. Converter para Matriz de Adjacência
    public int[][] matrizAdjacencia() {
        int tamanho = adjacencias.size();
        int[][] matriz = new int[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            Arrays.fill(matriz[i], 0); // Inicializa com zeros (sem arestas)
        }

        for (Map.Entry<Integer, List<Aresta>> entry : adjacencias.entrySet()) {
            int origem = entry.getKey();
            for (Aresta aresta : entry.getValue()) {
                matriz[origem][aresta.destino] = aresta.peso;
            }
        }

        return matriz;
    }

    // 9. Caminhamento em Amplitude (BFS)
    public void bfs(int inicio) {
        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> fila = new LinkedList<>();
        fila.add(inicio);

        while (!fila.isEmpty()) {
            int vertice = fila.poll();
            if (!visitados.contains(vertice)) {
                System.out.print(vertice + " ");
                visitados.add(vertice);
                for (Aresta aresta : adjacencias.get(vertice)) {
                    if (!visitados.contains(aresta.destino)) {
                        fila.add(aresta.destino);
                    }
                }
            }
        }
        System.out.println();
    }

    // 10. Caminhamento em Profundidade (DFS)
    public void dfs(int inicio) {
        Set<Integer> visitados = new HashSet<>();
        dfsRecursivo(inicio, visitados);
        System.out.println();
    }

    private void dfsRecursivo(int vertice, Set<Integer> visitados) {
        visitados.add(vertice);
        System.out.print(vertice + " ");
        for (Aresta aresta : adjacencias.get(vertice)) {
            if (!visitados.contains(aresta.destino)) {
                dfsRecursivo(aresta.destino, visitados);
            }
        }
    }

    // 11. Caminho Mínimo (Dijkstra)
    public Map<Integer, Integer> dijkstra(int inicio) {
        Map<Integer, Integer> distancias = new HashMap<>();
        PriorityQueue<Map.Entry<Integer, Integer>> prioridade = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (int vertice : adjacencias.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
        }
        distancias.put(inicio, 0);
        prioridade.add(new AbstractMap.SimpleEntry<>(inicio, 0));

        while (!prioridade.isEmpty()) {
            Map.Entry<Integer, Integer> entry = prioridade.poll();
            int verticeAtual = entry.getKey();
            int distanciaAtual = entry.getValue();

            for (Aresta aresta : adjacencias.get(verticeAtual)) {
                int novaDistancia = distanciaAtual + aresta.peso;
                if (novaDistancia < distancias.get(aresta.destino)) {
                    distancias.put(aresta.destino, novaDistancia);
                    prioridade.add(new AbstractMap.SimpleEntry<>(aresta.destino, novaDistancia));
                }
            }
        }

        return distancias;
    }

    // 12. Árvore Geradora Mínima (Prim)
    public List<Aresta> prim() {
        Set<Integer> visitados = new HashSet<>();
        List<Aresta> mst = new ArrayList<>();
        PriorityQueue<Aresta> arestas = new PriorityQueue<>(Comparator.comparingInt(a -> a.peso));

        if (adjacencias.isEmpty()) return mst;

        int verticeInicial = adjacencias.keySet().iterator().next();
        visitados.add(verticeInicial);
        arestas.addAll(adjacencias.get(verticeInicial));

        while (!arestas.isEmpty()) {
            Aresta aresta = arestas.poll();
            if (!visitados.contains(aresta.destino)) {
                visitados.add(aresta.destino);
                mst.add(aresta);
                for (Aresta vizinho : adjacencias.get(aresta.destino)) {
                    if (!visitados.contains(vizinho.destino)) {
                        arestas.add(vizinho);
                    }
                }
            }
        }

        return mst;
    }
}
