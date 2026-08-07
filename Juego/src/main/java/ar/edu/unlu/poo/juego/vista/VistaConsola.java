package ar.edu.unlu.poo.juego.vista;

import ar.edu.unlu.poo.juego.modelo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class VistaConsola {
    private final Scanner sc = new Scanner(System.in);

    public int obtenerOpcion(){
        return sc.nextInt();
    }

    public String obtenerString(){
        return sc.nextLine();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarMensajeSinSalto(String mensaje){
        System.out.print(mensaje);
    }

    public void mostrarMenu() {
        System.out.println("\n-MENU");
        System.out.println("[1]JUGAR");
        System.out.println("[2]SALIR");
        System.out.print("Opcion: ");
    }

    public void mostrarOpcionesDeJuego() {
        System.out.println("\n-OPCIONES DE JUEGO");
        System.out.println("[1]TOMAR CARTA");
        System.out.println("[2]MEZCLAR MANO");
        System.out.print("Opcion: ");
    }

    /**
     * Muestra a los jugadores del juego.
     * Solo revela el contenido de las cartas del jugador local ('yo').
     * Para los contrincantes muestra la cantidad de cartas pero con valores ocultos '[X]'.
     */
    public void mostrarJugadores(ArrayList<Jugador> jugadores, Jugador yo) {
        System.out.println();
        for (Jugador j : jugadores) {
            if (j.jugadorTermino()) {
                System.out.printf("[%s] TERMINÓ...\n", j.getNombre());
            } else {
                System.out.printf("[%s] (Cartas: %d): ", j.getNombre(), j.getTamanioMano());

                // Si el jugador de la lista es MI JUGADOR (yo), le muestro las cartas visibles
                if (yo != null && j.getNombre().equalsIgnoreCase(yo.getNombre())) {
                    for (Carta c : j.getMano().getMano()) {
                        if (c.getValor() == Valor.JOKER) {
                            System.out.print("[J]");
                        } else {
                            System.out.printf("[%d]", c.getValor().getNumero());
                        }
                    }
                } else {
                    // Para cualquier otro jugador (humano contrincante o IA), ocultamos las cartas
                    for (int i = 0; i < j.getTamanioMano(); i++) {
                        System.out.print("[X]");
                    }
                }
                System.out.println();
            }
        }
    }

    public void mostrarPerdedor(Jugador jugadorEnTurno) {
        System.out.printf("\n[%s] PERDIÓ...\n", jugadorEnTurno.getNombre());
    }
}