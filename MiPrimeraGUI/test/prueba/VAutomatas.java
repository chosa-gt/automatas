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
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.Arrays;

public class VAutomatas extends Application {

    String textoAbecedario;
    String textoEstados;
    ArrayList<String> abecedarioList = new ArrayList<>();
    ArrayList<String> estadosList = new ArrayList<>();

    TabPane tabPane = new TabPane();

    Tab tabFormulario = new Tab("Formulario");
    Tab tablaTab = createAdaptableTab("Tabla");
    Tab diagramaTab = new Tab("Diagrama");

    @Override
    public void start(Stage stage) {

        Group root = new Group();
        // Scene scene = new Scene(root, 720, 480);

        tabFormulario.setClosable(false);

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
            // grid.getChildren().clear();
        });

        vbox.getChildren().addAll(tabPane);
        tabFormulario.setContent(grid);
        tabPane.getTabs().add(tabFormulario);

        // root.getChildren().add(tabPane);
        stage.setTitle("Automatas");
        stage.setScene(scene);
        stage.show();
    }

    private void buildDynamicGridPane(Tab tab, ArrayList<String> abecedarioList, ArrayList<String> estadosList) {
        ScrollPane scrollPane = new ScrollPane();
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
            grid.add(etiqueta, 1, row + 6); // 6
        }

        TextField[][] textFields = new TextField[estadosList.size()][abecedarioList.size()];
        for (int row = 0; row < estadosList.size(); row++) {
            for (int col = 0; col < abecedarioList.size(); col++) {
                textFields[row][col] = new TextField();
                textFields[row][col].setPrefWidth(60); // El doble de ancho
                grid.add(textFields[row][col], col + 2, row + 6);
            }
        }

        Button crear = new Button("Crear Automata");// botton para crear el automata
        GridPane.setConstraints(crear, 1, 0);
        grid.getChildren().add(crear);

        crear.setOnAction((ActionEvent e) -> {
            // aqui adentro va la accion del botton

            // TextField[][] textFieldsList = getTextFieldsFromTab(tab,
            // abecedarioList.size() + 1, estadosList.size() + 1);
            // obtenerYProcesarDatos(textFieldsList);
            // imprimirTextFields(textFieldsList);
            // //fillEmptyTextFieldsWithLambda(textFieldsList);

            generateCircles(diagramaTab, estadosList.size());
            tabPane.getTabs().add(diagramaTab);
            tabPane.getSelectionModel().select(diagramaTab);
        });

        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true); // Esto permite que el ScrollPane se adapte al ancho de la ventana
        tab.setContent(scrollPane);
    }

    private void generateCircles(Tab tab, int count) {
        ScrollPane scrollPane = new ScrollPane();
        Pane pane = new Pane();
        scrollPane.setContent(pane);

        double centerX = 300; // Coordenada X del centro de los círculos
        double centerY = 200; // Coordenada Y del centro de los círculos
        double maxRadius = 100; // Radio máximo de los círculos
        double circleRadius = 20; // Radio de los círculos
        double angleIncrement = 360.0 / count;
        double currentAngle = 0;

        for (String estado : estadosList) {
            double x = centerX + maxRadius * Math.cos(Math.toRadians(currentAngle));
            double y = centerY + maxRadius * Math.sin(Math.toRadians(currentAngle));

            Circle circle = new Circle(x, y, circleRadius);
            circle.setStyle("-fx-stroke: black; -fx-fill: yellow;");
            pane.getChildren().add(circle);

            Text text = new Text(x - circleRadius / 2, y, estado);
            text.setStyle("-fx-font: 12 arial;");
            pane.getChildren().add(text);

            currentAngle += angleIncrement;
        }

        tab.setContent(scrollPane);
    }

    private Tab createAdaptableTab(String tabTitle) {
        Tab tab = new Tab(tabTitle);
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox();

        // Agrega contenido al ScrollPane (puedes personalizar esto)
        for (int j = 1; j <= 20; j++) {
            content.getChildren().add(new VBox());
        }

        scrollPane.setContent(content);

        // Asegura que el contenido del ScrollPane se ajuste al tamaño de la pestaña
        content.prefWidthProperty().bind(scrollPane.widthProperty());
        content.prefHeightProperty().bind(scrollPane.heightProperty());

        tab.setContent(scrollPane);

        return tab;
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

    public static void imprimirTextFields(TextField[][] textFields) {
        for (int row = 0; row < textFields.length; row++) {
            for (int col = 0; col < textFields[row].length; col++) {
                String dato = textFields[row][col].getText();
                System.out.println("Dato [" + row + "][" + col + "]: " + dato);
            }
        }
    }

    public static void obtenerYProcesarDatos(TextField[][] textFields) {
        // Obtener y procesar datos desde los campos TextField
        for (int row = 0; row < textFields.length / 2; row++) {
            for (int col = 0; col < (textFields[row].length / 2) - 1; col++) {
                String dato = textFields[row][col].getText();
                System.out.println("Dato [" + row + "][" + col + "]: " + dato);
                // Realizar el procesamiento necesario
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
