package ar.edu.unlu.poo.juego.rmi;


import ar.edu.unlu.poo.juego.controlador.ControladorConsola;
import ar.edu.unlu.poo.juego.vista.VistaConsola;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class AppClienteConsola {
    public static void main(String[] args) throws RemoteException {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchará peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchará peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        VistaConsola vista = new VistaConsola();
        ControladorConsola controlador = new ControladorConsola(vista);
        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        //vista.iniciar();
        try {
            c.iniciar(controlador);
            controlador.menu();
        } catch (RemoteException | RMIMVCException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
