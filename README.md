# Implementación de Algoritmos de Floyd

Este repositorio contiene la implementación de varios algoritmos de grafos en Java. Proporciona funcionalidades para encontrar la ruta más corta entre ciudades, determinar la ciudad central en el grafo y modificar el grafo agregando o eliminando conexiones entre ciudades.

## Clases y Métodos

### Clase Main

La clase `Main` contiene el método `main` que sirve como punto de entrada de la aplicación. Incluye una interfaz de línea de comandos para interactuar con el usuario.

#### Métodos:
- `main(String[] args)`: Inicia la aplicación.
- `modificarMatrizAdyacencia(Grafo grafo, Scanner scanner)`: Maneja la modificación de la matriz de adyacencia.
- `obtenerEnteroValido(Scanner scanner)`: Asegura la entrada de un número entero válido.

### Clase Grafo

La clase `Grafo` encapsula la estructura de datos del grafo y los algoritmos para manipular y analizar el grafo.

#### Atributos:
- `INF`: Representa una distancia infinita.
- `matrizAdyacencia`: La matriz de adyacencia del grafo.
- `nodos`: Un mapa de nombres de ciudades a sus índices.
- `distancia`: La distancia calculada por el algoritmo de Floyd-Warshall.

#### Métodos:
- `setDistancia(int distancia)`, `getDistancia()`: Establece y obtiene la distancia.
- `ejecutarFloydWarshall(String nombreArchivo, String ciudadOrigen, String ciudadDestino)`: Ejecuta el algoritmo de Floyd-Warshall.
- `encontrarCentroGrafo(String nombreArchivo)`: Encuentra la ciudad central del grafo.
- `eliminarConexion(String nombreArchivo, String ciudadOrigen, String ciudadDestino)`: Elimina una conexión entre dos ciudades.
- `agregarConexion(String nombreArchivo, String ciudadOrigen, String ciudadDestino, int peso)`: Agrega una conexión entre dos ciudades.
- `getMatrizAdyacencia()`, `getNodos()`, `getInf()`: Métodos para obtener varios atributos.

### Descripción de Métodos:

#### `modificarMatrizAdyacencia(Grafo grafo, Scanner scanner)`
Este método maneja las opciones de modificación del grafo permitiendo al usuario eliminar o agregar conexiones entre ciudades.

#### `obtenerEnteroValido(Scanner scanner)`
Este método asegura que el usuario ingrese un número entero válido, solicitando repetidamente la entrada hasta que se proporcione un valor correcto.

#### `ejecutarFloydWarshall(String nombreArchivo, String ciudadOrigen, String ciudadDestino)`
Este método ejecuta el algoritmo de Floyd-Warshall para encontrar la ruta más corta entre dos ciudades. Lee la matriz de adyacencia desde un archivo y calcula las distancias más cortas.

#### `encontrarCentroGrafo(String nombreArchivo)`
Este método encuentra la ciudad central del grafo utilizando la excentricidad de los nodos. Lee la matriz de adyacencia desde un archivo, ejecuta el algoritmo de Floyd-Warshall y calcula la excentricidad de cada nodo para determinar el centro.

#### `eliminarConexion(String nombreArchivo, String ciudadOrigen, String ciudadDestino)`
Este método elimina una conexión entre dos ciudades especificadas, actualizando la matriz de adyacencia y escribiendo los cambios en el archivo.

#### `agregarConexion(String nombreArchivo, String ciudadOrigen, String ciudadDestino, int peso)`
Este método agrega una conexión entre dos ciudades con un peso especificado, actualizando la matriz de adyacencia y escribiendo los cambios en el archivo.

#### `expandirMatrizAdyacencia()`
Este método expande la matriz de adyacencia cuando se agrega un nuevo nodo, asegurando que la nueva matriz tenga el tamaño adecuado y mantenga las conexiones existentes.

#### `leerMatrizAdyacencia(String nombreArchivo, Map<String, Integer> nodos)`
Este método lee la matriz de adyacencia desde un archivo y construye el mapa de nodos.

#### `escribirMatrizAdyacencia(String nombreArchivo)`
Este método escribe la matriz de adyacencia actualizada en el archivo especificado.

#### `getKeyByValue(Map<String, Integer> map, int value)`
Este método obtiene la clave (nombre de la ciudad) correspondiente a un valor dado (índice del nodo) en el mapa de nodos.
