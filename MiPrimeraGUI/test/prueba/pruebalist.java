import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;

public class pruebalist extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        double[][] curvePositions = {
            {50, 50, 125, 0, 200, 50},
            {50, 100, 125, 100, 200, 100},
            {50, 150, 125, 200, 200, 150}
        };

        drawCurvedLinesAndShowDialog(primaryStage, curvePositions);
    }

    public void drawCurvedLinesAndShowDialog(Stage stage, double[][] curvePositions) {
        // Crear un lienzo (Canvas) para dibujar las líneas curvas
        Canvas canvas = new Canvas(250, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        for (double[] position : curvePositions) {
            double startX = position[0];
            double startY = position[1];
            double controlX = position[2];
            double controlY = position[3];
            double endX = position[4];
            double endY = position[5];

           gc.beginPath();
            gc.moveTo(startX, startY);
            gc.quadraticCurveTo(controlX, controlY, endX, endY);
            gc.stroke();
        }

        // Crear un contenedor para el lienzo
        VBox vbox = new VBox(canvas);

        // Crear una escena y mostrarla en un diálogo
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Líneas Curvas Generadas");
        stage.show();

        // Mostrar un diálogo informativo
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText("Líneas curvas generadas con posiciones de datos de entrada.");
        alert.showAndWait();
    }
}




