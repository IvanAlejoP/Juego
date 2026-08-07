package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.controlador.ControladorFX;
import ar.edu.unlu.poo.juego.modelo.Carta;
import ar.edu.unlu.poo.juego.modelo.Jugador;
import ar.edu.unlu.poo.juego.modelo.JugadorHumano;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @FXML
    private Button btnTomarCarta;

    @FXML
    private Button btnDescartar;

    @FXML
    private Button btnTerminarTurno;

    private List<FlowPane> paneles;
    private final Map<Jugador, FlowPane> panelDeJugador = new LinkedHashMap<>();
    private Jugador humano;
    private final List<CartaGUI> seleccionadasParaDescarte = new ArrayList<>();

    @FXML
    public void initialize() {
        paneles = List.of(boxAbajo, boxIzquierda, boxArriba, boxDerecha);
        ocultarPaneles();
    }

    private void ocultarPaneles() {
        for (FlowPane panel : paneles) {
            panel.setVisible(false);
            panel.setManaged(false);
            panel.getChildren().clear();
        }
    }

    public void mostrarJugadores(ArrayList<Jugador> jugadores, Jugador yo, Jugador jugadorEnTurno) {
        this.humano = yo;

        boolean esMiTurno = (jugadorEnTurno != null && yo != null &&
                jugadorEnTurno.getNombre().equalsIgnoreCase(yo.getNombre()));

        if (btnTomarCarta != null) btnTomarCarta.setDisable(!esMiTurno);
        if (btnDescartar != null) btnDescartar.setDisable(!esMiTurno);

        panelDeJugador.clear();
        List<Jugador> jugadoresRotados = rotarDesdeHumano(jugadores);

        for (int i = 0; i < jugadoresRotados.size() && i < paneles.size(); i++) {
            Jugador j = jugadoresRotados.get(i);
            FlowPane panel = paneles.get(i);
            panel.setVisible(true);
            panel.setManaged(true);
            panelDeJugador.put(j, panel);
            mostrarMano(panel, j);
        }

        actualizarTurno(jugadorEnTurno);
    }

    private List<Jugador> rotarDesdeHumano(ArrayList<Jugador> jugadores) {
        List<Jugador> resultado = new ArrayList<>();

        if (humano == null) {
            resultado.addAll(jugadores);
            return resultado;
        }

        int indiceHumano = -1;
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombre().equalsIgnoreCase(humano.getNombre())) {
                indiceHumano = i;
                break;
            }
        }

        if (indiceHumano == -1) {
            resultado.addAll(jugadores);
            return resultado;
        }

        for (int i = 0; i < jugadores.size(); i++) {
            resultado.add(jugadores.get((indiceHumano + i) % jugadores.size()));
        }
        return resultado;
    }

    private void mostrarMano(FlowPane panel, Jugador jugador) {
        panel.getChildren().clear();

        boolean esHumano = (humano != null && jugador.getNombre().equalsIgnoreCase(humano.getNombre()));

        for (Carta carta : jugador.getMano().getMano()) {
            CartaGUI cartaGUI = new CartaGUI(carta, !esHumano);
            cartaGUI.setBloqueada(true);
            panel.getChildren().add(cartaGUI);
        }
    }

    public void actualizarTurno(Jugador jugador) {
        if (jugador != null && Turno != null) {
            Turno.setText("Turno: " + jugador.getNombre());
        }
    }

    private void procesarTurno() {
        limpiarSeleccion();
        ocultarBotonesDeDescarte();
        bloquearTodasLasCartas();

        if (controlador.juegoTerminado()) {
            finDelJuego();
            return;
        }

        Jugador jugadorEnTurno = controlador.getJugadorEnTurno();
        actualizarTurno(jugadorEnTurno);

        if (jugadorEnTurno instanceof JugadorHumano) {
            habilitarFaseDeRobo();
        } else {
            PauseTransition espera = new PauseTransition(Duration.seconds(0.8));
            espera.setOnFinished(e -> jugarTurnoIA(jugadorEnTurno));
            espera.play();
        }
    }

    private void jugarTurnoIA(Jugador jugadorIA) {
        controlador.jugarIA(jugadorIA);
        finalizarTurno();
    }

    private void habilitarFaseDeRobo() {
        Jugador siguiente = controlador.getSiguienteJugador(humano);
        FlowPane panelSiguiente = panelDeJugador.get(siguiente);

        if (panelSiguiente == null) {
            return;
        }

        for (int i = 0; i < panelSiguiente.getChildren().size(); i++) {
            CartaGUI cartaGUI = (CartaGUI) panelSiguiente.getChildren().get(i);
            int indice = i;
            cartaGUI.setBloqueada(false);
            cartaGUI.setAccionAlHacerClick(() -> robarCarta(indice));
        }
    }

    private void robarCarta(int indice) {
        bloquearTodasLasCartas();

        controlador.tomarCarta(humano, indice);
        refrescarTodosLosPaneles();

        habilitarFaseDeDescarte();
    }

    private void habilitarFaseDeDescarte() {
        FlowPane panelHumano = panelDeJugador.get(humano);

        if (panelHumano != null) {
            for (Node nodo : panelHumano.getChildren()) {
                CartaGUI cartaGUI = (CartaGUI) nodo;
                cartaGUI.setBloqueada(false);
                cartaGUI.setAccionAlHacerClick(() -> alternarSeleccionParaDescarte(cartaGUI));
            }
        }

        if (btnTerminarTurno != null) {
            btnTerminarTurno.setVisible(true);
            btnTerminarTurno.setManaged(true);
        }
        if (btnDescartar != null) {
            btnDescartar.setVisible(true);
            btnDescartar.setManaged(true);
            btnDescartar.setDisable(true);
        }
    }

    private void alternarSeleccionParaDescarte(CartaGUI cartaGUI) {
        if (cartaGUI.isSeleccionada()) {
            cartaGUI.deseleccionar();
            seleccionadasParaDescarte.remove(cartaGUI);
        } else {
            if (seleccionadasParaDescarte.size() == 2) {
                seleccionadasParaDescarte.remove(0).deseleccionar();
            }
            cartaGUI.seleccionar();
            seleccionadasParaDescarte.add(cartaGUI);
        }

        boolean sonPar = seleccionadasParaDescarte.size() == 2
                && seleccionadasParaDescarte.get(0).getCarta().getValor()
                == seleccionadasParaDescarte.get(1).getCarta().getValor();

        if (btnDescartar != null) {
            btnDescartar.setDisable(!sonPar);
        }
    }

    @FXML
    private void descartarPar() {
        if (seleccionadasParaDescarte.size() != 2) {
            return;
        }

        Carta uno = seleccionadasParaDescarte.get(0).getCarta();
        Carta dos = seleccionadasParaDescarte.get(1).getCarta();

        controlador.descartarPares(humano, uno, dos);
        finalizarTurno();
    }

    @FXML
    private void terminarTurno() {
        finalizarTurno();
    }

    private void finalizarTurno() {
        ocultarBotonesDeDescarte();
        limpiarSeleccion();
        refrescarTodosLosPaneles();

        controlador.pasarTurno();
        controlador.revisarJugadores();

        procesarTurno();
    }

    private void finDelJuego() {
        bloquearTodasLasCartas();
        ocultarBotonesDeDescarte();
        try {
            controlador.cambiarSceneFin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refrescarTodosLosPaneles() {
        for (Map.Entry<Jugador, FlowPane> entrada : panelDeJugador.entrySet()) {
            mostrarMano(entrada.getValue(), entrada.getKey());
        }
    }

    private void limpiarSeleccion() {
        for (CartaGUI cartaGUI : seleccionadasParaDescarte) {
            cartaGUI.deseleccionar();
        }
        seleccionadasParaDescarte.clear();
    }

    private void ocultarBotonesDeDescarte() {
        if (btnDescartar != null) {
            btnDescartar.setVisible(false);
            btnDescartar.setManaged(false);
            btnDescartar.setDisable(true);
        }
        if (btnTerminarTurno != null) {
            btnTerminarTurno.setVisible(false);
            btnTerminarTurno.setManaged(false);
        }
    }

    private void bloquearTodasLasCartas() {
        for (FlowPane panel : panelDeJugador.values()) {
            for (Node nodo : panel.getChildren()) {
                ((CartaGUI) nodo).setBloqueada(true);
            }
        }
    }

    public void setControlador(ControladorFX controlador){
        this.controlador = controlador;
    }
}