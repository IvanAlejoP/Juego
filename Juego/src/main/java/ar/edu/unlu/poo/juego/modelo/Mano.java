package ar.edu.unlu.poo.juego.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Mano {
    private ArrayList<Carta> cartas;

    public Mano(){
        this.cartas = new ArrayList<>();
    }

    //GETTERS
    public int getTamanio() {
        return this.cartas.size();
    }

    public Carta getCarta(int indice){
        if(this.cartas.get(indice) != null){
            return this.cartas.get(indice);
        }
        return null;
    }

    public ArrayList<Carta> getCartas(){
        return this.cartas;
    }
    //SETTERS


    //METODOS MIXTOS
    public void mezclarCartas(){
        Collections.shuffle(cartas);
    }

    public void agregarCarta(Carta carta){
        this.cartas.add(carta);
    }

    public Carta descartarCarta(int indice) {
        return this.cartas.remove(indice);
    }

    public void descartarPares(Carta c, Carta k){
        this.cartas.remove(c);
        this.cartas.remove(k);
    }
}
