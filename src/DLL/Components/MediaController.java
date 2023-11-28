package DLL.Components;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public class MediaController {
    public Media media;
    public MediaPlayer mPlayer;

    public Double currentVolume = 10.0;

    private Boolean fileLoaded = false;


    public MediaController(){}

    public void playSong(File Song){
        System.out.println(Song.getName());
        if(!fileLoaded) {
            fileLoaded = true;
            media = new Media(Song.toURI().toString());
            mPlayer = new MediaPlayer(media);
        }

        mPlayer.play();
    }

    public void pauseSong(){
        mPlayer.pause();
    }

    public MediaPlayer getCurrentSong() {
        return mPlayer;
    }

    public void setVolume(MediaPlayer currentSong, Double newVolume){
        System.out.println("Running");
        currentSong.setVolume(newVolume);
    }
}
