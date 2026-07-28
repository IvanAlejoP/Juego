package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.Partida;
import ar.edu.unlu.poo.juego.observer.Observador;
import ar.edu.unlu.poo.juego.vista.VistaFX;

public class ControladorFX implements Observador {
    private Partida partida;
    private VistaFX vista;

    public ControladorFX(Partida partida, VistaFX vista){
        this.partida = partida;
        this.vista = vista;
        this.partida.agregarObservador(this);
    }



    @Override
    public void actualizar() {

    }
}
