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

    //GETTERS
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

                if(jugadores.get(siguiente).getEstado() == EstadoJugador.TERMINO && !juegoTerminado()){
                    return getSiguienteJugador(jugadores.get(siguiente));
                }

                break;
            }
        }

        return jugadores.get(siguiente);
    }

    public ArrayList<Jugador> getJugadores(){//MAL
        return this.jugadores;
    }


    public int getCantidadJugadores(){
        return this.jugadores.size();
    }


    //SETTERS
    public void setPrimerJugadorEnTurno(){
        this.jugadores.getFirst().setEstado(EstadoJugador.JUGANDO);
    }



    //METODOS MIXTOS
    public void iniciar(){
        repartirCartas();
        setPrimerJugadorEnTurno();

        notificarObservadores();
    }

    public void descartarPares(Jugador jugador, Carta uno, Carta dos){
        jugador.descartarPares(uno, dos);
    }

    public boolean sonPares(Jugador jugador, int cartaUno, int cartaDos){
        return jugador.getCarta(cartaUno).getValor() == jugador.getCarta(cartaDos).getValor();
    }

    public boolean esCartaValida(Jugador jugador, int indice){
        return jugador.existeCarta(indice);
    }

    public void agregarJugador(Jugador j){
        this.jugadores.add(j);
    }

    public void tomarCarta(Jugador jugador, int carta){
        jugador.tomarCarta(getSiguienteJugador(jugador).cartaRobada(carta));

        notificarObservadores();
    }

    public void pasarTurno(){
        Jugador actual = getJugadorEnTurno();
        Jugador siguiente = getSiguienteJugador(actual);

        actual.setEstado(EstadoJugador.ESPERANDO);
        siguiente.setEstado(EstadoJugador.JUGANDO);
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

    public boolean opcionValidaParaTomar(Jugador jugador, int indice) {
        return getSiguienteJugador(jugador).existeCarta(indice);
    }

    public void jugarIA(Jugador jugador){
        //TOMA CARTA
        int rand = (int)(Math.random() * getSiguienteJugador(jugador).getTamanioMano());
        tomarCarta(jugador, rand);

        //DESCARTA Y MEZCLA
        for(Carta c : jugador.getMano().getMano()){
            for(Carta k : jugador.getMano().getMano()){
                if(!c.equals(k)){
                    if(c.getValor() == k.getValor()){
                        jugador.descartarPares(c, k);
                        jugador.mezclarMano();
                        return;
                    }
                }
            }
        }
    }

    public void revisarJugadores(){
        for(Jugador j : jugadores){
            j.revisarse();
        }

        notificarObservadores();
    }

    public boolean juegoTerminado(){
        int contador = 0;
        for(Jugador j : jugadores){
            if(j.getEstado() == EstadoJugador.TERMINO){
                contador++;
            }
        }
        return contador == (getCantidadJugadores() - 1);
    }

    public void mezclarMano(Jugador jugador){
        jugador.mezclarMano();

        notificarObservadores();
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
