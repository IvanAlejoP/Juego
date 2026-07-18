module ar.edu.unlu.poo.juego {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens ar.edu.unlu.poo.juego to javafx.fxml;
    exports ar.edu.unlu.poo.juego;
}