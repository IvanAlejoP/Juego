package ar.edu.unlu.poo.juego;

import ar.edu.unlu.poo.juego.controlador.ControladorConsola;
import ar.edu.unlu.poo.juego.modelo.Partida;
import ar.edu.unlu.poo.juego.vista.VistaConsola;

public class AppConsola {
    static void main(String[] args) {
        ControladorConsola c = new ControladorConsola(new Partida(), new VistaConsola());
        c.menu();
    }
}
