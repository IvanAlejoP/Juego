package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {
    private ControladorFX controlador;

    public void setControlador(ControladorFX controlador){
        this.controlador = controlador;
    }

    @FXML
    private void btnJugar(ActionEvent e) throws IOException {
        controlador.cambiarScenePrepJuego();
    }

    @FXML
    private void btnSalir(ActionEvent e){
        controlador.salir();
    }
}
