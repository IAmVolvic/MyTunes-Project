package DLL;

import DLL.Components.MediaController;
import DLL.Components.FileController;

import java.io.File;


public class DllController {
    private final String currentSong = "Test.mp3";

    // Controllers
    FileController fileController = new FileController();
    MediaController mediaController = new MediaController();

    public String getSongName(String songName){
        return fileController.getSong(songName).getName();
    }

    public void PlaySong(){
        mediaController.playSong(fileController.getSong(currentSong));
    }
}
