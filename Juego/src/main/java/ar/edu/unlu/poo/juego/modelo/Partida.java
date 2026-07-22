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
                break;
            }
        }
        return jugadores.get(siguiente);
    }

    public ArrayList<Jugador> getJugadores(){
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
    public void descartarPares(Jugador jugador, Carta uno, Carta dos){
        jugador.descartarPares(uno, dos);
    }

    public boolean sonPares(Jugador jugador, int cartaUno, int cartaDos){
        return jugador.existeCarta(cartaUno) && jugador.existeCarta(cartaDos);
    }

    public boolean cartaValida(Jugador jugador, int carta){
        return carta > 0 && carta <= jugador.getTamanioMano();
    }

    public void agregarJugador(Jugador j){
        this.jugadores.add(j);
    }

    public void tomarCarta(Jugador jugador, int carta){
        jugador.tomarCarta(getSiguienteJugador(jugador).cartaRobada(carta));
    }

    public void pasarTurno(){
        Jugador j = getJugadorEnTurno();
        if(j.jugadorTermino()){
            j.setEstado(EstadoJugador.TERMINO);
        }
        else{
            j.setEstado(EstadoJugador.ESPERANDO);
        }

        if(juegoTerminado()) return;

        Jugador i = getSiguienteJugador(j);
        while(!i.jugadorTermino()){
            i = getSiguienteJugador(i);
        }

        i.setEstado(EstadoJugador.JUGANDO);

        //VERIFICAR JUGADORES QUE TERMINARON
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
        return getSiguienteJugador(jugador).getTamanioMano() <= indice || indice < 0;
    }

    public void jugarIA(Jugador jugador){
        //TOMA CARTA
        int rand = (int)(Math.random() * getSiguienteJugador(jugador).getTamanioMano() + 1);
        tomarCarta(jugador, rand);

        //DESCARTA Y MEZCLA
        for(Carta c : jugador.getMano().getCartas()){
            for(Carta k : jugador.getMano().getCartas()){
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

    public void verificarJugadores(){
        for(Jugador j : jugadores){
            if(j.getTamanioMano() == 0){
                j.setEstado(EstadoJugador.TERMINO);
            }
        }
    }

    public boolean juegoTerminado(){
        int contador = 0;
        for(Jugador j : jugadores){
            if(j.getEstado() == EstadoJugador.TERMINO){
                contador++;
            }
        }
        return contador <= (getCantidadJugadores() - 1);
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
