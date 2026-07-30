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
    private Button btnDescartar;

    @FXML
    private Button btnTerminarTurno;

    // Orden visual alrededor de la mesa, empezando por "vos" (abajo)
    private List<FlowPane> paneles;

    // A qué panel corresponde cada jugador
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

    public void mostrarJugadores(ArrayList<Jugador> jugadores) {

        ocultarPaneles();
        panelDeJugador.clear();

        humano = jugadores.stream()
                .filter(j -> j instanceof JugadorHumano)
                .findFirst()
                .orElse(null);

        List<Jugador> ordenVisual = rotarDesdeHumano(jugadores);

        for (int i = 0; i < ordenVisual.size(); i++) {
            Jugador jugador = ordenVisual.get(i);
            FlowPane panel = paneles.get(i);

            panel.setVisible(true);
            panel.setManaged(true);
            panelDeJugador.put(jugador, panel);

            mostrarMano(panel, jugador);
        }

        procesarTurno();
    }

    // Reordena la lista de jugadores para que "vos" quedes siempre abajo, respetando
    // el orden real de turnos (solo cambia cómo se acomodan en la mesa, no la lógica)
    private List<Jugador> rotarDesdeHumano(ArrayList<Jugador> jugadores) {
        List<Jugador> resultado = new ArrayList<>();

        if (humano == null) {
            resultado.addAll(jugadores);
            return resultado;
        }

        int indiceHumano = jugadores.indexOf(humano);
        for (int i = 0; i < jugadores.size(); i++) {
            resultado.add(jugadores.get((indiceHumano + i) % jugadores.size()));
        }
        return resultado;
    }

    // Requisito 0: solo se ven los valores de TUS cartas; las del resto se muestran boca abajo
    private void mostrarMano(FlowPane panel, Jugador jugador) {

        panel.getChildren().clear();

        boolean esHumano = (jugador == humano);

        for (Carta carta : jugador.getMano().getMano()) {
            CartaGUI cartaGUI = new CartaGUI(carta, !esHumano);
            cartaGUI.setBloqueada(true); // arranca bloqueada; procesarTurno() habilita lo que corresponda
            panel.getChildren().add(cartaGUI);
        }
    }

    public void actualizarTurno(Jugador jugador) {
        if (jugador != null) {
            Turno.setText("Turno: " + jugador.getNombre());
        }
    }

    // Punto central: decide qué se puede clickear según de quién es el turno.
    // Requisito 1: si no es tu turno, queda tdo bloqueado hasta que la IA termine de jugar.
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
            // Pequeña pausa para que se note que la IA "está jugando" antes de resolver su turno
            PauseTransition espera = new PauseTransition(Duration.seconds(0.8));
            espera.setOnFinished(e -> jugarTurnoIA(jugadorEnTurno));
            espera.play();
        }
    }

    private void jugarTurnoIA(Jugador jugadorIA) {
        controlador.jugarIA(jugadorIA);
        finalizarTurno();
    }

    // Requisito 2 (parte 1): solo se pueden clickear las cartas del jugador siguiente,
    // que es a quien realmente se le puede robar una carta
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

    // Al clickear una carta del contrincante, el resto se bloquea (requisito 2) y se pasa
    // a la fase de descarte
    private void robarCarta(int indice) {
        bloquearTodasLasCartas();

        controlador.tomarCarta(humano, indice);
        refrescarTodosLosPaneles();

        habilitarFaseDeDescarte();
    }

    // Requisito 2 (parte 2): ahora solo tus propias cartas son seleccionables (hasta 2)
    private void habilitarFaseDeDescarte() {
        FlowPane panelHumano = panelDeJugador.get(humano);

        for (Node nodo : panelHumano.getChildren()) {
            CartaGUI cartaGUI = (CartaGUI) nodo;
            cartaGUI.setBloqueada(false);
            cartaGUI.setAccionAlHacerClick(() -> alternarSeleccionParaDescarte(cartaGUI));
        }

        btnTerminarTurno.setVisible(true);
        btnTerminarTurno.setManaged(true);
        btnDescartar.setVisible(true);
        btnDescartar.setManaged(true);
        btnDescartar.setDisable(true);
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

        // Si hay exactamente 2 cartas del mismo valor seleccionadas, se habilita "Descartar Par"
        boolean sonPar = seleccionadasParaDescarte.size() == 2
                && seleccionadasParaDescarte.get(0).getCarta().getValor()
                == seleccionadasParaDescarte.get(1).getCarta().getValor();

        btnDescartar.setDisable(!sonPar);
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

    // Requisito 3: al terminar la partida se muestra la pantalla de Fin con el perdedor
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
        btnDescartar.setVisible(false);
        btnDescartar.setManaged(false);
        btnDescartar.setDisable(true);
        btnTerminarTurno.setVisible(false);
        btnTerminarTurno.setManaged(false);
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
