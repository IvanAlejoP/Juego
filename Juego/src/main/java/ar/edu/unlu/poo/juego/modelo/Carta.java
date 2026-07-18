package ar.edu.unlu.poo.juego.modelo;

public class Carta {
    private Palo palo;
    private Valor valor;

    public Carta(Palo palo, Valor valor){
        this.palo = palo;
        this.valor = valor;
    }

    public Valor getValor(){
        return this.valor;
    }
}
