package ar.edu.unlu.poo.juego;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import ar.edu.unlu.poo.juego.modelo.Partida;
import ar.edu.unlu.poo.juego.vista.VistaFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppFX extends Application{
    @Override
    public void start(Stage stage) throws IOException {

        Partida partida = new Partida();
        VistaFX vista = new VistaFX(stage);
        ControladorFX controlador = new ControladorFX(partida, vista);

        vista.setControlador(controlador);

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Presioná ESC para salir de pantalla completa");

        vista.cambiarSceneMenu();
    }

    public static void main(String[] args) {
        Application.launch(AppFX.class, args);
    }
}