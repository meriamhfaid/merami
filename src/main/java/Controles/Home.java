package Controles;

import com.sun.javafx.scene.ParentHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;import javafx.scene.Scene;




public class Home extends Application {
    public static final String CURRENCY = "TND";



    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Interface.fxml"));
        try {
            Parent  root = loader.load();
            Scene scene =new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
