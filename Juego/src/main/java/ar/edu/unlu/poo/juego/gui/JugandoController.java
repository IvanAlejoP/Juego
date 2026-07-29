package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import ar.edu.unlu.poo.juego.modelo.Carta;
import ar.edu.unlu.poo.juego.modelo.Jugador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class JugandoController {
    private ControladorFX controlador;

    @FXML
    private FlowPane boxArriba;

    @FXML
    private FlowPane boxIzquierda;

    @FXML
    private FlowPane boxDerecha;

    @FXML
    private FlowPane boxAbajo;

    @FXML
    private Label Turno;

    private List<Pane> paneles;

    @FXML
    public void initialize() {

        paneles = List.of(
                boxArriba,
                boxIzquierda,
                boxDerecha,
                boxAbajo
        );

        ocultarPaneles();
    }

    private void ocultarPaneles() {

        for (Pane panel : paneles) {
            panel.setVisible(false);
            panel.setManaged(false);
            panel.getChildren().clear();
        }
    }

    public void mostrarJugadores(ArrayList<Jugador> jugadores) {

        ocultarPaneles();

        for (int i=0; i<jugadores.size(); i++) {

            Pane panel = paneles.get(i);

            panel.setVisible(true);
            panel.setManaged(true);

            mostrarMano(panel, jugadores.get(i));
        }
    }

    private void mostrarMano(Pane panel, Jugador jugador) {

        panel.getChildren().clear();

        for (Carta carta : jugador.getMano().getMano()) {
            panel.getChildren().add(new CartaGUI(carta));
        }
    }

    public void actualizarTurno(Jugador jugador) {
        Turno.setText("Turno: " + jugador.getNombre());
    }


    public void setControlador(ControladorFX controlador){
        this.controlador = controlador;
    }
}
