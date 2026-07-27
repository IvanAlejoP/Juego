package ar.edu.unlu.poo.juego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppFX extends Application{
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("SceneMenu.fxml"));
        Scene sceneMenu = new Scene(root);
        stage.setScene(sceneMenu);
        stage.show();

        /*FXMLLoader fxmlLoader = new FXMLLoader(AppFX.class.getResource("SceneMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/
    }

    public static void main(String[] args) {
        Application.launch(AppFX.class, args);
    }
}