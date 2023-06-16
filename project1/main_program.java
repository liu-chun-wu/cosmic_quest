package project1;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class main_program extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //
        try {
            configuration.initalize();
            Parent root = FXMLLoader.load(getClass().getResource("start_scene.fxml"));
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println(e);
        }
        //
        stage.setOnCloseRequest(event -> LeaveGame(stage));
        //
        stage.setTitle("Cosmic Quest");
        //
        stage.show();
    }

    public void LeaveGame(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Leave Game");
        alert.setHeaderText("You are about to leave game!");
        alert.setContentText("Do you really want to leave game?!");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
            System.exit(0);
        }
    }
}
