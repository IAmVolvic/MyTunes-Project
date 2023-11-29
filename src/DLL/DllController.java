package DLL;

import DLL.Components.MediaController;
import DLL.Components.FileController;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class DllController {
    private final String currentSong = "ThxSoMchHate1.mp3";

    // Controllers
    FileController fileController = new FileController();
    MediaController mediaController = new MediaController();

    public String getSongName(String songName){
        return fileController.getSong(songName).getName();
    }

    public void PlaySong(){
        if(fileController.getSong(currentSong) != null){
            mediaController.playSong(fileController.getSong(currentSong));
        }
    }

    public void PauseSong(){
        mediaController.pauseSong();
    }


    public void SetVolume(Double newVolume){
        mediaController.setVolume(newVolume);
    }
}
