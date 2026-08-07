package ar.edu.unlu.poo.juego.modelo;

import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

public class Partida extends ObservableRemoto implements IPartida {
    private ArrayList<Jugador> jugadores;
    private Mazo mazo;
    private static final int CANTIDAD_JUGADORES_REQUERIDOS = 2; // Ajustar según las reglas de tu juego

    public Partida() throws RemoteException {
        this.jugadores = new ArrayList<>();
        this.mazo = new Mazo();
    }

    // GETTERS
    @Override
    public Jugador getJugadorEnTurno() throws RemoteException {
        for (Jugador j : jugadores) {
            if (j.getEstado() == EstadoJugador.JUGANDO) {
                return j;
            }
        }
        return null;
    }

    @Override
    public Jugador getSiguienteJugador(Jugador jugador) throws RemoteException {
        int siguiente = 0;
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).equals(jugador)) {
                siguiente = (i + 1) % jugadores.size();

                // Si el siguiente jugador ya terminó, buscamos recursivamente al siguiente en juego
                if (jugadores.get(siguiente).getEstado() == EstadoJugador.TERMINO && !juegoTerminado()) {
                    return getSiguienteJugador(jugadores.get(siguiente));
                }

                break;
            }
        }
        return jugadores.get(siguiente);
    }

    @Override
    public ArrayList<Jugador> getJugadores() throws RemoteException {
        return this.jugadores;
    }

    @Override
    public int getCantidadJugadores() throws RemoteException {
        return this.jugadores.size();
    }

    // SETTERS
    @Override
    public void setPrimerJugadorEnTurno() throws RemoteException {
        for (Jugador j : jugadores) {
            j.setEstado(EstadoJugador.ESPERANDO);
        }
        if (!jugadores.isEmpty()) {
            this.jugadores.getFirst().setEstado(EstadoJugador.JUGANDO);
        }
    }

    // MÉTODOS DE LÓGICA DE JUEGO

    @Override
    public void agregarJugador(Jugador j) throws RemoteException {
        this.jugadores.add(j);

        // Notifica a los observadores que un nuevo jugador se ha unido
        notificarObservadores(EventoJuego.JUGADOR_AGREGADO);

        // Si se alcanza la cantidad de jugadores necesaria, se inicia la partida automáticamente
        if (this.jugadores.size() == CANTIDAD_JUGADORES_REQUERIDOS) {
            iniciar();
        }
    }

    @Override
    public void iniciar() throws RemoteException {
        repartirCartas();
        setPrimerJugadorEnTurno();

        // Notifica el inicio de la partida a todos los clientes
        notificarObservadores(EventoJuego.INICIO_PARTIDA);
    }

    @Override
    public void pasarTurno() throws RemoteException {
        revisarJugadores();

        if (juegoTerminado()) {
            notificarObservadores(EventoJuego.FIN_DE_JUEGO);
            return;
        }

        Jugador actual = getJugadorEnTurno();
        if (actual != null) {
            Jugador siguiente = getSiguienteJugador(actual);

            // Solo pasa a ESPERANDO si el jugador no ha terminado la partida
            if (actual.getEstado() != EstadoJugador.TERMINO) {
                actual.setEstado(EstadoJugador.ESPERANDO);
            }

            siguiente.setEstado(EstadoJugador.JUGANDO);
        }

        notificarObservadores(EventoJuego.TURNO_CAMBIADO);
    }

    @Override
    public void tomarCarta(Jugador jugador, int carta) throws RemoteException {
        jugador.tomarCarta(getSiguienteJugador(jugador).cartaRobada(carta));
        revisarJugadores();

        notificarObservadores(EventoJuego.CARTA_TOMADA);
    }

    @Override
    public void descartarPares(Jugador jugador, Carta uno, Carta dos) throws RemoteException {
        jugador.descartarPares(uno, dos);
        revisarJugadores();

        notificarObservadores(EventoJuego.PARES_DESCARTADOS);
    }

    @Override
    public boolean sonPares(Jugador jugador, int cartaUno, int cartaDos) throws RemoteException {
        return jugador.getCarta(cartaUno).getValor() == jugador.getCarta(cartaDos).getValor();
    }

    @Override
    public boolean esCartaValida(Jugador jugador, int indice) throws RemoteException {
        return jugador.existeCarta(indice);
    }

    @Override
    public void repartirCartas() throws RemoteException {
        Collections.shuffle(jugadores);

        while (!mazo.mazoVacio()) {
            for (Jugador j : jugadores) {
                if (mazo.mazoVacio()) {
                    return;
                }
                j.tomarCarta(mazo.sacarCarta());
            }
        }
    }

    @Override
    public boolean opcionValidaParaTomar(Jugador jugador, int indice) throws RemoteException {
        return getSiguienteJugador(jugador).existeCarta(indice);
    }

    @Override
    public void jugarIA(Jugador jugador) throws RemoteException {
        // TOMA CARTA
        int rand = (int) (Math.random() * getSiguienteJugador(jugador).getTamanioMano());
        tomarCarta(jugador, rand);

        // DESCARTA Y MEZCLA
        for (Carta c : jugador.getMano().getMano()) {
            for (Carta k : jugador.getMano().getMano()) {
                if (!c.equals(k)) {
                    if (c.getValor() == k.getValor()) {
                        descartarPares(jugador, c, k);
                        jugador.mezclarMano();
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void revisarJugadores() throws RemoteException {
        for (Jugador j : jugadores) {
            j.revisarse();
        }
    }

    @Override
    public boolean juegoTerminado() throws RemoteException {
        int contadorTerminados = 0;
        for (Jugador j : jugadores) {
            if (j.getEstado() == EstadoJugador.TERMINO) {
                contadorTerminados++;
            }
        }
        return contadorTerminados == (getCantidadJugadores() - 1);
    }

    @Override
    public void mezclarMano(Jugador jugador) throws RemoteException {
        jugador.mezclarMano();
        notificarObservadores(EventoJuego.TURNO_CAMBIADO);
    }
}