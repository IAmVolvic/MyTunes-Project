package DLL.Components;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public class MediaController {
    public MediaPlayer mPlayer;

    public MediaController(){}

    public void playSong(File Song){
        Media media = new Media(getClass().getResource("/music/Test.mp3").toString());
        mPlayer  = new MediaPlayer(media);
        mPlayer.play();
    }
}
