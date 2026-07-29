package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class PrepJuegoController {
    private ControladorFX controlador;

    @FXML
    private ChoiceBox<Integer> cantJugadores;

    @FXML
    public void initialize(){
        cantJugadores.getItems().addAll(2,3,4);
        cantJugadores.getSelectionModel().selectFirst();
    }

    @FXML
    public void aceptar() throws IOException {
        int cantidad = cantJugadores.getValue();
        controlador.cantJugadores(cantidad);
        controlador.cambiarSceneJugando();
    }

    @FXML
    public void salir() throws IOException {
        controlador.cambiarSceneMenu();
    }

    public void setControlador(ControladorFX controlador){
        this.controlador = controlador;
    }
}
