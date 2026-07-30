package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.*;
import ar.edu.unlu.poo.juego.observer.Observador;
import ar.edu.unlu.poo.juego.vista.VistaFX;

import java.io.IOException;
import java.util.ArrayList;

public class ControladorFX implements Observador {
    private Partida partida;
    private VistaFX vista;

    public ControladorFX(Partida partida, VistaFX vista){
        this.partida = partida;
        this.vista = vista;
        this.partida.agregarObservador(this);
    }

    public void cantJugadores(int cantJugadores){
        for(int i=0; i<cantJugadores-1; i++){
            Jugador j = new JugadorIA("IA");
            partida.agregarJugador(j);
        }
        Jugador j = new JugadorHumano("TU");
        partida.agregarJugador(j);
    }

    public void cambiarSceneMenu() throws IOException {
        vista.cambiarSceneMenu();
    }

    public void cambiarScenePrepJuego() throws IOException {
        vista.cambiarScenePrepJuego();
    }

    public void cambiarSceneJugando() throws IOException {
        partida.iniciar();
        vista.cambiarSceneJugando(partida.getJugadores());
    }

    public void cambiarSceneFin() throws IOException {
        Jugador perdedor = partida.getJugadorEnTurno();
        String nombre = (perdedor != null) ? perdedor.getNombre() : "";
        vista.cambiarSceneFin(nombre);
    }

    public void salir(){
        vista.salir();
    }

    //METODOS DE PARTIDA EXPUESTOS PARA LA LOGICA DE LA GUI DE JUEGO (JugandoController)

    public Jugador getJugadorEnTurno(){
        return partida.getJugadorEnTurno();
    }

    public Jugador getSiguienteJugador(Jugador jugador){
        return partida.getSiguienteJugador(jugador);
    }

    public ArrayList<Jugador> getJugadores(){
        return partida.getJugadores();
    }

    public void tomarCarta(Jugador jugador, int indice){
        partida.tomarCarta(jugador, indice);
    }

    public boolean sonPares(Jugador jugador, int cartaUno, int cartaDos){
        return partida.sonPares(jugador, cartaUno, cartaDos);
    }

    public void descartarPares(Jugador jugador, Carta uno, Carta dos){
        partida.descartarPares(jugador, uno, dos);
    }

    public void pasarTurno(){
        partida.pasarTurno();
    }

    public void revisarJugadores(){
        partida.revisarJugadores();
    }

    public boolean juegoTerminado(){
        return partida.juegoTerminado();
    }

    public void jugarIA(Jugador jugador){
        partida.jugarIA(jugador);
    }

    @Override
    public void actualizar() {
        vista.refrescarPantalla();
    }
}
