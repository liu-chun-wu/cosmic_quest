package project1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.effect.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;

public class start_scene_controller implements Initializable {
	@FXML
	public Slider OffsetSlider, FallingDurationSlider, VolumeSlider;
	@FXML
	public Text OffsetDisplay, DurationDisplay, VolumeDisplay;
	@FXML
	public AnchorPane StartPane, SettingPane;
	@FXML
	public Button StartButton;

	Glow glow = new Glow(1.0);
	public static SceneList scenelist = new SceneList();

	public void CallSetting(ActionEvent event) {
		//
		VolumeSlider.setValue(configuration.MusicVolume);
		//
		VolumeDisplay.setText(String.format("%,.1f", configuration.MusicVolume));
		//
		if (SettingPane.isVisible()) {
			SettingPane.setVisible(false);
		} else {
			SettingPane.setVisible(true);
		}
		//
		if (StartButton.isVisible()) {
			StartButton.setVisible(false);
		} else {
			StartButton.setVisible(true);
		}
	}

	public void SwitchToPlayScene(ActionEvent event) throws Exception {
		scenelist.SwitchToPlayScene(event);
	}

	//
	public void LeaveGame(ActionEvent event) {
		Stage stage = (Stage) StartPane.getScene().getWindow();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Leave Game");
		alert.setHeaderText("You are about to leave game!");
		alert.setContentText("Do you really want to leave game?!");
		if (alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
			System.exit(0);
		}
	}

	public void initialize(URL url, ResourceBundle resourceBundle) {
		//
		SettingPane.setVisible(false);
		//
		OffsetSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				OffsetDisplay.setText(String.format("%,.0f", OffsetSlider.getValue()));
				configuration.offset = (int) OffsetSlider.getValue();
			}

		});
		//
		FallingDurationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				DurationDisplay.setText(String.format("%,.0f", FallingDurationSlider.getValue()));
				configuration.NodeDuration = (int) FallingDurationSlider.getValue();
			}
		});
		//
		VolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				configuration.MusicVolume = VolumeSlider.getValue();
				try {
					music_list.mediaPlayer.setVolume(configuration.MusicVolume / 100);
				} catch (Exception e) {

				}
				VolumeDisplay.setText(String.format("%,.1f", configuration.MusicVolume));
			}
		});
	}
}
