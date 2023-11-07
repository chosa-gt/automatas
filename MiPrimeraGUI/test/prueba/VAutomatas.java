package prueba;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VAutomatas extends Application {

    String textoAbecedario;
    String textoEstados;
    ArrayList<String> abecedarioList = new ArrayList<>();
    ArrayList<String> estadosList = new ArrayList<>();

    TabPane tabPane = new TabPane();

    Tab tabFormulario = new Tab("Formulario");
    Tab tablaTab = createAdaptableTab("Tabla");
    Tab diagramaTab = new Tab("Diagrama");

    TextField[][] textFields;

    private TextField estadoInicialTextField;// Nuevas variables miembro para el Estado inicial y Estado final
    private TextField estadoFinalTextField;

    private ChoiceBox<String> tipoAutomataChoiceBox;// Nueva variable miembro para el ChoiceBox del Tipo de autómata
    
    private Map<String, Map<String, String>> transicionesMap = new HashMap<>();

    @Override
    public void start(Stage stage) {
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

        estadoInicialTextField = new TextField();
        estadoInicialTextField.setPromptText("Estado inicial");
        GridPane.setConstraints(estadoInicialTextField, 2, 0);
        grid.getChildren().add(estadoInicialTextField);

        estadoFinalTextField = new TextField();
        estadoFinalTextField.setPromptText("Estado final");
        GridPane.setConstraints(estadoFinalTextField, 2, 1);
        grid.getChildren().add(estadoFinalTextField);

        tipoAutomataChoiceBox = new ChoiceBox<>();
        tipoAutomataChoiceBox.getItems().addAll("Finito Determinista", "Finito No Determinista");
        tipoAutomataChoiceBox.setValue("Finito Determinista"); // Valor predeterminado
        GridPane.setConstraints(tipoAutomataChoiceBox, 2, 2);
        grid.getChildren().add(tipoAutomataChoiceBox);

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
            String abecedarioText = abecedario.getText();
            String estadosText = estados.getText();
            String estadoInicialText = estadoInicialTextField.getText();
            String estadoFinalText = estadoFinalTextField.getText();

            if (abecedarioText != null && !abecedarioText.isEmpty()
                        && estadosText != null && !estadosText.isEmpty()
                        && estadoInicialText != null && !estadoInicialText.isEmpty()
                        && estadoFinalText != null && !estadoFinalText.isEmpty()) {
                
                

                // Validar que el estado inicial y final estén en la lista de estados
                if (!estadosList.contains(estadoInicialText) || !estadosList.contains(estadoFinalText)) {
                    label.setText("El estado inicial o final no es válido.");
                } else {
                    // ... Continuar con el procesamiento de los datos ...
                }

                label.setText(abecedario.getText() + " " + estados.getText() + ", Parámetros aceptados.");
                textoAbecedario = abecedario.getText();
                textoEstados = estados.getText();
                abecedarioList = new ArrayList<>(Arrays.asList(textoAbecedario.split("[,\\s]+")));
                estadosList = new ArrayList<>(Arrays.asList(textoEstados.split("[,\\s]+")));

                String estadoInicial = estadoInicialTextField.getText();// Almacena el estado inicial y final ingresados por el usuario
                String estadoFinal = estadoFinalTextField.getText();
                String tipoAutomata = tipoAutomataChoiceBox.getValue(); // Obtiene el tipo de autómata seleccionado

                buildDynamicGridPane(tablaTab, abecedarioList, estadosList);
                tabPane.getTabs().add(tablaTab);
                tabPane.getSelectionModel().select(tablaTab);
            } else {
                label.setText("Asegúrese de completar todos los campos.");
            }
        });

        limpiar.setOnAction((ActionEvent e) -> {
            abecedario.clear();
            estados.clear();
            label.setText(null);
        });

        vbox.getChildren().addAll(tabPane);
        tabFormulario.setContent(grid);
        tabPane.getTabs().add(tabFormulario);

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

        textFields = new TextField[estadosList.size()][abecedarioList.size()];

        for (int col = 0; col < abecedarioList.size(); col++) {
            Label etiqueta = new Label(abecedarioList.get(col));
            grid.add(etiqueta, col + 2, 5);
        }

        for (int row = 0; row < estadosList.size(); row++) {
            Label etiqueta = new Label(estadosList.get(row));
            grid.add(etiqueta, 1, row + 6);
        }

        for (int row = 0; row < estadosList.size(); row++) {
            for (int col = 0; col < abecedarioList.size(); col++) {
                textFields[row][col] = new TextField();
                textFields[row][col].setPrefWidth(60);
                grid.add(textFields[row][col], col + 2, row + 6);
            }
        }

        Button crear = new Button("Crear Automata");
        GridPane.setConstraints(crear, 1, 0);
        grid.getChildren().add(crear);

        crear.setOnAction((ActionEvent e) -> {
            procesarDatos(textFields, estadosList, abecedarioList);

            generateCircles(diagramaTab, estadosList.size());
            tabPane.getTabs().add(diagramaTab);
            tabPane.getSelectionModel().select(diagramaTab);
        });

        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
    }

    private void generateStates(Pane pane) {
    double centerX = 300;
    double centerY = 200;
    double maxRadius = 100;
    double circleRadius = 20;
    double labelOffsetX = -circleRadius / 2; // Offset horizontal para centrar el texto
    double labelOffsetY = circleRadius / 4; // Offset vertical para centrar el texto
    double triangleOffsetX = -circleRadius; // Offset horizontal para el triángulo invertido
    double triangleOffsetY = 0; // Offset vertical para el triángulo invertido
    double angleIncrement = 360.0 / estadosList.size();
    double currentAngle = 0;

    for (int i = 0; i < estadosList.size(); i++) {
        String estado = estadosList.get(i);
        double x = centerX + maxRadius * Math.cos(Math.toRadians(currentAngle));
        double y = centerY + maxRadius * Math.sin(Math.toRadians(currentAngle));

        Circle circle = new Circle(x, y, circleRadius);
        circle.setStyle("-fx-stroke: black; -fx-fill: yellow;");
        pane.getChildren().add(circle);

        Text text = new Text(x + labelOffsetX, y + labelOffsetY, estado);
        text.setStyle("-fx-font: 12 arial;");
        pane.getChildren().add(text);

        // Marcar el estado inicial con un triángulo invertido (girado 180 grados)
        if (estado.equals(estadoInicialTextField.getText())) {
            Polygon initialArrow = new Polygon();
            initialArrow.getPoints().addAll(x + triangleOffsetX, y + triangleOffsetY - circleRadius,
                    x + triangleOffsetX - circleRadius, y + triangleOffsetY + circleRadius,
                    x + triangleOffsetX + circleRadius, y + triangleOffsetY + circleRadius);
            initialArrow.setStyle("-fx-fill: green;");
            pane.getChildren().add(initialArrow);
        }

        // Marcar el estado final
        if (estado.equals(estadoFinalTextField.getText())) {
            // Marcar el estado final con un círculo con borde verde
            Circle finalStateCircle = new Circle(x, y, circleRadius + 5);
            finalStateCircle.setStyle("-fx-stroke: green; -fx-fill: none;");
            pane.getChildren().add(finalStateCircle);
        }

        currentAngle += angleIncrement;
    }
}


private void generateTransitions(Pane pane) {
    double centerX = 300;
    double centerY = 200;
    double maxRadius = 100;
    double circleRadius = 20;
    double angleIncrement = 360.0 / estadosList.size();
    double currentAngle = 0;

    for (int row = 0; row < estadosList.size(); row++) {
        for (int col = 0; col < abecedarioList.size(); col++) {
            String dato = textFields[row][col].getText();
            if (!dato.isEmpty()) {
                int nodeIndex1 = row; // Nodo actual
                int nodeIndex2 = abecedarioList.indexOf(dato); // Nodo correspondiente

                double x1 = centerX + maxRadius * Math.cos(Math.toRadians(currentAngle + nodeIndex1 * angleIncrement));
                double y1 = centerY + maxRadius * Math.sin(Math.toRadians(currentAngle + nodeIndex1 * angleIncrement));
                double x2 = centerX + maxRadius * Math.cos(Math.toRadians(currentAngle + nodeIndex2 * angleIncrement));
                double y2 = centerY + maxRadius * Math.sin(Math.toRadians(currentAngle + nodeIndex2 * angleIncrement));

                // Calcular las coordenadas de inicio y fin para que inicie y termine en el borde de los círculos
                double startX = x1 + (circleRadius * Math.cos(Math.toRadians(currentAngle + nodeIndex1 * angleIncrement)));
                double startY = y1 + (circleRadius * Math.sin(Math.toRadians(currentAngle + nodeIndex1 * angleIncrement)));
                double endX = x2 - (circleRadius * Math.cos(Math.toRadians(currentAngle + nodeIndex2 * angleIncrement)));
                double endY = y2 - (circleRadius * Math.sin(Math.toRadians(currentAngle + nodeIndex2 * angleIncrement)));

                Polygon arrow = new Polygon();
                arrow.getPoints().addAll(endX, endY, endX - 10, endY + 10, endX + 10, endY + 10);
                arrow.setStyle("-fx-fill: black;");
                pane.getChildren().add(arrow);

                Line line = new Line(startX, startY, endX, endY);
                line.setStyle("-fx-stroke: black;");
                pane.getChildren().add(line);
            }
        }
    }
}
    
    private void generateCircles(Tab tab, int count) {
    ScrollPane scrollPane = new ScrollPane();
    Pane pane = new Pane();
    scrollPane.setContent(pane);

    generateStates(pane); // Llama al método para crear los estados
    generateTransitions(pane); // Llama al método para crear las transiciones (líneas y flechas)
    tab.setContent(scrollPane);
}


    private Tab createAdaptableTab(String tabTitle) {
        Tab tab = new Tab(tabTitle);
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox();

        for (int j = 1; j <= 20; j++) {
            content.getChildren().add(new VBox());
        }

        scrollPane.setContent(content);

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

   public static void procesarDatos(TextField[][] textFields, List<String> estadosList, List<String> abecedarioList) {
    // Crear un mapa para almacenar las transiciones
    Map<String, Map<String, String>> transicionesMap = new HashMap<>();

    for (int row = 0; row < textFields.length; row++) {
        String estadoOrigen = estadosList.get(row); // Estado actual (origen)
        Map<String, String> transiciones = new HashMap<>(); // Mapa de transiciones para el estado actual
        boolean tieneBucle = false; // Variable para indicar si hay un bucle en este estado

        for (int col = 0; col < textFields[row].length; col++) {
            String dato = textFields[row][col].getText();

            if (!dato.isEmpty()) {
                String simbolo = abecedarioList.get(col); // Símbolo del abecedario
                String estadoDestino = dato; // Estado de destino

                transiciones.put(simbolo, estadoDestino);// Agregar la transición al mapa de transiciones del estado actual

                // Verifica si es un bucle (conexión a sí mismo)
                if (estadoOrigen.equals(estadoDestino)) {
                    // Registra el bucle en el mapa de transiciones del estado actual
                    transiciones.put("loop", estadoOrigen);
                    tieneBucle = true;
                }
            }
        }

        transicionesMap.put(estadoOrigen, transiciones);// Agregar el mapa de transiciones del estado actual al mapa principal

        // Si tiene un bucle, regístralo en la estructura de datos
        if (tieneBucle) {
            // Puedes registrar el bucle en el mismo mapa de transiciones o en una estructura adicional.
            // Por ejemplo:
            // transicionesMap.get(estadoOrigen).put("loop", estadoOrigen);
        }
    }

}

    public static void main(String[] args) {
        launch(args);
    }
}
