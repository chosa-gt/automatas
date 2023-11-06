/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prueba;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import static javafx.application.Application.launch;
import javafx.scene.control.Tab;

/**
 *
 * @author chino
 */
public class NewMain {
public void start(Stage primaryStage) {
        // Supongamos que tienes tus listas abecedarioList y estadosList
        ArrayList<String> abecedarioList = new ArrayList<>();
        ArrayList<String> estadosList = new ArrayList<>();

        // Crear la pestaña (Tab) y el contenido
        Tab tab = new Tab("Contenido Dinámico");
        buildDynamicGridPane(tab, abecedarioList, estadosList);

        // Obtener los datos desde los campos TextField
        GridPane grid = (GridPane) tab.getContent();
        TextField[][] textFields = new TextField[estadosList.size()][abecedarioList.size()];
        for (int row = 0; row < estadosList.size(); row++) {
            for (int col = 0; col < abecedarioList.size(); col++) {
                textFields[row][col] = (TextField) grid.getChildren().get((row + 6) * abecedarioList.size() + col + 2);
            }
        }

        List<String> datosObtenidos = obtenerDatos(textFields);

        // Puedes utilizar la lista "datosObtenidos" en tu programa
        for (String dato : datosObtenidos) {
            System.out.println(dato);
        }
    }

    public static void buildDynamicGridPane(Tab tab, ArrayList<String> abecedarioList, ArrayList<String> estadosList) {
        GridPane grid = new GridPane();

        // ... (código para crear etiquetas y campos TextField)

        tab.setContent(grid);
    }

    public static List<String> obtenerDatos(TextField[][] textFields) {
        List<String> datos = new ArrayList<>();
        for (int row = 0; row < textFields.length; row++) {
            for (int col = 0; col < textFields[row].length; col++) {
                String texto = textFields[row][col].getText();
                datos.add(texto);
            }
        }
        return datos;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
