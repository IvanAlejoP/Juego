package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.*;
import ar.edu.unlu.poo.juego.observer.Observador;
import ar.edu.unlu.poo.juego.vista.VistaConsola;

public class ControladorConsola implements Observador {
    private final Partida partida;
    private final VistaConsola vista;

    public ControladorConsola(Partida partida, VistaConsola vista){
        this.partida = partida;
        this.vista = vista;
        this.partida.agregarObservador(this);
    }

    public void menu(){
        while(true){
            vista.mostrarMenu();
            switch (vista.obtenerOpcion()){
                case 1:
                    vista.mostrarMensaje("\nPreparando Juego...");
                    cantJugadores();
                    vista.mostrarMensaje("\nJugando!!!");
                    jugando();
                    return;
                case 2:
                    vista.mostrarMensaje("\nHasta Luego!");
                    return;
                default:
                    vista.mostrarMensaje("\nOpcion invalida!");
                    break;
            }
        }
    }

    public void jugando(){
        partida.repartirCartas();
        partida.setPrimerJugadorEnTurno();
        actualizar();
        //ACTUALIZAR
        while(partida.juegoTerminado()){ //WHILE QUEDEN JUGADORES JUGANDO (J>1)

            if(partida.getJugadorEnTurno() instanceof JugadorHumano)
            {
                vista.mostrarMensaje("\nTU TURNO: ");
                jugarTurno(partida.getJugadorEnTurno());
            }
            else if(partida.getJugadorEnTurno() instanceof JugadorIA)
            {
                vista.mostrarMensaje("\nTURNO IA: ");
                partida.jugarIA(partida.getJugadorEnTurno());
            }

            partida.verificarJugadores();
            partida.pasarTurno();
        }
    }

    public void jugarTurno(Jugador jugador){
        while(true){
            vista.mostrarOpcionesDeJuego();
            switch(vista.obtenerOpcion()){
                case 1: //TOMAR CARTA Y DESCARTAR
                    turnoTomarCarta(jugador);
                    turnoDescartarCartas(jugador);
                    actualizar();
                    return;
                case 2:
                    jugador.mezclarMano();
                    actualizar();
                    break;
                default:
                    vista.mostrarMensaje("\nOpcion invalida!");
                    break;
            }
        }
    }

    public void turnoTomarCarta(Jugador jugador){
        vista.mostrarMensajeSinSalto("\nElija una carta del contrincante: ");
        int indice = vista.obtenerOpcion() - 1;

        while(partida.opcionValidaParaTomar(jugador, indice)){
            vista.mostrarMensaje("\nOpcion invalida!");
            vista.mostrarMensajeSinSalto("\nElija una carta del contrincante: ");
            indice = vista.obtenerOpcion();
        }

        vista.mostrarMensaje("\nTomando carta...");
        partida.tomarCarta(jugador, indice);

        actualizar();
    }

    public void turnoDescartarCartas(Jugador jugador){
        if(jugador.tienePares()){
            vista.mostrarMensaje("\nElija dos cartas del mismo par: ");
            vista.mostrarMensajeSinSalto("Carta UNO: ");
            int cartaUno = vista.obtenerOpcion();
            vista.mostrarMensajeSinSalto("Carta DOS: ");
            int cartaDos = vista.obtenerOpcion();

            while(
                cartaUno == cartaDos ||
                !partida.cartaValida(jugador, cartaUno) ||
                !partida.cartaValida(jugador, cartaDos) ||
                !partida.sonPares(jugador, cartaUno, cartaDos))
            {
                vista.mostrarMensaje("\nOpcion invalida!");
                vista.mostrarMensajeSinSalto("Carta UNO: ");
                cartaUno = vista.obtenerOpcion();
                vista.mostrarMensajeSinSalto("Carta DOS: ");
                cartaDos = vista.obtenerOpcion();
            }
            vista.mostrarMensaje("\nEliminando pares...");
            partida.descartarPares(jugador, jugador.getCarta(cartaUno), jugador.getCarta(cartaDos));
        }
        else{
            vista.mostrarMensaje("\nNo tienes pares para descartar...");
        }
    }

    public void cantJugadores(){
        while(true){
            vista.mostrarMensaje("\nElija cantidad de jugadores (MAX=4, MIN=2)");
            vista.mostrarMensajeSinSalto("Opcion: ");
            int opcion = vista.obtenerOpcion();
            if(opcion<5 && opcion>1){
                for(int i=0; i<opcion-1; i++){
                    Jugador j = new JugadorIA("IA");
                    partida.agregarJugador(j);
                }
                //String nombre = vista.obtenerString(); NO FUNCIONA?
                Jugador j = new JugadorHumano("TU");
                partida.agregarJugador(j);
                return;
            }
            vista.mostrarMensaje("\nOpcion invalida!");
        }
    }


    @Override
    public void actualizar() {
        vista.mostrarJugadores(partida.getJugadores());
    }
}
