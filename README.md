# Algoritmo_Floyd_HDT10


    public static void main(String[] args) {
        String nombreArchivo = "guategrafo.txt";

        try {
            Map<String, Integer> nodos = new HashMap<>();
            int[][] matrizAdyacencia = leerMatrizAdyacencia(nombreArchivo, nodos);
            floydWarshall(matrizAdyacencia, nodos);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }