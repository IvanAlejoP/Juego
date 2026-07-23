package ar.edu.unlu.poo.juego.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mano {
    private ArrayList<Carta> mano;

    public Mano(){
        this.mano = new ArrayList<>();
    }

    //GETTERS

    public int getTamanio() {
        return this.mano.size();
    }

    public Carta getCarta(int indice){
        if(existeCarta(indice)){
            return this.mano.get(indice);
        }
        return null;
    }

    //CORREGIDO?
    public ArrayList<Carta> getMano(){
        return this.mano;
    }



    //SETTERS


    //METODOS MIXTOS
    public boolean existeCarta(int indice){
        return indice >= 0 && indice < getTamanio();
    }

    public void mezclarCartas(){
        Collections.shuffle(mano);
    }

    public void agregarCarta(Carta carta){
        this.mano.add(carta);
    }

    public Carta descartarCarta(int indice) {
        return this.mano.remove(indice);
    }

    public void descartarPares(Carta cartaUno, Carta cartaDos){
        this.mano.remove(cartaUno);
        this.mano.remove(cartaDos);
    }

    public boolean tienePares(){
        for(int i=0; i<=getTamanio(); i++){
            for(int k=i+1; k<getTamanio(); k++){
                if(getCarta(i).getValor() == getCarta(k).getValor()){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isVacio(){
        return this.mano.isEmpty();
    }
}
