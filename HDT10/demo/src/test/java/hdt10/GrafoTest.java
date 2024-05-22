package hdt10;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GrafoTest {

    private Grafo grafo;
    private final String testFile = "src\\test\\guategrafo.txt";

    @Before
    public void setUp() throws IOException {
        grafo = new Grafo();
        
    }

    @Test
    //Test del algoritmo de Floyd
    public void testFloyd(){
        String ciudadOrigen = "Mixco";
        String ciudadDestino = "Panajachel";
        grafo.ejecutarFloydWarshall(testFile, ciudadOrigen, ciudadDestino);
        int distancia = grafo.getDistancia();
        assertEquals(360, distancia);
    }

    @Test
    //Test para agregar y eliminar arcos
    public void connectarDesconectarTest(){
        //Se válida el proceso de conexión
        String ciudadOrigen = "ciudadImaginariaUno";
        String ciudadDestino = "ciudadImaginariaDos";

        //Test para validar si se agrego una conexión
        int distanciaImaginaria = 500;
        grafo.agregarConexion(testFile, ciudadOrigen, ciudadDestino, distanciaImaginaria);
        int distanciaConexion = grafo.getDistancia();
        assertEquals(500, distanciaConexion);

        //Test para validar que se elimina la conexión
        grafo.eliminarConexion(testFile, ciudadOrigen, ciudadDestino);
        distanciaConexion = grafo.getDistancia();
        assertEquals(0,distanciaConexion); 
    }

    @Test
    //Test para verificar que se agregan nodos
    public void nodosTest(){
        Grafo grafoTest = new Grafo();
        assertEquals(null, grafoTest.getNodos());

        String origen = "Mixco";
        String destino = "Antigua";

        grafoTest.ejecutarFloydWarshall(testFile, origen, destino);

        assertFalse(grafoTest.getNodos().isEmpty());

    }



}