package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.Jugador;
import ar.edu.unlu.poo.juego.modelo.JugadorHumano;
import ar.edu.unlu.poo.juego.modelo.JugadorIA;
import ar.edu.unlu.poo.juego.modelo.Partida;
import ar.edu.unlu.poo.juego.observer.Observador;
import ar.edu.unlu.poo.juego.vista.VistaFX;

import java.io.IOException;

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

    public void salir(){
        vista.salir();
    }

    @Override
    public void actualizar() {
        vista.refrescarPantalla();
    }
}
