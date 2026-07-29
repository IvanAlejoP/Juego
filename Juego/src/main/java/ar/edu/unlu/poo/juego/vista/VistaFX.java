package ar.edu.unlu.poo.juego.vista;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import ar.edu.unlu.poo.juego.gui.FinController;
import ar.edu.unlu.poo.juego.gui.JugandoController;
import ar.edu.unlu.poo.juego.gui.MenuController;
import ar.edu.unlu.poo.juego.gui.PrepJuegoController;
import ar.edu.unlu.poo.juego.modelo.Jugador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class VistaFX {
    private final Stage stage;
    private ControladorFX controlador;

    public VistaFX(Stage stage){
        this.stage = stage;
    }

    public void setControlador(ControladorFX controlador){
        this.controlador = controlador;
    }

    public void cambiarSceneMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ar/edu/unlu/poo/juego/SceneMenu.fxml"));
        Parent root = loader.load();
        MenuController controller = loader.getController();
        controller.setControlador(controlador);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void cambiarScenePrepJuego() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ar/edu/unlu/poo/juego/ScenePrepJuego.fxml"));
        Parent root = loader.load();
        PrepJuegoController controller = loader.getController();
        controller.setControlador(controlador);

        stage.setScene(new Scene(root));
    }

    public void cambiarSceneJugando(ArrayList<Jugador> jugadores) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ar/edu/unlu/poo/juego/SceneJugando.fxml"));
        Parent root = loader.load();
        JugandoController controller = loader.getController();
        controller.setControlador(controlador);
        controller.mostrarJugadores(jugadores);

        stage.setScene(new Scene(root));
    }

    public void cambiarSceneFin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ar/edu/unlu/poo/juego/SceneFin.fxml"));
        Parent root = loader.load();
        FinController controller = loader.getController();
        controller.setControlador(controlador);

        stage.setScene(new Scene(root));
    }

    public void salir(){
        stage.close();
    }

    public void refrescarPantalla() {//METODO PARA ACTUALIZAR PANTALLA, HAY QUE VER COMO LO HACEMOS
    }
}
