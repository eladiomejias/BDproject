package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.awt.event.ActionEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Biblioteca - Principal");
        //root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        // Add icon
        javafx.scene.image.Image icon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root, 520, 470));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
