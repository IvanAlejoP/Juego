package ar.edu.unlu.poo.juego.modelo;

public enum EstadoJugador {
    JUGANDO(1),
    ESPERANDO(2),
    TERMINO(3);

    private int estado;

    EstadoJugador(int estado) {
        this.estado = estado;
    }

    public int getEstado(){
        return this.estado;
    }

    public void setEstado(int indice) {
        if(!(indice < 1 || indice > 3)){
            this.estado = indice;
        }
    }
}
