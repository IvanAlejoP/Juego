package ar.edu.unlu.poo.juego.modelo;

import java.util.Objects;

public class Carta {
    private final Palo palo;
    private final Valor valor;

    public Carta(Palo palo, Valor valor){
        this.palo = palo;
        this.valor = valor;
    }

    public Valor getValor(){
        return this.valor;
    }

    public Palo getPalo(){
        return this.palo;
    }





    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Carta otra = (Carta) obj;

        return valor == otra.valor && palo == otra.palo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(palo, valor);
    }
}
