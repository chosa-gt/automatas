package prueba;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VAutomatas extends Application {

    String textoAbecedario;
    String textoEstados;
    ArrayList<String> abecedarioList = new ArrayList<>();
    ArrayList<String> estadosList = new ArrayList<>();
    List<String> datos = new ArrayList<>();
    private Parent createContent() {
        return new StackPane(new Text("Hello World"));
    }

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        //Scene scene = new Scene(root, 720, 480);
        
        TabPane tabPane = new TabPane();
        
        Tab tablaTab = new Tab("Tabla");

        Tab tab1 = new Tab("Formulario");
        tab1.setClosable(false);
        
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 720, 480);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        final TextField abecedario = new TextField();
        abecedario.setPromptText("Introduzca el abecedario");
        GridPane.setConstraints(abecedario, 0, 0);
        grid.getChildren().add(abecedario);

        final TextField estados = new TextField();
        estados.setPromptText("Introduzca los estados");
        GridPane.setConstraints(estados, 0, 1);
        grid.getChildren().add(estados);

        Button aceptar = new Button("Aceptar");
        GridPane.setConstraints(aceptar, 1, 0);
        grid.getChildren().add(aceptar);
        
                Button aceptar2 = new Button("Aceptar");
        GridPane.setConstraints(aceptar2, 3, 0);
        grid.getChildren().add(aceptar2);
        
        Button limpiar = new Button("Limpiar");
        GridPane.setConstraints(limpiar, 1, 1);
        grid.getChildren().add(limpiar);

        final Label label = new Label();
        GridPane.setConstraints(label, 0, 3);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        aceptar.setOnAction((ActionEvent e) -> {
            if (estados.getText() != null && !abecedario.getText().isEmpty()) {
                label.setText(abecedario.getText() + " " + estados.getText() + ", Parámetros aceptados.");
                textoAbecedario = abecedario.getText();
                textoEstados = estados.getText();
                abecedarioList = new ArrayList<>(Arrays.asList(textoAbecedario.split("[,\\s]+")));
                estadosList = new ArrayList<>(Arrays.asList(textoEstados.split("[,\\s]+")));

                buildDynamicGridPane(tablaTab, abecedarioList, estadosList);
                tabPane.getTabs().add(tablaTab);
                tabPane.getSelectionModel().select(tablaTab);
            } else {
                label.setText("Parámetros no aceptados");
            }
        });

        limpiar.setOnAction((ActionEvent e) -> {
            abecedario.clear();
            estados.clear();
            label.setText(null);
            grid.getChildren().clear();
        });
        
         aceptar2.setOnAction((ActionEvent e) -> {
                // Obtener la matriz de campos TextField desde la pestaña (Tab)
TextField[][] textFields = getTextFieldsFromTab(tablaTab, abecedarioList.size()+1, estadosList.size()+1);

// Llamar al método para imprimir los datos en la consola
imprimirTextFields(textFields);

        });
        
        vbox.getChildren().addAll(tabPane);
        tab1.setContent(grid);
        tabPane.getTabs().add(tab1);

        //root.getChildren().add(tabPane);
        stage.setTitle("Automatas");
        stage.setScene(scene);
        stage.show();
    }

public static void buildDynamicGridPane(Tab tab, ArrayList<String> abecedarioList, ArrayList<String> estadosList) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        
        for (int col = 0; col < abecedarioList.size(); col++) {
            Label etiqueta = new Label(abecedarioList.get(col));
            grid.add(etiqueta, col + 2, 5);
        }

        for (int row = 0; row < estadosList.size(); row++) {
            Label etiqueta = new Label(estadosList.get(row));
            grid.add(etiqueta, 1 , row + 6);
        }

        // Matriz de datos TextField
        TextField[][] textFields = new TextField[estadosList.size()][abecedarioList.size()];
        for (int row = 0; row < estadosList.size(); row++) {
            for (int col = 0; col < abecedarioList.size(); col++) {
                textFields[row][col] = new TextField();
                textFields[row][col].setPrefWidth(60); // El doble de ancho
                grid.add(textFields[row][col], col + 2, row + 6);
            }
        }

        Button crear = new Button("Crear Automata");//botton para crear el automata
        GridPane.setConstraints(crear, 1, 0);
        grid.getChildren().add(crear);
        crear.setOnAction((ActionEvent e) -> {
            //TextField[][] textFields = getTextFieldsFromTab(tab, abecedarioList.size() + 1, estadosList.size() + 1);

    // Llamar al método para imprimir los datos en la consola
    imprimirTextFields(textFields);

    // Llamar al método para obtener y procesar datos
    obtenerYProcesarDatos(textFields);
        });
        
        tab.setContent(grid);
    }

    public static TextField[][] getTextFieldsFromTab(Tab tab, int numColumns, int numRows) {
        GridPane grid = (GridPane) tab.getContent();
        TextField[][] textFields = new TextField[numRows][numColumns];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                textFields[row][col] = (TextField) grid.getChildren().get((row + 6) * numColumns + col + 2);
            }
        }
        return textFields;
    }

    public static void obtenerYProcesarDatos(TextField[][] textFields) {
        // Obtener y procesar datos desde los campos TextField
        for (int row = 0; row < textFields.length/2; row++) {
            for (int col = 0; col < (textFields[row].length/2)-1; col++) {
                String dato = textFields[row][col].getText();
                System.out.println("Dato [" + row + "][" + col + "]: " + dato);
                // Realizar el procesamiento necesario
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    

public static void imprimirTextFields(TextField[][] textFields) {
    for (int row = 0; row < textFields.length; row++) {
        for (int col = 0; col < textFields[row].length; col++) {
            String dato = textFields[row][col].getText();
            System.out.println("Dato [" + row + "][" + col + "]: " + dato);
        }
    }
}

 


    
    
}
