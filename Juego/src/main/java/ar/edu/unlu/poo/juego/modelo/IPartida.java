package ar.edu.unlu.poo.juego.modelo;

import ar.edu.unlu.poo.juego.observer.Observable;
import ar.edu.unlu.poo.juego.observer.Observador;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IPartida extends IObservableRemoto{
    //GETTERS
    Jugador getJugadorEnTurno() throws RemoteException;

    Jugador getSiguienteJugador(Jugador jugador) throws RemoteException;

    ArrayList<Jugador> getJugadores() throws RemoteException;

    int getCantidadJugadores() throws RemoteException;

    //SETTERS
    void setPrimerJugadorEnTurno() throws RemoteException;

    //METODOS MIXTOS
    void iniciar() throws RemoteException;

    void descartarPares(Jugador jugador, Carta uno, Carta dos) throws RemoteException;

    boolean sonPares(Jugador jugador, int cartaUno, int cartaDos) throws RemoteException;

    boolean esCartaValida(Jugador jugador, int indice) throws RemoteException;

    void agregarJugador(Jugador j) throws RemoteException;

    void tomarCarta(Jugador jugador, int carta) throws RemoteException;

    void pasarTurno() throws RemoteException;

    void repartirCartas() throws RemoteException;

    boolean opcionValidaParaTomar(Jugador jugador, int indice) throws RemoteException;

    void jugarIA(Jugador jugador) throws RemoteException;

    void revisarJugadores() throws RemoteException;

    boolean juegoTerminado() throws RemoteException;

    void mezclarMano(Jugador jugador) throws RemoteException;

    void setCantidadJugadores(int cantidad) throws RemoteException;

}
