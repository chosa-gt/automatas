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

import java.util.ArrayList;
import java.util.Arrays;

public class VAutomatas extends Application {

    String textoAbecedario;
    String textoEstados;
    ArrayList<String> abecedarioList = new ArrayList<>();
    ArrayList<String> estadosList = new ArrayList<>();

    private Parent createContent() {
        return new StackPane(new Text("Hello World"));
    }

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        Scene scene = new Scene(root, 720, 480);

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

                buildDynamicGridPane(grid, abecedarioList, estadosList);
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

        root.getChildren().add(grid);
        stage.setTitle("Automatas");
        stage.setScene(scene);
        stage.show();
    }

    private void buildDynamicGridPane(GridPane grid, ArrayList<String> abecedarioList, ArrayList<String> estadosList) {
        for (int col = 0; col < abecedarioList.size(); col++) {
            Label etiqueta = new Label(abecedarioList.get(col));
            grid.add(etiqueta, col + 2, 5);
        }

        for (int row = 0; row < estadosList.size(); row++) {
            Label etiqueta = new Label(estadosList.get(row));
            grid.add(etiqueta, 1 , row + 6); //6
        }

        TextField[][] textFields = new TextField[estadosList.size()][abecedarioList.size()];
        for (int row = 0; row < estadosList.size(); row++) {
            for (int col = 0; col < abecedarioList.size(); col++) {
                textFields[row][col] = new TextField();
                textFields[row][col].setPrefWidth(60); // El doble de ancho
                grid.add(textFields[row][col], col + 2, row + 6);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
