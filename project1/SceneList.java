package project1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SceneList {

    public void SwitchToStartScene(ActionEvent event) throws Exception {
        configuration.ResetScore();
        Parent root = FXMLLoader.load(getClass().getResource("start_scene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToPlayScene(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("play_scene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchToEndScene(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("end_scene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
