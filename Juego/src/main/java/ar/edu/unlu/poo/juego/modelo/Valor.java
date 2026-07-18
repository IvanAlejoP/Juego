package ar.edu.unlu.poo.juego.modelo;

public enum Valor {
    UNO(1),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    OCHO(8),
    NUEVE(9),
    MUCHACHO(10),
    CABALLO(11),
    REY(12),
    JOKER(13);

    private final int valor;

    Valor(int valor) {
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
