package ar.edu.unlu.poo.juego.modelo;

import java.util.ArrayList;

public class Mano {
    ArrayList<Carta> mano;

    public Mano(){
        this.mano = new ArrayList<>();
    }

    public void agregarCarta(Carta carta){
        this.mano.add(carta);
    }

    public Carta getCarta(int indice){
        if(this.mano.get(indice) != null){
            return this.mano.get(indice);
        }
        return null;
    }

    public Carta descartarCarta(int indice) {
        return this.mano.remove(indice);
    }


    public int getTamanio() {
        return this.mano.size();
    }
}
