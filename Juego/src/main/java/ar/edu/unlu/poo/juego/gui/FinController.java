package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class FinController {
    private ControladorFX controlador;

    @FXML
    private Label resultado;

    public void setControlador(ControladorFX controlador){
        this.controlador = controlador;
    }

    public void setResultado(String texto){
        resultado.setText(texto);
    }

    @FXML
    private void volverAlMenu(javafx.event.ActionEvent e) throws IOException {
        controlador.cambiarSceneMenu();
    }
}
