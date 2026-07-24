package ar.edu.unlu.poo.juego.vista;

import ar.edu.unlu.poo.juego.modelo.*;

import java.util.ArrayList;
import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);

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

    public void mostrarJugadores(ArrayList<Jugador> jugadores){
        System.out.println();
        for(Jugador j : jugadores){
            if(j instanceof JugadorHumano){
                if(j.jugadorTermino()){
                    System.out.printf("[%s]TERMINO...", j.getNombre());
                }
                else{
                    System.out.printf("[%s][%d]: ", j.getNombre(),j.getTamanioMano());
                    for(Carta c : j.getMano().getMano()){
                        if(c.getValor() == Valor.JOKER){
                            System.out.print("[J]");
                        }
                        else{
                            System.out.printf("[%d]", c.getValor().getNumero());
                        }
                    }
                }
                System.out.println();
            }
            else if(j instanceof JugadorIA){
                if(j.jugadorTermino()){
                    System.out.printf("[%s]TERMINO...", j.getNombre());
                }
                else{
                    System.out.printf("[%s][%d]: ", j.getNombre(),j.getTamanioMano());
                    for(Carta c : j.getMano().getMano()){
                        System.out.print("[X]");
                    }
                }
                System.out.println();
            }
        }
    }

    public void mostrarPerdedor(Jugador jugadorEnTurno) {
        System.out.printf("\n[%s]PERDIO...\n", jugadorEnTurno.getNombre());
    }
}
