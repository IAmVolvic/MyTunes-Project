package DLL;

import BE.Playlist;
import BE.Song;
import DAL.Logic.MyPlaylistController;
import DAL.Logic.MySongsController;
import DLL.Media.MediaController;
import DLL.FIle.FileController;
import DLL.Media.MediaPlayerObservable;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.ArrayList;

public class DllController {
    private final String currentSong = "ThxSoMchHate.mp3";



    // Controllers
    MyPlaylistController myPlaylist = new MyPlaylistController();
    MySongsController mySongs = new MySongsController();
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


    public File callFileChooser(ActionEvent event, String filters){
        return fileController.promptFilerChooser(event, filters);
    }

    public File getFile(String path, String filter) {
        return fileController.findFile(path, filter);
    }



    public ArrayList<Playlist> getPlaylists() {
        return myPlaylist.getAllPlaylists();
    }

    public Playlist createPlaylist(String iconPath, String playlistTitle){
        //Add the Data to the DB
        Playlist newPlaylist = myPlaylist.createPlaylist(playlistTitle);

        //Create the Folder
        fileController.createPlaylistPathSingle(playlistTitle, newPlaylist.playlistId());

        //Add the image to the folder
        fileController.createPlaylistIcon(iconPath, playlistTitle, newPlaylist.playlistId());

        return newPlaylist;
    }



    public ArrayList<Song> getSongs(int playlistId) {
        return mySongs.getPlaylistSongs(playlistId);
    }

    public void bindProgressObserver(MediaPlayerObservable observable) {
        this.mediaController.bindProgressListener(observable);
    }
}
