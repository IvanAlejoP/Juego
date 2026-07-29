package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.modelo.Carta;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CartaGUI extends StackPane {
    private final Carta carta;
    private boolean seleccionada;

    public CartaGUI(Carta carta) {

        this.carta = carta;

        Rectangle fondo = new Rectangle(10, 20);
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setFill(Color.WHITE);
        fondo.setStroke(Color.BLACK);

        Label texto = new Label(carta.getValor().toString());

        getChildren().addAll(fondo, texto);

        setOnMouseClicked(e -> alternarSeleccion());
    }

    private void alternarSeleccion() {

        seleccionada = !seleccionada;

        if (seleccionada) {
            setTranslateY(-15);
            setStyle("-fx-border-color: blue; -fx-border-width:2;");
        } else {
            setTranslateY(0);
            setStyle("");
        }
    }

    public Carta getCarta() {
        return carta;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void seleccionar() {
        if (!seleccionada) {
            alternarSeleccion();
        }
    }

    public void deseleccionar() {
        if (seleccionada) {
            alternarSeleccion();
        }
    }
}
