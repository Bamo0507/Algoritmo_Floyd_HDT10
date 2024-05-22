package hdt10;

import java.util.Scanner;

public class Main {
    private static String nombreArchivo = "guategrafo.txt";


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();
        

        //Variables para manejar menús
        boolean systemON = true;
        
        System.out.println("***********************************************************");
        System.out.println("*  __        _______ _     ____ ___  __  __ _____ _ _ _   *");
        System.out.println("*  \\ \\      / / ____| |   / ___/ _ \\|  \\/  | ____| | | |  *");
        System.out.println("*   \\ \\ /\\ / /|  _| | |  | |  | | | | |\\/| |  _| | | | |  *");
        System.out.println("*    \\ V  V / | |___| |__| |__| |_| | |  | | |___|_|_|_|  *");
        System.out.println("*     \\_/\\_/  |_____|_____\\____\\___/|_|  |_|_____(_|_|_)  *");
        System.out.println("***********************************************************");

        while (systemON) {
            System.out.println("¿Qué desea hacer hoy querido usuario?");
            System.out.println("1. Encontrar la mejor ruta entre dos ciudades");
            System.out.println("2. Conocer la ciudad que está en el centro");
            System.out.println("3. Modificar el mapa");
            System.out.println("4. Salir del programa");
            System.out.print("Seleccione una opción: ");

            String seleccion = scanner.nextLine();

            switch(seleccion){
                case "1":
                    //calcular la mejor ruta
                    System.out.print("Ingrese la ciudad de origen: ");
                    String ciudadOrigen = scanner.nextLine();
                    System.out.print("Ingrese la ciudad de destino: ");
                    String ciudadDestino = scanner.nextLine();
                    
                    System.out.println();
                    grafo.ejecutarFloydWarshall(nombreArchivo, ciudadOrigen, ciudadDestino);
                    System.out.println();
                    break;

                case "2":
                    //Encontrar centro del grafo
                    System.out.println();
                    grafo.encontrarCentroGrafo(nombreArchivo);
                    System.out.println();
                    break;

                case "3":
                    //Modificar el mapa
                    modificarMatrizAdyacencia(grafo, scanner);
                    break;

                case "4":
                    System.out.println("Que tenga un buen día querido usuario :)");
                    systemON = false;
                    break;

                default:
                    System.out.println("Por favor, selecciona una opción válida...");
            }            
        }
    }

    private static void modificarMatrizAdyacencia(Grafo grafo, Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Submenu:");
            System.out.println("1. Eliminar conexión entre dos ciudades");
            System.out.println("2. Conectar dos ciudades");
            System.out.println("3. Regresar al menú principal");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("1")) {
                System.out.print("Ingrese la ciudad de origen: ");
                String ciudadOrigen = scanner.nextLine();
                System.out.print("Ingrese la ciudad de destino: ");
                String ciudadDestino = scanner.nextLine();
                grafo.eliminarConexion(nombreArchivo, ciudadOrigen, ciudadDestino);
            } else if (opcion.equals("2")) {
                System.out.print("Ingrese la ciudad de origen: ");
                String ciudadOrigen = scanner.nextLine();
                System.out.print("Ingrese la ciudad de destino: ");
                String ciudadDestino = scanner.nextLine();
                System.out.print("Ingrese la distancia (peso) entre las ciudades: ");
                int peso = obtenerEnteroValido(scanner);
                System.out.println("Apache ENTER para hacer válido el cambio");
                scanner.nextLine(); // Consumir el salto de línea
                grafo.agregarConexion(nombreArchivo, ciudadOrigen, ciudadDestino, peso);
            } else if (opcion.equals("3")) {
                System.out.println("Regresando al menú principal...");
                System.out.println("\n\n");
                break;
            } else {
                System.out.println("Opción inválida, coloca una opción correcta");
            }
        }
    }

    //Método para asegurarse que se ingrese un entero en los campos necesarios
    public static int obtenerEnteroValido(Scanner scanner) {
        int numero = 0;
        boolean entradaValida = false;
        System.out.println("------------------------");
        do {
            try {
                System.out.print("Por favor, ingresa un número entero: ");
                String entrada = scanner.nextLine();
                numero = Integer.parseInt(entrada);
                entradaValida = true;
                System.out.println("");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debes ingresar un número entero.");
            }
        } while (!entradaValida);

        return numero;
    }




}
