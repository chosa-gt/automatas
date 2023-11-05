package miprimeragui;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class VAutomatas extends Application {

    String textoAbecedario;
    String textoEstados;
    String[] abecedarioArray;
    String[] estadosArray;
    ArrayList<String> abecedarioList;
    ArrayList<String> estadosList;
    

    private Parent createContent() {
        return new StackPane(new Text("Hello World"));
    }

    @Override
    public void start(Stage stage) { //public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, 720, 480);

        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

//Defining the abecedario text field
        final TextField abecedario = new TextField();
        abecedario.setPromptText("Introduzca el abecedario.");
        GridPane.setConstraints(abecedario, 0, 0);
        grid.getChildren().add(abecedario);

//Defining the estados text field
        final TextField estados = new TextField();
        estados.setPromptText("Indroduzca los estados.");
        GridPane.setConstraints(estados, 0, 1);
        grid.getChildren().add(estados);

////Defining the Comment text field
//        final TextField comment = new TextField();
//        comment.setPromptText("Enter your comment.");
//        GridPane.setConstraints(comment, 0, 2);
//        grid.getChildren().add(comment);
//Defining the Submit button
        Button aceptar = new Button("Aceptar");
        GridPane.setConstraints(aceptar, 1, 0);
        grid.getChildren().add(aceptar);

//Defining the Clear button
        Button limpiar = new Button("Limpiar");
        GridPane.setConstraints(limpiar, 1, 1);
        grid.getChildren().add(limpiar);

        ////////////////////////////////////////////////
        //Adding a Label
        final Label label = new Label();
        GridPane.setConstraints(label, 0, 3);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        aceptar.setOnAction((ActionEvent e) -> {
            if (estados.getText() != null && !abecedario.getText().isEmpty()) {//estados.getText() != null && !abecedario.getText().isEmpty())
                label.setText(abecedario.getText() + " "
                        + estados.getText() + ", "
                        + "Parametros aceptados.");
                // Obtén el texto de los campos de texto
                textoAbecedario = abecedario.getText(); // Obtén el texto de los campos de texto
                textoEstados = estados.getText(); // Obtén el texto de los campos de texto

                // Divide el texto en función de comas y espacios en blanco
                abecedarioArray = textoAbecedario.split("[,\\s]+");
                estadosArray = textoEstados.split("[,\\s]+");

                // Crea un ArrayList para almacenar los valores divididos
                abecedarioList = new ArrayList<>(Arrays.asList(abecedarioArray));
                estadosList = new ArrayList<>(Arrays.asList(estadosArray));

            } else {
                label.setText("Parametros no aceptados");
            }
        });

        limpiar.setOnAction((ActionEvent e) -> {
            abecedario.clear();
            estados.clear();
            label.setText(null);
        });

        root.getChildren().add(grid);
        stage.setTitle("Automatas");
        stage.setScene(scene);
        //stage.setScene(new Scene(createContent(), 720, 480));
        stage.show();

    }
}
