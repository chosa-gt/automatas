/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chino
 */
public class pruebalist {

    /**
     * @param args the command line arguments
     */

public static List<String> procesarDatos(List<String> lista) {
        // Crear una nueva lista para almacenar los datos procesados
        List<String> datosProcesados = new ArrayList<>();

        // Recorrer la lista y agregar los elementos a la nueva lista
        for (String elemento : lista) {
            datosProcesados.add(elemento);
        }

        // Devolver la lista procesada
        return datosProcesados;
    }

    public static void miMetodo2(List<String> lista) {
        // Llamar al método procesarDatos para obtener la lista procesada
        List<String> datosProcesados = procesarDatos(lista);

        // Ahora puedes utilizar la lista datosProcesados en tu código
        for (String elemento : datosProcesados) {
            System.out.println("Dato procesado: " + elemento);
        }
    }

    public static void main(String[] args) {
        List<String> datos = new ArrayList<>();
        datos.add("Dato 1");
        datos.add("Dato 2");
        datos.add("Dato 3");

        // Llamar a miMetodo2 y pasar la lista de datos
        miMetodo2(datos);
        
        // Llamar a procesarDatos y almacenar la lista procesada
        List<String> datosProcesados = procesarDatos(datos);

        // Ahora puedes utilizar la lista datosProcesados en tu código
        for (String elemento : datosProcesados) {
            System.out.println("Dato procesado: " + elemento);
        }
    }
    
}

