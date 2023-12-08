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
import java.util.Optional;

public class DllController {
    //Single
    private ArrayList<Playlist> playLists;


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



    public ArrayList<Playlist> getPlaylistsINT() {
        ArrayList<Playlist> dbPlaylists = myPlaylist.getAllPlaylists();

        for(Playlist val : dbPlaylists){
            ArrayList<Song> dbSongsFromPlaylist = mySongs.getPlaylistSongs(val.playlistId());
            val.addSongTable(dbSongsFromPlaylist);
        }

        playLists = dbPlaylists;

        return dbPlaylists;
    }

    public ArrayList<Playlist> getPlaylistsSingle() {
        return playLists;
    }



    public Playlist createPlaylist(String iconPath, String playlistTitle){
        //Add the Data to the DB
        Playlist newPlaylist = myPlaylist.createPlaylist(playlistTitle);
        playLists.add(newPlaylist);

        //Create the Folder
        fileController.createPlaylistPathSingle(playlistTitle, newPlaylist.playlistId());

        //Add the image to the folder
        fileController.createPlaylistIcon(iconPath, playlistTitle, newPlaylist.playlistId());

        return newPlaylist;
    }


    public ArrayList<Song> createSong(int playListId, String songPath, String songTitle){
        Song newSong = mySongs.newSong(playListId, songTitle);
        Optional<Playlist> songsTable = playLists.stream()
                .filter(playlist -> playlist.playlistId() == playListId)
                .findFirst();
        songsTable.get().addSong(newSong);


        return songsTable.get().getSongTable();
    }


    public ArrayList<Song> getSongs(int getPlaylistWithId) {
        Optional<Playlist> songsTable = playLists.stream()
                .filter(playlist -> playlist.playlistId() == getPlaylistWithId)
                .findFirst();

        return songsTable.get().getSongTable();
    }

    public void bindProgressObserver(MediaPlayerObservable observable) {
        this.mediaController.bindProgressListener(observable);
    }
}
