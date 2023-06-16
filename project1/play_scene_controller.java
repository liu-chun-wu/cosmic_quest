package project1;

import project1.node.Cue;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.util.Duration;

import java.io.FileNotFoundException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Glow;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class play_scene_controller implements Initializable {
    @FXML
    public AnchorPane GamePane, SettingPane;
    @FXML
    public ImageView SwitchToEndSceneImage;
    @FXML
    public Path path1, path2, path3, path4, path1_end, path2_end, path3_end, path4_end;
    @FXML
    public Label ScoreDisplay;
    @FXML
    public Circle CenterCircle;
    @FXML
    public ProgressBar ProgressBar;
    @FXML
    public Button SwitchToEndSceneButton;
    @FXML
    public Slider VolumeSlider;
    @FXML
    public Text VolumeDisplay;

    public static Timer StartMusic_Progressbar;
    public static TimerTask StartMusic_Progressbar_Task;

    public static int UpIndex, DownIndex, LeftIndex, RightIndex = 0;

    public static boolean MusicHasStarted = false;
    public static boolean CanPressReset = false;
    public static boolean IsPaused = true;

    public static node_controller nl = new node_controller();
    public static SceneList scenelist = new SceneList();

    public void SwitchToEndScene(ActionEvent event) throws Exception {
        //
        node_controller.SetCurrentNodeTransparent();
        //
        node_controller.ResetNodeList();
        //
        StartMusic_Progressbar.cancel();
        //
        scenelist.SwitchToEndScene(event);
    }

    public void SwitchToStartScene(ActionEvent event) throws Exception {
        //
        configuration.ResetScore();
        //
        VolumeSlider.setValue(configuration.MusicVolume);
        //
        VolumeDisplay.setText(String.format("%,.1f", configuration.MusicVolume));
        //
        node_controller.SetCurrentNodeTransparent();
        //
        node_controller.ResetNodeList();
        //
        StartMusic_Progressbar.cancel();
        //
        scenelist.SwitchToStartScene(event);
    }

    public void LeaveGame(ActionEvent event) {
        //
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Leave Game");
        alert.setHeaderText("You are about to leave game!");
        alert.setContentText("Do you really want to leave game?!");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) GamePane.getScene().getWindow();
            stage.close();
            System.exit(0);
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
        SwitchToEndSceneButton.setVisible(false);
        SwitchToEndSceneImage.setVisible(false);
        SettingPane.setVisible(false);
        //
        ScoreDisplay.setText("0");
        //
        MusicHasStarted = false;
        CanPressReset = false;
        IsPaused = true;
        //
        timer.reset();
        //
        music_list.reset();
        //
        ProgressBar.setStyle("-fx-accent: green");
        //
        VolumeSlider.setValue(configuration.MusicVolume);
        //
        VolumeDisplay.setText(String.format("%,.1f", configuration.MusicVolume));
        //
        try {
            nl.LoadNode("Start.txt", GamePane, path1, path2, path3, path4);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        //
        try {
            music_list.LoadMusic();
        } catch (MalformedURLException e) {
            System.out.println(e);
        }
        //
        VolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                configuration.MusicVolume = VolumeSlider.getValue();
                music_list.mediaPlayer.setVolume(configuration.MusicVolume / 100);
                VolumeDisplay.setText(String.format("%,.1f", configuration.MusicVolume));
            }
        });
        //
        Glow glow = new Glow(0.8);
        Timeline tl2 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 3.0)),
                new KeyFrame(Duration.millis(500), new KeyValue(glow.levelProperty(), 0.0)));
        tl2.setCycleCount(Animation.INDEFINITE);
        SwitchToEndSceneImage.setEffect(glow);
        tl2.play();
        //
        StartMusic_Progressbar = new Timer();
        StartMusic_Progressbar_Task = new TimerTask() {
            public void run() {
                ProgressBar.setProgress(music_list.get_progress());
                //
                if (MusicHasStarted == false) {
                    if (timer.time >= configuration.offset) {
                        try {
                            music_list.StartAndStop();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        MusicHasStarted = true;
                    }
                }
                //
                if (music_list.get_progress() == 1.0) {
                    configuration.MaxComboCounter = Math.max(configuration.ComboCounter,
                            configuration.MaxComboCounter);
                    SwitchToEndSceneButton.setVisible(true);
                    SwitchToEndSceneImage.setVisible(true);
                }
            }
        };
        //
        StartMusic_Progressbar.scheduleAtFixedRate(StartMusic_Progressbar_Task, 1, 1);
        //
        CenterCircle.setFocusTraversable(true);
        //
        CenterCircle.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                //
                case W:
                    ProcessNode(node_controller.UpNodeList, UpIndex);
                    ScoreDisplay.setText(Integer.toString(configuration.score));
                    break;
                //
                case S:
                    ProcessNode(node_controller.DownNodeList, DownIndex);
                    ScoreDisplay.setText(Integer.toString(configuration.score));
                    break;
                //
                case A:
                    ProcessNode(node_controller.LeftNodeList, LeftIndex);
                    ScoreDisplay.setText(Integer.toString(configuration.score));
                    break;
                //
                case D:
                    ProcessNode(node_controller.RightNodeList, RightIndex);
                    ScoreDisplay.setText(Integer.toString(configuration.score));
                    break;
                //
                case SPACE:
                    if (music_list.get_progress() != 1.0) {
                        if (CanPressReset == false) {
                            CanPressReset = true;
                        }
                        //
                        timer.StartAndStop();
                        //
                        node_controller.StartAndStop();
                        //
                        if (MusicHasStarted == true) {
                            try {
                                music_list.StartAndStop();
                            } catch (MalformedURLException e1) {
                                System.out.println(e1);
                            }
                        }
                        //
                        if (IsPaused == false) {
                            SettingPane.setVisible(true);
                            node_controller.SetCurrentNodeTransparent();
                            IsPaused = true;
                        } else {
                            SettingPane.setVisible(false);
                            node_controller.RestoreNodeColor();
                            IsPaused = false;
                        }
                    }
                    break;
                //
                case R:
                    if (CanPressReset == true) {
                        //
                        CanPressReset = false;
                        //
                        SwitchToEndSceneButton.setVisible(false);
                        SwitchToEndSceneImage.setVisible(false);
                        //
                        timer.StartAndStop();
                        //
                        node_controller.StartAndStop();
                        //
                        if (MusicHasStarted == true) {
                            try {
                                music_list.StartAndStop();
                            } catch (MalformedURLException e1) {
                                System.out.println(e1);
                            }
                        }
                        //
                        ScoreDisplay.setText("0");
                        //
                        MusicHasStarted = false;
                        IsPaused = true;
                        //
                        configuration.ResetScore();
                        //
                        timer.reset();
                        //
                        music_list.reset();
                        //
                        node_controller.SetCurrentNodeTransparent();
                        //
                        node_controller.ResetNodeList();
                        //
                        try {
                            nl.LoadNode("Start.txt", GamePane, path1, path2, path3, path4);
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        //
                        SettingPane.setVisible(false);
                    }
                    break;
                default:
                    break;
            }
        });
    }

    public void FromStopToStart() {
        if (CanPressReset == false) {
            CanPressReset = true;
        }
        //
        timer.StartAndStop();
        //
        node_controller.StartAndStop();
        //
        if (MusicHasStarted == true) {
            try {
                music_list.StartAndStop();
            } catch (MalformedURLException e1) {
                System.out.println(e1);
            }
        }
        //
        if (IsPaused == false) {
            SettingPane.setVisible(true);
            node_controller.SetCurrentNodeTransparent();
            IsPaused = true;
        } else {
            SettingPane.setVisible(false);
            node_controller.RestoreNodeColor();
            IsPaused = false;
        }
    }

    public static void ProcessNode(ArrayList<node> arraylist, int index) {
        if (arraylist.get(index).GetTiming() != Cue.IGNORE) {
            CalculateScore(arraylist.get(index).GetTiming());
            arraylist.get(index).SetTransparent();
        }
    }

    public static void CalculateScore(Cue status) {
        switch (status) {
            case PERFECT:
                configuration.score += 100;
                configuration.PerfectCounter++;
                configuration.ComboCounter++;
                break;
            case GREAT:
                configuration.score += 60;
                configuration.GreatCounter++;
                configuration.ComboCounter++;
                break;
            case BAD:
                configuration.score += 20;
                configuration.BadCounter++;
                configuration.ComboCounter++;
                break;
            case MISS:
                configuration.MissCounter++;
                configuration.MaxComboCounter = Math.max(configuration.ComboCounter,
                        configuration.MaxComboCounter);
                configuration.ComboCounter = 0;
                break;
            default:
                break;
        }
    }
}
