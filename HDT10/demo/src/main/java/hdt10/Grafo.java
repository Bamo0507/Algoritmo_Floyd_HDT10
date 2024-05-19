package hdt10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Grafo {

    private static final int INF = Integer.MAX_VALUE;
    private int[][] matrizAdyacencia;
    private Map<String, Integer> nodos;

    public void ejecutarFloydWarshall(String nombreArchivo) {
        try {
            nodos = new HashMap<>();
            matrizAdyacencia = leerMatrizAdyacencia(nombreArchivo, nodos);
            floydWarshall(matrizAdyacencia, nodos);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private int[][] leerMatrizAdyacencia(String nombreArchivo, Map<String, Integer> nodos) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
        String linea;
        int numNodos = 0;

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(" ");
            String nodo1 = partes[0];
            String nodo2 = partes[1];

            if (!nodos.containsKey(nodo1)) {
                nodos.put(nodo1, numNodos++);
            }
            if (!nodos.containsKey(nodo2)) {
                nodos.put(nodo2, numNodos++);
            }
        }

        int[][] matrizAdyacencia = new int[numNodos][numNodos];

        for (int i = 0; i < numNodos; i++) {
            for (int j = 0; j < numNodos; j++) {
                matrizAdyacencia[i][j] = (i == j) ? 0 : INF;
            }
        }

        br = new BufferedReader(new FileReader(nombreArchivo));

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(" ");
            String nodo1 = partes[0];
            String nodo2 = partes[1];
            int peso = Integer.parseInt(partes[2]);

            int indice1 = nodos.get(nodo1);
            int indice2 = nodos.get(nodo2);

            matrizAdyacencia[indice1][indice2] = peso; // Conexión dirigida
        }

        br.close();
        return matrizAdyacencia;
    }

    private void floydWarshall(int[][] matrizAdyacencia, Map<String, Integer> nodos) {
        int n = matrizAdyacencia.length;
        int[][] distancias = new int[n][n];
        int[][] siguientes = new int[n][n];

        // Inicializar las distancias y los siguientes
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distancias[i][j] = matrizAdyacencia[i][j];
                if (matrizAdyacencia[i][j] != INF && i != j) {
                    siguientes[i][j] = j;
                } else {
                    siguientes[i][j] = -1;
                }
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] != INF && distancias[k][j] != INF &&
                        distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        siguientes[i][j] = siguientes[i][k];
                    }
                }
            }
        }

        // Solicitar entrada del usuario
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la ciudad de origen: ");
        String ciudadOrigen = scanner.nextLine();
        System.out.print("Ingrese la ciudad de destino: ");
        String ciudadDestino = scanner.nextLine();

        if (!nodos.containsKey(ciudadOrigen) || !nodos.containsKey(ciudadDestino)) {
            System.out.println("Una o ambas ciudades no existen en el grafo.");
            return;
        }

        int origen = nodos.get(ciudadOrigen);
        int destino = nodos.get(ciudadDestino);

        if (distancias[origen][destino] == INF) {
            System.out.println("No hay camino disponible entre " + ciudadOrigen + " y " + ciudadDestino + ".");
        } else {
            System.out.println("El camino más corto desde " + ciudadOrigen + " hasta " + ciudadDestino + " es:");
            System.out.println("Distancia: " + distancias[origen][destino] + " km");
            System.out.print("Ruta: " + ciudadOrigen);
            int siguiente = siguientes[origen][destino];
            while (siguiente != -1) {
                System.out.print(" -> " + getKeyByValue(nodos, siguiente));
                siguiente = siguientes[siguiente][destino];
            }
            System.out.println();
        }
    }

    public void encontrarCentroGrafo(String nombreArchivo) {
        try {
            nodos = new HashMap<>();
            matrizAdyacencia = leerMatrizAdyacencia(nombreArchivo, nodos);

            // Ejecutar el algoritmo de Floyd-Warshall para obtener todas las distancias más cortas
            int[][] distancias = new int[matrizAdyacencia.length][matrizAdyacencia.length];
            for (int i = 0; i < matrizAdyacencia.length; i++) {
                System.arraycopy(matrizAdyacencia[i], 0, distancias[i], 0, matrizAdyacencia.length);
            }

            for (int k = 0; k < matrizAdyacencia.length; k++) {
                for (int i = 0; i < matrizAdyacencia.length; i++) {
                    for (int j = 0; j < matrizAdyacencia.length; j++) {
                        if (distancias[i][k] != INF && distancias[k][j] != INF &&
                            distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                            distancias[i][j] = distancias[i][k] + distancias[k][j];
                        }
                    }
                }
            }

            // Calcular la excentricidad de cada nodo
            int[] excentricidades = new int[matrizAdyacencia.length];
            for (int i = 0; i < matrizAdyacencia.length; i++) {
                int maxDistancia = 0;
                for (int j = 0; j < matrizAdyacencia.length; j++) {
                    if (distancias[i][j] != INF && distancias[i][j] > maxDistancia) {
                        maxDistancia = distancias[i][j];
                    }
                }
                excentricidades[i] = maxDistancia;
            }

            // Encontrar el nodo con la mínima excentricidad
            int centro = 0;
            for (int i = 1; i < excentricidades.length; i++) {
                if (excentricidades[i] < excentricidades[centro]) {
                    centro = i;
                }
            }

            // Mostrar el centro del grafo
            System.out.println("El centro del grafo es: " + getKeyByValue(nodos, centro));

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void eliminarConexion(String nombreArchivo, String ciudadOrigen, String ciudadDestino) {
        if (!nodos.containsKey(ciudadOrigen) || !nodos.containsKey(ciudadDestino)) {
            System.out.println("Una o ambas ciudades no existen en el grafo.");
            return;
        }

        int origen = nodos.get(ciudadOrigen);
        int destino = nodos.get(ciudadDestino);

        if (matrizAdyacencia[origen][destino] == INF) {
            System.out.println("No existe una conexión entre " + ciudadOrigen + " y " + ciudadDestino + ".");
        } else {
            matrizAdyacencia[origen][destino] = INF;
            System.out.println("Conexión entre " + ciudadOrigen + " y " + ciudadDestino + " eliminada.");
            escribirMatrizAdyacencia(nombreArchivo);
        }
    }

    public void agregarConexion(String nombreArchivo, String ciudadOrigen, String ciudadDestino, int peso) {
        if (!nodos.containsKey(ciudadOrigen)) {
            nodos.put(ciudadOrigen, nodos.size());
            expandirMatrizAdyacencia();
        }
        if (!nodos.containsKey(ciudadDestino)) {
            nodos.put(ciudadDestino, nodos.size());
            expandirMatrizAdyacencia();
        }

        int origen = nodos.get(ciudadOrigen);
        int destino = nodos.get(ciudadDestino);

        if (matrizAdyacencia[origen][destino] != INF) {
            System.out.println("Ya existe una conexión entre " + ciudadOrigen + " y " + ciudadDestino + ".");
        } else {
            matrizAdyacencia[origen][destino] = peso;
            System.out.println("Conexión entre " + ciudadOrigen + " y " + ciudadDestino + " agregada con un peso de " + peso + ".");
            escribirMatrizAdyacencia(nombreArchivo);
        }
    }

    private void expandirMatrizAdyacencia() {
        int nuevoTamano = nodos.size();
        int[][] nuevaMatriz = new int[nuevoTamano][nuevoTamano];

        for (int i = 0; i < nuevoTamano; i++) {
            for (int j = 0; j < nuevoTamano; j++) {
                if (i < matrizAdyacencia.length && j < matrizAdyacencia.length) {
                    nuevaMatriz[i][j] = matrizAdyacencia[i][j];
                } else {
                    nuevaMatriz[i][j] = (i == j) ? 0 : INF;
                }
            }
        }

        matrizAdyacencia = nuevaMatriz;
    }

    private void escribirMatrizAdyacencia(String nombreArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Map.Entry<String, Integer> entry : nodos.entrySet()) {
                String ciudad1 = entry.getKey();
                int idx1 = entry.getValue();
                for (Map.Entry<String, Integer> entry2 : nodos.entrySet()) {
                    String ciudad2 = entry2.getKey();
                    int idx2 = entry2.getValue();
                    if (matrizAdyacencia[idx1][idx2] != INF && idx1 != idx2) {
                        bw.write(ciudad1 + " " + ciudad2 + " " + matrizAdyacencia[idx1][idx2]);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    private String getKeyByValue(Map<String, Integer> map, int value) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
