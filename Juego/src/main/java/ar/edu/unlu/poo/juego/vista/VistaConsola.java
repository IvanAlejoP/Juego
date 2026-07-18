package ar.edu.unlu.poo.juego.vista;

import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);


    public int obtenerOpcion(){
        try {

        }
        return sc.nextInt();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
