package ar.edu.unlu.poo.juego.controlador;

import ar.edu.unlu.poo.juego.modelo.*;
import ar.edu.unlu.poo.juego.vista.VistaConsola;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import javax.swing.*;
import java.rmi.RemoteException;

public class ControladorConsola implements IControladorRemoto {
    private IPartida partida;
    private VistaConsola vista;
    private Jugador yo;

    public ControladorConsola(VistaConsola vista) throws RemoteException {
        this.vista = vista;
    }

    /**
     * Registro inicial del jugador.
     */
    public void menu() throws RemoteException {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre de jugador:");

        while (nombre == null || nombre.trim().isEmpty()) {
            vista.mostrarMensaje("El nombre no puede estar vacío.");
            nombre = JOptionPane.showInputDialog("Ingrese su nombre de jugador:");
        }

        this.yo = new JugadorHumano(nombre.trim());
        vista.mostrarMensaje("\n¡Registro exitoso! Bienvenido, " + this.yo.getNombre());

        // Registra al jugador en la partida remota
        partida.agregarJugador(this.yo);
        vista.mostrarMensaje("Esperando a que se completen los jugadores...");
    }

    /**
     * Procesa las acciones del turno únicamente para el cliente local.
     */
    private void procesarTurnoLocal() {
        try {
            boolean turnoFinalizado = false;

            while (!turnoFinalizado) {
                vista.mostrarOpcionesDeJuego();
                int opcion = vista.obtenerOpcion();

                switch (opcion) {
                    case 1: // TOMAR CARTA Y DESCARTAR
                        turnoTomarCarta(this.yo);
                        turnoDescartarCartas(this.yo);
                        turnoFinalizado = true;

                        vista.mostrarMensaje("\nPasando Turno...");
                        partida.pasarTurno();
                        break;

                    case 2: // MEZCLAR MANO
                        partida.mezclarMano(this.yo);
                        vista.mostrarMensaje("\nHas mezclado tu mano.");
                        break;

                    default:
                        vista.mostrarMensaje("\n¡Opción inválida!");
                        break;
                }
            }
        } catch (RemoteException e) {
            vista.mostrarMensaje("Error de comunicación con el servidor: " + e.getMessage());
        }
    }

    public void turnoTomarCarta(Jugador jugador) throws RemoteException {
        vista.mostrarMensajeSinSalto("\nElija una carta del contrincante: ");
        int indice = vista.obtenerOpcion() - 1;

        while (!partida.opcionValidaParaTomar(jugador, indice)) {
            vista.mostrarMensaje("\n¡Opción inválida!");
            vista.mostrarMensajeSinSalto("Elija una carta del contrincante: ");
            indice = vista.obtenerOpcion() - 1;
        }

        vista.mostrarMensaje("\nTomando carta...");
        partida.tomarCarta(jugador, indice);
    }

    public void turnoDescartarCartas(Jugador jugador) throws RemoteException {
        if (jugador.tienePares()) {
            vista.mostrarMensaje("\nElija dos cartas del mismo par: ");
            vista.mostrarMensajeSinSalto("Carta UNO: ");
            int cartaUno = vista.obtenerOpcion() - 1;
            vista.mostrarMensajeSinSalto("Carta DOS: ");
            int cartaDos = vista.obtenerOpcion() - 1;

            while (
                    cartaUno == cartaDos ||
                            !partida.esCartaValida(jugador, cartaUno) ||
                            !partida.esCartaValida(jugador, cartaDos) ||
                            !partida.sonPares(jugador, cartaUno, cartaDos))
            {
                vista.mostrarMensaje("\n¡Opción inválida!");
                vista.mostrarMensajeSinSalto("Carta UNO: ");
                cartaUno = vista.obtenerOpcion() - 1;
                vista.mostrarMensajeSinSalto("Carta DOS: ");
                cartaDos = vista.obtenerOpcion() - 1;
            }

            vista.mostrarMensaje("\nEliminando pares...");
            partida.descartarPares(jugador, jugador.getCarta(cartaUno), jugador.getCarta(cartaDos));
        } else {
            vista.mostrarMensaje("\nNo tienes pares para descartar.");
        }
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T t) throws RemoteException {
        this.partida = (IPartida) t;
    }

    /**
     * Canal de eventos de RMI-MVC. Se activa en todos los clientes cuando
     * el modelo invoca notificarObservadores(evento).
     */
    @Override
    public void actualizar(IObservableRemoto iObservableRemoto, Object evento) throws RemoteException {
        if (evento instanceof EventoJuego) {
            EventoJuego e = (EventoJuego) evento;

            switch (e) {
                case JUGADOR_AGREGADO:
                    vista.mostrarMensaje("\nUn nuevo jugador se ha unido a la sala.");
                    vista.mostrarJugadores(partida.getJugadores(), this.yo);
                    break;

                case INICIO_PARTIDA:
                    vista.mostrarMensaje("\n==================================");
                    vista.mostrarMensaje("¡LA PARTIDA HA COMENZADO!");
                    vista.mostrarMensaje("==================================");
                    evaluarEstadoTurno();
                    break;

                case TURNO_CAMBIADO:
                    evaluarEstadoTurno();
                    break;

                case FIN_DE_JUEGO:
                    vista.mostrarMensaje("\n==================================");
                    vista.mostrarMensaje("¡EL JUEGO HA TERMINADO!");
                    if (partida.getJugadorEnTurno() != null) {
                        vista.mostrarPerdedor(partida.getJugadorEnTurno());
                    }
                    vista.mostrarMensaje("==================================");
                    break;
            }
        } else {
            // Actualización por defecto si no se pasa un enum específico
            vista.mostrarJugadores(partida.getJugadores(), this.yo);
        }
    }

    /**
     * Verifica si el jugador local es quien debe realizar una jugada.
     */
    private void evaluarEstadoTurno() throws RemoteException {
        // Muestra las cartas de 'yo' visibles y las de los contrincantes como [X]
        vista.mostrarJugadores(partida.getJugadores(), this.yo);

        Jugador jugadorEnTurno = partida.getJugadorEnTurno();

        if (jugadorEnTurno == null) {
            return;
        }

        if (this.yo != null && this.yo.getNombre().equalsIgnoreCase(jugadorEnTurno.getNombre())) {
            vista.mostrarMensaje("\n----------------------------------");
            vista.mostrarMensaje(">>> ¡ES TU TURNO! <<<");
            vista.mostrarMensaje("----------------------------------");
            procesarTurnoLocal();
        } else {
            vista.mostrarMensaje("\nTurno actual de: " + jugadorEnTurno.getNombre() + ". Esperando...");
        }
    }
}