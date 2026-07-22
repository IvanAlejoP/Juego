package ar.edu.unlu.poo.juego.modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ArrayList<Carta> mazo;

    public Mazo(){
        this.mazo = new ArrayList<>();
        llenarMazo();
    }

    //GETTERS
    //SETTERS

    private void llenarMazo(){
        for(Palo p : Palo.values()){
            if(p != Palo.JOKER){
                for(Valor v : Valor.values()){
                    if(v != Valor.JOKER){
                        this.mazo.add(new Carta(p, v));
                    }
                }
            }
        }

        //AGREGAMOS EL JOKER AL FINAL
        this.mazo.add(new Carta(Palo.JOKER, Valor.JOKER));
        Collections.shuffle(mazo);
    }

    public Carta sacarCarta(){
        return mazo.removeFirst();
    }

    public boolean mazoVacio(){
        return mazo.isEmpty();
    }
}
