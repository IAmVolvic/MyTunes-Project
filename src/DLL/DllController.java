package DLL;

import BE.Playlist;
import DAL.Logic.MyPlaylistController;
import DLL.Components.MediaController;
import DLL.Components.FileController;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.ArrayList;

public class DllController {
    private final String currentSong = "ThxSoMchHate.mp3";

    // Controllers
    MyPlaylistController myPlaylist = new MyPlaylistController();
    FileController fileController = new FileController(myPlaylist);
    MediaController mediaController = new MediaController();



    public DllController(){}


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


    public ArrayList<Playlist> getPlaylists() {
        return myPlaylist.getAllPlaylists();
    }

    public File callFileChooser(ActionEvent event){
        return fileController.promptFilerChooser(event);
    }


    public boolean createPlaylist(String iconPath, String playlistTitle){
        //Add the Data to the DB
        myPlaylist.createPlaylist(playlistTitle);

        //Create the Folder
        fileController.createPlaylistPathSingle(playlistTitle);

        //Add the image to the folder
        fileController.createPlaylistIcon(iconPath, playlistTitle);


        return true;
    }
}
