package ar.edu.unlu.poo.juego.modelo;

public abstract class Jugador {
    private String nombre;
    private Mano mano;
    private EstadoJugador estado;

    public Jugador(String nombre){
        this.nombre = nombre;
        this.mano = new Mano();
        this.estado = EstadoJugador.ESPERANDO;
    }

    //GETTERS
    public String getNombre(){
        return this.nombre;
    }

    public EstadoJugador getEstado(){
        return this.estado;
    }

    public int getTamanioMano(){
        return mano.getTamanio();
    }

    public Mano getMano(){
        return this.mano;
    }

    public Carta getCarta(int indice){
        return this.mano.getCarta(indice);
    }


    //SETTERS
    public void setEstado(EstadoJugador estado){
        this.estado = estado;
    }




    //METODOS MIXTOS
    public boolean existeCarta(int indice){
        return this.mano.existeCarta(indice);
    }

    public void descartarPares(Carta cartaUno, Carta cartaDos){
        this.mano.descartarPares(cartaUno, cartaDos);
    }

    public Carta cartaRobada(int indice) {
        if(indice >= 0 && indice < mano.getTamanio()){
            return mano.descartarCarta(indice);
        }
        return null;
    }

    public void tomarCarta(Carta carta){
        this.mano.agregarCarta(carta);
    }

    public void mezclarMano(){
        this.mano.mezclarCartas();
    }

    public boolean jugadorTermino(){
        return getEstado() == EstadoJugador.TERMINO;
    }

    public boolean tienePares(){
        return mano.tienePares();
    }

    public void revisarse() {//HAY QUE RENOMBRAR ESTO
        if(mano.isVacio()) {
            setEstado(EstadoJugador.TERMINO);
        }
    }
}
