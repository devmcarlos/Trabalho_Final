import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();
        grafo.inserirVertice(1);
        grafo.inserirVertice(2);
        grafo.inserirVertice(3);
        grafo.inserirVertice(4);

        grafo.inserirAresta(1, 2, 1);
        grafo.inserirAresta(1, 3, 4);
        grafo.inserirAresta(4, 1, 60);



        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Inserir Vértice");
            System.out.println("2. Inserir Aresta");
            System.out.println("3. Remover Vértice");
            System.out.println("4. Remover Aresta");
            System.out.println("5. Visualizar Grafo");
            System.out.println("6. Informar Grau de um Vértice");
            System.out.println("7. Verificar se o Grafo é Conexo");
            System.out.println("8. Converter para Matriz de Adjacência");
            System.out.println("9. Caminhamento em Amplitude (BFS)");
            System.out.println("10. Caminhamento em Profundidade (DFS)");
            System.out.println("11. Caminho Mínimo (Dijkstra)");
            System.out.println("12. Árvore Geradora Mínima (Prim)");
            System.out.println("13. Sair");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID do Vértice: ");
                    int idVertice = scanner.nextInt();
                    grafo.inserirVertice(idVertice);
                    break;

                case 2:
                    System.out.print("Digite o ID do Vértice de Origem: ");
                    int origem = scanner.nextInt();
                    System.out.print("Digite o ID do Vértice de Destino: ");
                    int destino = scanner.nextInt();
                    System.out.print("Digite o Peso da Aresta: ");
                    int peso = scanner.nextInt();
                    grafo.inserirAresta(origem, destino, peso);
                    break;

                case 3:
                    System.out.print("Digite o ID do Vértice a ser Removido: ");
                    int idRemover = scanner.nextInt();
                    grafo.removerVertice(idRemover);
                    break;

                case 4:
                    System.out.print("Digite o ID do Vértice de Origem da Aresta: ");
                    int origemAresta = scanner.nextInt();
                    System.out.print("Digite o ID do Vértice de Destino da Aresta: ");
                    int destinoAresta = scanner.nextInt();
                    grafo.removerAresta(origemAresta, destinoAresta);
                    break;

                case 5:
                    grafo.visualizarGrafo();
                    break;

                case 6:
                    System.out.print("Digite o ID do Vértice: ");
                    int idGrau = scanner.nextInt();
                    System.out.println("Grau do Vértice " + idGrau + ": " + grafo.grauVertice(idGrau));
                    break;

                case 7:
                    System.out.println("O grafo é conexo? " + grafo.ehConexo());
                    break;

                case 8:
                    int[][] matriz = grafo.matrizAdjacencia();
                    System.out.println("Matriz de Adjacência:");
                    for (int[] linha : matriz) {
                        for (int valor : linha) {
                            System.out.print(valor + " ");
                        }
                        System.out.println();
                    }
                    break;

                case 9:
                    System.out.print("Digite o Vértice Inicial: ");
                    int inicioBFS = scanner.nextInt();
                    grafo.bfs(inicioBFS);
                    break;

                case 10:
                    System.out.print("Digite o Vértice Inicial: ");
                    int inicioDFS = scanner.nextInt();
                    grafo.dfs(inicioDFS);
                    break;

                case 11:
                    System.out.print("Digite o Vértice Inicial: ");
                    int inicioDijkstra = scanner.nextInt();
                    Map<Integer, Integer> distancias = grafo.dijkstra(inicioDijkstra);
                    System.out.println("Distâncias Mínimas:");
                    for (Map.Entry<Integer, Integer> entry : distancias.entrySet()) {
                        System.out.println("Vértice " + entry.getKey() + ": " + entry.getValue());
                    }
                    break;

                case 12:
                    List<Aresta> mst = grafo.prim();
                    System.out.println("Árvore Geradora Mínima:");
                    for (Aresta aresta : mst) {
                        System.out.println("Destino: " + aresta.destino + ", Peso: " + aresta.peso);
                    }
                    break;

                case 13:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        }
    }
}
