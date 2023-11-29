package DLL.Components;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaController {
    public Media media;
    public MediaPlayer mPlayer;
    private Boolean fileLoaded = false;

    private double lastVolume = 0.1;


    public void playSong(File Song) {
        System.out.println(Song.getName());

        if (!fileLoaded) {
            fileLoaded = true;
            media = new Media(Song.toURI().toString());
            mPlayer = new MediaPlayer(media);
        }

        mPlayer.setVolume(lastVolume);
        mPlayer.play();
    }

    public void pauseSong() {
        mPlayer.pause();
    }

    public void setVolume(Double newVolume) {
        if(fileLoaded){
            mPlayer.setVolume(newVolume);
        }
        lastVolume = newVolume;
    }

}
