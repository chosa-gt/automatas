package miprimeragui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MiPrimeraGUI extends Application {

    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
        
    }

    //Este metodo es obligatorio
    public void start(Stage primaryStage) {
        Group root = new Group();
        //El grupo que se desea agregar, y el tamaño ancho y alto
        Scene scene = new Scene(root, 300, 300);
        //Titulo de la ventana
        primaryStage.setTitle("Clase Principal");
        //Se agrega la scena
        primaryStage.setScene(scene);

        // create a label
        Label l = new Label("Por ahora no has clicado el botón...");

        // action event
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                l.setText("¡¡¡Has hecho clic en el BOTÓN!!!");
                VAutomatas ventanaAutomatas = new VAutomatas();
                Stage nuevaVentana = new Stage();
                try {
                    ventanaAutomatas.start(nuevaVentana);
                } catch (Exception r) {
                    //r.printStackTrace();
                }

            }
        };

        //Creacion del boton
        Button boton = new Button("Automatas");
        boton.setDefaultButton(true);
        //Tamaño del boton
        boton.setPrefSize(100, 50);
        //Tamaño del boton
        //Posicion dle boton
        boton.setLayoutX(105);
        boton.setLayoutY(110);
        boton.setOnAction(event);

        //Se agrega el boton
        root.getChildren().add(boton);
        //Se agrega el label
        root.getChildren().add(l);
        //Para mostrar la visible, semejante al setVisible(true)
        primaryStage.show();
    }

}
