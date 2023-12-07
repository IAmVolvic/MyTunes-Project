package DLL.Media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaController {
    public Media media;
    public MediaPlayer mPlayer;
    private Boolean fileLoaded = false;

    private double lastVolume = 0.01;

    private final MediaPlayerSubject mPlayerSubject;

    public MediaController() {
        this.mPlayerSubject = new MediaPlayerSubject();
    }

    public void playSong(File Song) {
        System.out.println(Song.getName());

        if (!fileLoaded) {
            fileLoaded = true;
            media = new Media(Song.toURI().toString());
            mPlayer = new MediaPlayer(media);
        }

        mPlayer.setVolume(lastVolume);
        mPlayer.play();

        mPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            this.mPlayerSubject.update(newValue, mPlayer.getTotalDuration());
        });
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


    public void bindProgressListener(MediaPlayerObservable listener) {
        this.mPlayerSubject.registerObserver(listener);
    }
}
