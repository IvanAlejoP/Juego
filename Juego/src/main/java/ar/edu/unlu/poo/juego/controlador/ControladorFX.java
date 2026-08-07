package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.*;
import ar.edu.unlu.poo.juego.vista.VistaFX;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import javafx.application.Platform;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ControladorFX implements IControladorRemoto {
    private IPartida partida;
    private VistaFX vista;
    private Jugador yo;

    public ControladorFX(VistaFX vista) throws RemoteException {
        this.vista = vista;
    }

    /**
     * Registrar jugador local en la partida remota.
     */
    public void registrarJugador(String nombre) {
        try {
            this.yo = new JugadorHumano(nombre.trim());
            partida.agregarJugador(this.yo);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Jugador getYo() {
        return this.yo;
    }

    // NAVEGACIÓN Y VISTA
    public void cambiarSceneMenu() throws IOException {
        vista.cambiarSceneMenu();
    }

    public void cambiarScenePrepJuego() throws IOException {
        vista.cambiarScenePrepJuego();
    }

    public void cambiarSceneFin() throws IOException {
        Jugador perdedor = getJugadorEnTurno();
        String nombrePerdedor = (perdedor != null) ? perdedor.getNombre() : "Alguien";
        vista.cambiarSceneFin(nombrePerdedor);
    }

    public void salir() {
        vista.salir();
    }

    // DELEGACIÓN A LA PARTIDA REMOTA

    public void cantJugadores(int cantidad) {
        try {
            partida.setCantidadJugadores(cantidad);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Jugador getJugadorEnTurno() {
        try {
            return partida.getJugadorEnTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Jugador getSiguienteJugador(Jugador jugador) {
        try {
            return partida.getSiguienteJugador(jugador);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Jugador> getJugadores() {
        try {
            return partida.getJugadores();
        } catch (RemoteException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void tomarCarta(Jugador jugador, int indice) {
        try {
            partida.tomarCarta(jugador, indice);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean sonPares(Jugador jugador, int cartaUno, int cartaDos) {
        try {
            return partida.sonPares(jugador, cartaUno, cartaDos);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void descartarPares(Jugador jugador, Carta uno, Carta dos) {
        try {
            partida.descartarPares(jugador, uno, dos);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void mezclarMano(Jugador jugador) {
        try {
            partida.mezclarMano(jugador);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void pasarTurno() {
        try {
            partida.pasarTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean juegoTerminado() {
        try {
            return partida.juegoTerminado();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void jugarIA(Jugador jugadorIA) {
        try {
            partida.jugarIA(jugadorIA);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void revisarJugadores() {
        try {
            partida.revisarJugadores();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // MÉTODOS OBLIGATORIOS DE RMI-MVC

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.partida = (IPartida) t;
    }

    @Override
    public void actualizar(IObservableRemoto modelo, Object evento) throws RemoteException {
        if (evento instanceof EventoJuego) {
            EventoJuego e = (EventoJuego) evento;

            // Se usa Platform.runLater porque los eventos RMI entran en hilos de red
            Platform.runLater(() -> {
                try {
                    switch (e) {
                        case JUGADOR_AGREGADO:
                            vista.refrescarSalaEspera(getJugadores());
                            break;

                        case INICIO_PARTIDA:
                            vista.cambiarSceneJugando(getJugadores(), this.yo);
                            break;

                        case TURNO_CAMBIADO:
                        case CARTA_TOMADA:
                        case PARES_DESCARTADOS:
                            vista.refrescarPantalla(getJugadores(), this.yo, getJugadorEnTurno());
                            break;

                        case FIN_DE_JUEGO:
                            Jugador perdedor = getJugadorEnTurno();
                            String nombrePerdedor = (perdedor != null) ? perdedor.getNombre() : "Alguien";
                            vista.cambiarSceneFin(nombrePerdedor);
                            break;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }
}