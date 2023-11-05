package prueba;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class DibujarCirculosJavaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Dibujar Círculos");

        TextField inputField = new TextField();
        Button drawButton = new Button("Dibujar");

        HBox inputBox = new HBox(10, inputField, drawButton);
        inputBox.setAlignment(Pos.CENTER);

        Pane root = new Pane();
        root.getChildren().add(inputBox);

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);

        drawButton.setOnAction(e -> {
            try {
                int cantidad = Integer.parseInt(inputField.getText());
                dibujarCirculos(root, cantidad);
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un número válido.");
            }
        });

        stage.show();
    }

    private void dibujarCirculos(Pane pane, int cantidad) {
        double radio = 10.0;
        double espacio = 20.0;
        double x = radio;
        double y = radio;

        pane.getChildren().clear();

        for (int i = 0; i < cantidad; i++) {
            Circle circle = new Circle(x, y, radio);
            circle.setFill(Color.BLUE);
            pane.getChildren().add(circle);
            x += radio * 2 + espacio;

            if (x + radio > pane.getWidth()) {
                x = radio;
                y += radio * 2 + espacio;
            }
        }
    }
}