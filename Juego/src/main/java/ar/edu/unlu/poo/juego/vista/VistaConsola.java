package ar.edu.unlu.poo.juego.vista;

import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);


    public int obtenerOpcion(){
        return sc.nextInt();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarMenu() {
        System.out.println("\n-MENU");
        System.out.println("[1]JUGAR");
        System.out.println("[2]SALIR");
        System.out.print("Opcion: ");
    }
}
