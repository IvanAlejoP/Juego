package ar.edu.unlu.poo.juego.modelo;

import ar.edu.unlu.poo.juego.observer.Observable;
import ar.edu.unlu.poo.juego.observer.Observador;

import java.util.ArrayList;
import java.util.Collections;

public class Partida implements Observable {
    private ArrayList<Jugador> jugadores;
    private Mazo mazo;
    private ArrayList<Observador> observadores;

    public Partida(){
        this.jugadores = new ArrayList<>();
        this.mazo = new Mazo();
        this.observadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador j){
        this.jugadores.add(j);
    }

    public void setPrimerJugadorEnTurno(){
        this.jugadores.getFirst().setEstado(1);
    }

    public Jugador getJugadorEnTurno(){
        for(Jugador j : jugadores){
            if(j.getEstado() == EstadoJugador.JUGANDO){
                return j;
            }
        }
        return null;
    }

    public Jugador getSiguienteJugador(Jugador jugador){
        int siguiente = 0;
        for(int i=0; i<jugadores.size(); i++){
            if(jugadores.get(i) == jugador){
                siguiente = (i + 1) % jugadores.size();
                break;
            }
        }
        return jugadores.get(siguiente);
    }

    public void pasarTurno(){
        Jugador j = getJugadorEnTurno();
        j.setEstado(2);

        Jugador i = getSiguienteJugador(j);
        i.setEstado(1);
    }

    public void repartirCartas(){
        Collections.shuffle(jugadores);

        while(!mazo.mazoVacio()){
            for(Jugador j : jugadores){
                if(mazo.mazoVacio()){
                    return;
                }
                j.tomarCarta(mazo.sacarCarta());
            }
        }
    }

    //METODOS DE OBSERVADOR
    @Override
    public void agregarObservador(Observador observador) {
        this.observadores.add(observador);
    }

    @Override
    public void quitarObservador(Observador observador) {
        this.observadores.remove(observador);
    }

    @Override
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }
}
