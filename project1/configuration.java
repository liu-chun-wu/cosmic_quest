package project1;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class configuration {
    public static int offset = -200;
    public static int NodeDuration = 2000;
    public static double MusicVolume = 50;

    public static int score = 0;
    public static int PerfectCounter = 0;
    public static int GreatCounter = 0;
    public static int BadCounter = 0;
    public static int MissCounter = 0;
    public static int MaxComboCounter = 0;
    public static int ComboCounter = 0;

    public static Text ErrorMessage = new Text();

    public static VBox SettingBox = new VBox();

    public static void ResetScore() {
        score = 0;
        PerfectCounter = 0;
        GreatCounter = 0;
        BadCounter = 0;
        MissCounter = 0;
        MaxComboCounter = 0;
        ComboCounter = 0;
        play_scene_controller.UpIndex = 0;
        play_scene_controller.DownIndex = 0;
        play_scene_controller.LeftIndex = 0;
        play_scene_controller.RightIndex = 0;
    }

    public static void initalize() {
        Label OffsetDisplay = new Label("offset (音樂時間補足)");
        Label NodeDurationDisplay = new Label("node speed (音符落下速度))");
        Label VolumeDisplay = new Label("volume (歌曲音量)");
        Slider OffsetSlider = new Slider(-300.0, 300.0, 1.0);
        Slider NodeDurationSlider = new Slider(500.0, 2500.0, 1.0);
        Slider VolumeSlider = new Slider(0, 1, 0.1);

        OffsetSlider.setShowTickMarks(true);
        OffsetSlider.setShowTickLabels(true);
        OffsetSlider.setValue(offset);
        OffsetSlider.valueProperty().addListener(
                new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                            Number newValue) {
                        offset = newValue.intValue();
                    }
                });

        NodeDurationSlider.setShowTickMarks(true);
        NodeDurationSlider.setShowTickLabels(true);
        NodeDurationSlider.setValue(NodeDuration);
        NodeDurationSlider.valueProperty().addListener(
                new ChangeListener<Number>() {

                    public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                            Number newValue) {
                        NodeDuration = newValue.intValue();
                    }
                });
        SettingBox.getChildren().addAll(OffsetDisplay, OffsetSlider, NodeDurationDisplay, NodeDurationSlider,
                VolumeDisplay, VolumeSlider);
        SettingBox.setSpacing(10);
        SettingBox.setPrefSize(500, 500);
        SettingBox.setLayoutY(150);
    }

    public static Pane get_SettingBox() {
        return SettingBox;
    }
}
