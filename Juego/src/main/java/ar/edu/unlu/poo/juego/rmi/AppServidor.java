package ar.edu.unlu.poo.juego.rmi;

import ar.edu.unlu.poo.juego.modelo.Partida;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AppServidor {
    public static void main(String[] args) throws RemoteException {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchar� peticiones el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchar� peticiones el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        Partida modelo = new Partida();
        Servidor servidor = new Servidor(ip, Integer.parseInt(port));
        try {
            // Algunas implementaciones de rmimvc requieren que el modelo sea
            // lo primero que se registre.
            servidor.iniciar(modelo);
            System.out.println("Servidor esperando conexiones...");
        } catch (RemoteException | RMIMVCException e) {
            e.printStackTrace();
        }
    }
}
