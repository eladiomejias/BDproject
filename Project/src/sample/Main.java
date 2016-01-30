package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(new Scene(root, 353, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
