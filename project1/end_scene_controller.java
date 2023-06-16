package project1;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.util.Duration;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.shape.*;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class end_scene_controller implements Initializable {
	@FXML
	public Path perfect_path, great_path, bad_path, miss_path, max_combo_path, score_path;
	@FXML
	public Label end_score_display;
	@FXML
	public ImageView perfect, great, bad, miss, max_combo;
	@FXML
	public Text miss_count, bad_count, great_count, max_combo_count, perfect_count;
	@FXML
	public AnchorPane end_scene_pane;
	public static SceneList scenelist = new SceneList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		PathTransition perfect_pt = new PathTransition(Duration.millis(400), perfect_path, perfect);
		PathTransition great_pt = new PathTransition(Duration.millis(400), great_path, great);
		PathTransition bad_pt = new PathTransition(Duration.millis(400), bad_path, bad);
		PathTransition miss_pt = new PathTransition(Duration.millis(400), miss_path, miss);
		PathTransition max_combo_pt = new PathTransition(Duration.millis(400), max_combo_path, max_combo);
		PathTransition score_pt = new PathTransition(Duration.millis(800), score_path, end_score_display);

		end_score_display.setText(Integer.toString(configuration.score));
		miss_count.setText(Integer.toString(configuration.MissCounter));
		bad_count.setText(Integer.toString(configuration.BadCounter));
		great_count.setText(Integer.toString(configuration.GreatCounter));
		max_combo_count.setText(Integer.toString(configuration.MaxComboCounter));
		perfect_count.setText(Integer.toString(configuration.PerfectCounter));

		GaussianBlur blur = new GaussianBlur(20);
		Glow glow = new Glow(0.8);
		miss_count.setEffect(blur);
		bad_count.setEffect(blur);
		great_count.setEffect(blur);
		perfect_count.setEffect(blur);
		max_combo_count.setEffect(blur);
		miss_count.setEffect(glow);
		bad_count.setEffect(glow);
		great_count.setEffect(glow);
		perfect_count.setEffect(glow);
		max_combo_count.setEffect(glow);

		Timeline tl = new Timeline(
				new KeyFrame(Duration.ZERO, event -> {
					perfect.setY(700);
					great.setY(700);
					bad.setY(700);
					max_combo.setY(700);
					miss.setY(700);
				}),
				new KeyFrame(Duration.millis(1), event -> {
					score_pt.play();
				}, new KeyValue(end_score_display.scaleXProperty(), 1),
						new KeyValue(end_score_display.scaleYProperty(), 1)),
				new KeyFrame(Duration.millis(700), event -> {
					perfect_pt.play();
				}, new KeyValue(end_score_display.scaleXProperty(), 4),
						new KeyValue(end_score_display.scaleYProperty(), 4)),
				new KeyFrame(Duration.millis(1000), event -> {
					great_pt.play();
				}, new KeyValue(
						miss_count.opacityProperty(), 0),
						new KeyValue(
								bad_count.opacityProperty(), 0),
						new KeyValue(
								great_count.opacityProperty(), 0),
						new KeyValue(
								perfect_count.opacityProperty(), 0),
						new KeyValue(
								max_combo_count.opacityProperty(), 0)),
				new KeyFrame(Duration.millis(1300), event -> {
					bad_pt.play();
				}, new KeyValue(
						blur.radiusProperty(), 20)),
				new KeyFrame(Duration.millis(1600), event -> {
					max_combo_pt.play();
				}),
				new KeyFrame(Duration.millis(1900), event -> {
					miss_pt.play();
				}, new KeyValue(
						miss_count.opacityProperty(), 1),
						new KeyValue(
								bad_count.opacityProperty(), 1),
						new KeyValue(
								great_count.opacityProperty(), 1),
						new KeyValue(
								perfect_count.opacityProperty(), 1),
						new KeyValue(
								max_combo_count.opacityProperty(), 1),
						new KeyValue(
								blur.radiusProperty(), 0)));
		Timeline tl2 = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 3.0)),
				new KeyFrame(Duration.millis(500), new KeyValue(glow.levelProperty(), 0.0)));
		tl2.setAutoReverse(true);
		tl2.setCycleCount(Animation.INDEFINITE);
		tl.setOnFinished(event -> {
			tl2.play();
		});
		tl.play();
	}

	public void LeaveGame(ActionEvent event) {
		Stage stage = (Stage) end_scene_pane.getScene().getWindow();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Leave Game");
		alert.setHeaderText("You are about to leave game!");
		alert.setContentText("Do you really want to leave game?!");
		if (alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
			System.exit(0);
		}
	}

	public void SwitchToStartScene(ActionEvent event) throws Exception {
		configuration.ResetScore();
		scenelist.SwitchToStartScene(event);
	}
}
