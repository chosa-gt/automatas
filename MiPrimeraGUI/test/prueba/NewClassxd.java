package prueba;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class NewClassxd extends Application {

    ArrayList<String> abecedarioList = new ArrayList<>();
    ArrayList<String> estadosList = new ArrayList<>();

    private Parent createContent() {
        return new StackPane(new Text("Hello World"));
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        TabPane tabPane = new TabPane();
        
        Tab tablaTab = createAdaptableTab("Tabla");
        Tab diagramaTab = createAdaptableTab("Diagrama");

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

        Button limpiar = new Button("Limpiar");
        GridPane.setConstraints(limpiar, 1, 1);
        grid.getChildren().add(limpiar);

        final Label label = new Label();
        GridPane.setConstraints(label, 0, 3);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        aceptar.setOnAction(e -> {
            if (estados.getText() != null && !abecedario.getText().isEmpty()) {
                label.setText(abecedario.getText() + " " + estados.getText() + ", Parámetros aceptados");
                abecedarioList = new ArrayList<>(Arrays.asList(abecedario.getText().split("[,\\s]+")));
                estadosList = new ArrayList<>(Arrays.asList(estados.getText().split("[,\\s]+")));

                buildDynamicGridPane(tablaTab, abecedarioList, estadosList);
                tabPane.getTabs().add(tablaTab);
                tabPane.getSelectionModel().select(tablaTab);
                
                buildDiagram(diagramaTab);
                tabPane.getTabs().add(diagramaTab);
                tabPane.getSelectionModel().select(diagramaTab);
                
            } else {
                label.setText("Parámetros no aceptados");
            }
        });

        limpiar.setOnAction(e -> {
            abecedario.clear();
            estados.clear();
            label.setText(null);
            buildDynamicGridPane(tablaTab, new ArrayList<>(), new ArrayList<>());
            tabPane.getTabs().remove(tablaTab);
            buildDiagram(diagramaTab);
            tabPane.getTabs().remove(diagramaTab);
        });
        
        vbox.getChildren().addAll(tabPane);
        tab1.setContent(grid);
        tabPane.getTabs().add(tab1);

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
            grid.add(etiqueta, 1, row + 6);
        }

        TextField[][] textFields = new TextField[estadosList.size()][abecedarioList.size()];
        for (int row = 0; row < estadosList.size(); row++) {
            for (int col = 0; col < abecedarioList.size(); col++) {
                textFields[row][col] = new TextField();
                textFields[row][col].setPrefWidth(60);
                grid.add(textFields[row][col], col + 2, row + 6);
            }
        }

        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
    }
    
    private void buildDiagram(Tab tab) {
        ScrollPane scrollPane = new ScrollPane();
        Pane pane = new Pane();
        scrollPane.setContent(pane);

        double centerX = 300;
        double centerY = 200;
        double maxRadius = 100;
        double circleRadius = 20;
        double angleIncrement = 360.0 / estadosList.size();
        double currentAngle = 0;

        ArrayList<Circle> circles = new ArrayList<>();
        ArrayList<Text> labels = new ArrayList<>();

        for (String estado : estadosList) {
            double x = centerX + maxRadius * Math.cos(Math.toRadians(currentAngle));
            double y = centerY + maxRadius * Math.sin(Math.toRadians(currentAngle));

            Circle circle = new Circle(x, y, circleRadius);
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.YELLOW);
            circles.add(circle);

            Text text = new Text(x - circleRadius / 2, y, estado);
            text.setFont(Font.font("Arial", 12));
            labels.add(text);

            currentAngle += angleIncrement;
        }

        for (int i = 0; i < circles.size(); i++) {
            for (int j = i + 1; j < circles.size(); j++) {
                double startX = circles.get(i).getCenterX();
                double startY = circles.get(i).getCenterY();
                double endX = circles.get(j).getCenterX();
                double endY = circles.get(j).getCenterY();

                Line line = new Line(startX, startY, endX, endY);
                line.setStroke(Color.BLACK);
                pane.getChildren().add(line);
            }
        }

        pane.getChildren().addAll(circles);
        pane.getChildren().addAll(labels);

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

    public static void main(String[] args) {
        launch(args);
    }
}

