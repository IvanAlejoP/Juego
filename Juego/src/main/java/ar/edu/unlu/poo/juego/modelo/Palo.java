package ar.edu.unlu.poo.juego.modelo;

public enum Palo {
    COPA("C"),
    ESPADA("E"),
    BASTO("B"),
    ORO("O"),
    JOKER("J");

    private final String simbolo;

    Palo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getPalo(){
        return simbolo;
    }

}
