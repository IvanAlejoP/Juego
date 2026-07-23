package ar.edu.unlu.poo.juego.modelo;

public class JugadorIA extends Jugador{

    public JugadorIA(String nombre) {
        super(nombre);
    }

    public void tomarCartaIA(Jugador jugador){
        int rand = (int)(Math.random() * jugador.getTamanioMano() + 1);
        tomarCarta(jugador.cartaRobada(rand));
    }//EN UN FUTURO CERCANO PODEMOS HACER QUE APARTE DE TOMAR UNA CARTA, LA DEVUELVA
     //PARA MOSTRARLE AL JUGADOR QUÉ CARTA LE ROBARON.

    public void mezclarYDescartarIA(){
        mezclarMano();
        for(int i=0; i<=getTamanioMano(); i++){
            for(int k=i+1; k<=getTamanioMano(); k++){
                if(getCarta(i).getValor() == getCarta(k).getValor()){
                    descartarPares(getCarta(i), getCarta(k));
                }
            }
        }
    }
}
