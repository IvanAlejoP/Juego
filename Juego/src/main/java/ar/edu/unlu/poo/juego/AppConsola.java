package ar.edu.unlu.poo.juego;

import ar.edu.unlu.poo.juego.controlador.ControladorConsola;
import ar.edu.unlu.poo.juego.modelo.Partida;
import ar.edu.unlu.poo.juego.vista.VistaConsola;

import java.rmi.RemoteException;

public class AppConsola {
    static void main(String[] args) throws RemoteException {
        ControladorConsola c = new ControladorConsola(new VistaConsola());
        c.menu();
    }
}
