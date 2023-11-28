package DLL.Components;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaController {
    public Media media;
    public MediaPlayer mPlayer;
    private Boolean fileLoaded = false;

    private double vol = 0.0;


    public void playSong(File Song) {
        System.out.println(Song.getName());

        if (!fileLoaded) {
            fileLoaded = true;
            media = new Media(Song.toURI().toString());
            mPlayer = new MediaPlayer(media);
        }

        System.out.println(vol);

        mPlayer.play();
    }

    public void pauseSong() {
        mPlayer.pause();
    }

    public void setVolume(Double newVolume) {
        mPlayer.setVolume(newVolume);
    }

}
