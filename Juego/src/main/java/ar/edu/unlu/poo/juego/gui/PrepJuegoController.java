package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import ar.edu.unlu.poo.juego.modelo.Jugador;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class PrepJuegoController {
    private ControladorFX controlador;

    @FXML
    private ChoiceBox<Integer> cantJugadores;

    @FXML
    private TextField txtNombreJugador;

    @FXML
    private ListView<String> lstJugadores;

    @FXML
    public void initialize(){
        if (cantJugadores != null) {
            cantJugadores.getItems().addAll(2,3,4);
            cantJugadores.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void aceptar() throws IOException {
        if (cantJugadores != null) {
            int cantidad = cantJugadores.getValue();
            controlador.cantJugadores(cantidad);
        }

        String nombre = "Jugador";
        if (txtNombreJugador != null && !txtNombreJugador.getText().trim().isEmpty()) {
            nombre = txtNombreJugador.getText().trim();
        } else {
            nombre += "_" + (System.currentTimeMillis() % 1000);
        }

        controlador.registrarJugador(nombre);
    }

    public void actualizarListaJugadores(ArrayList<Jugador> jugadores) {
        if (lstJugadores != null) {
            lstJugadores.getItems().clear();
            for (Jugador j : jugadores) {
                lstJugadores.getItems().add(j.getNombre());
            }
        }
    }

    @FXML
    public void salir() throws IOException {
        controlador.cambiarSceneMenu();
    }

    public void setControlador(ControladorFX controlador){
        this.controlador = controlador;
    }
}