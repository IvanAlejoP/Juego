package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.Jugador;
import ar.edu.unlu.poo.juego.modelo.JugadorHumano;
import ar.edu.unlu.poo.juego.modelo.JugadorIA;
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
            vista.mostrarMenu();
            switch (vista.obtenerOpcion()){
                case 1:
                    vista.mostrarMensaje("Preparando Juego:");
                    cantJugadores();
                    vista.mostrarMensaje("Iniciando Juego:");
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

    public void cantJugadores(){
        vista.mostrarMensaje("Elija cantidad de jugadores");
        vista.mostrarMensaje("MAX=4, MIN=2");
        vista.mostrarMensaje("Opcion: ");
        while(true){
            int opcion = vista.obtenerOpcion();
            if(opcion<5 && opcion>1){
                for(int i=0; i<opcion-1; i++){
                    Jugador j = new JugadorIA("IA");
                    partida.agregarJugador(j);
                }
                Jugador j = new JugadorHumano("TU");
                partida.agregarJugador(j);
                return;
            }
            vista.mostrarMensaje("Opcion invalida");
        }
    }


    @Override
    public void actualizar() {

    }
}
