package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.Partida;
import ar.edu.unlu.poo.juego.observer.Observador;
import ar.edu.unlu.poo.juego.vista.VistaConsola;

public class ControladorConsola implements Observador {
    private Partida partida;
    private VistaConsola vista;

    public ControladorConsola(Partida partida, VistaConsola vista){
        this.partida = partida;
        this.vista = vista;
        this.partida.agregarObservador(this);
    }

    public void menu(){
        while(true){
            switch (vista.obtenerOpcion()){
                case 1:
                    vista.mostrarMensaje("Iniciando Juego");
                    return;
                case 2:
                    vista.mostrarMensaje("Hasta Luego");
                    return;
                default:
                    vista.mostrarMensaje("Opcion invalida");
                    break;
            }
        }
    }

    @Override
    public void actualizar() {

    }
}
