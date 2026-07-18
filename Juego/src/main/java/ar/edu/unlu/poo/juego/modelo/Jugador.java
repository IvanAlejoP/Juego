package ar.edu.unlu.poo.juego.modelo;

public abstract class Jugador {
    private String nombre;
    private Mano mano;
    private EstadoJugador estadoJugador;

    public Jugador(String nombre){
        this.nombre = nombre;
        this.mano = new Mano();
        setEstado(3);
    }

    public void descartarCarta(int indice) {
        if(indice >= 0 && indice <= mano.getTamanio()){
            mano.descartarCarta(indice);
        }
    }

    public void tomarCarta(Carta carta){
        this.mano.agregarCarta(carta);
    }

    public void setEstado(int indice){
        assert this.estadoJugador != null;
        this.estadoJugador.setEstado(indice);
    }

    public EstadoJugador getEstado(){
        return this.estadoJugador;
    }

}
