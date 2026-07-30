package ar.edu.unlu.poo.juego.gui;

import ar.edu.unlu.poo.juego.modelo.Carta;
import ar.edu.unlu.poo.juego.modelo.Valor;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CartaGUI extends StackPane {
    private final Carta carta;
    private boolean seleccionada;
    private boolean bloqueada;
    private Runnable accionAlHacerClick;

    /**
     * @param carta  la carta que representa
     * @param oculta true para mostrarla "boca abajo" (cartas del contrincante),
     *               false para mostrar su valor (tus propias cartas)
     */
    public CartaGUI(Carta carta, boolean oculta) {

        this.carta = carta;

        Rectangle fondo = new Rectangle(30, 70);
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setStroke(Color.BLACK);

        if (oculta) {
            fondo.setFill(Color.DARKSLATEGRAY);
            getChildren().add(fondo);
        } else {
            fondo.setFill(Color.WHITE);
            Label texto = new Label(textoValor(carta));
            getChildren().addAll(fondo, texto);
        }

        setOnMouseClicked(e -> {
            if (!bloqueada && accionAlHacerClick != null) {
                accionAlHacerClick.run();
            }
        });
    }

    private String textoValor(Carta carta) {
        if (carta.getValor() == Valor.JOKER) {
            return "J";
        }
        return String.valueOf(carta.getValor().getNumero());
    }

    public void setAccionAlHacerClick(Runnable accion) {
        this.accionAlHacerClick = accion;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
        setOpacity(bloqueada ? 0.6 : 1.0);
        setCursor(bloqueada ? Cursor.DEFAULT : Cursor.HAND);
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public Carta getCarta() {
        return carta;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void seleccionar() {
        if (!seleccionada) {
            seleccionada = true;
            setTranslateY(-15);
            setStyle("-fx-border-color: blue; -fx-border-width: 2;");
        }
    }

    public void deseleccionar() {
        if (seleccionada) {
            seleccionada = false;
            setTranslateY(0);
            setStyle("");
        }
    }
}
