package DLL;

import BE.Playlist;
import DAL.Logic.MyPlaylistController;
import DLL.Components.MediaController;
import DLL.Components.FileController;
import java.io.File;
import java.util.ArrayList;

public class DllController {
    private final String currentSong = "ThxSoMchHate1.mp3";

    // Controllers
    FileController fileController = new FileController();
    MediaController mediaController = new MediaController();
    MyPlaylistController myPlaylist = new MyPlaylistController();
    public DllController(){
        createPlaylistPath();
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

    public ArrayList<Playlist> getPlaylists() {
        return myPlaylist.getAllPlaylists();
    }


    private void createPlaylistPath(){
        int numberOfPlaylists = myPlaylist.getAllPlaylists().size();
        String playlistPath = "resources/Playlists/";

        for(Playlist playlist : myPlaylist.getAllPlaylists()){
            File theDir = new File(playlistPath + playlist.PlaylistName());

            System.out.println(numberOfPlaylists);
            if (numberOfPlaylists > 0){
                if (theDir.mkdirs()) {
                    System.out.println("Folder created successfully.");
                }
            }
        }
    }

}
