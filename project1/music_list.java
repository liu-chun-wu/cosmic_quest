package project1;

import javafx.util.Duration;
import java.io.File;
import java.net.MalformedURLException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class music_list {
    public static File file;
    public static Media media;
    public static MediaPlayer mediaPlayer;
    public static MediaView mediaView;
    public static boolean MusicIsPlaying = false;

    public static void LoadMusic() throws MalformedURLException {
        if (mediaPlayer == null) {
            file = new File("project1/music/" + "Start.mp3");
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        }
    }

    public static void StartAndStop() throws MalformedURLException {
        if (mediaPlayer != null) {
            if (MusicIsPlaying == false) {
                mediaPlayer.play();
                MusicIsPlaying = true;
            } else {
                mediaPlayer.pause();
                MusicIsPlaying = false;
            }
        }
    }

    public static Double get_progress() {
        if (timer.time >= configuration.offset && mediaPlayer != null) {
            return mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis();
        }
        return 0.0;
    }

    public static void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.pause();
            MusicIsPlaying = false;
        }
    }
}
