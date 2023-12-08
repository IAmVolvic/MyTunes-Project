package DLL;

import BE.Playlist;
import BE.Song;
import DAL.Logic.MyPlaylistController;
import DAL.Logic.MySongsController;
import DLL.Media.MediaController;
import DLL.FIle.FileController;
import DLL.Media.MediaPlayerObservable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DllController {
    //Single
    private ArrayList<Playlist> playLists;


    // Controllers
    MyPlaylistController myPlaylist = new MyPlaylistController();
    MySongsController mySongs = new MySongsController();
    FileController fileController = new FileController(myPlaylist);
    MediaController mediaController = new MediaController(fileController);


    // CONSTRUCTOR
    public DllController(){}



    // MEDIA CONTROLS
    public void inzSongLabel(Label songLabel){ mediaController.setLabel(songLabel); }
    public void PlaySong(){
        mediaController.playSong();
    }

    public void PauseSong(){
        mediaController.pauseSong();
    }

    public void skipSong() { mediaController.skipSong(); }

    public void prevSong() {
        mediaController.prevSong();
    }

    public void SetVolume(Double newVolume){
        mediaController.setVolume(newVolume);
    }



    //PLAYLIST
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



    //SONGS
    public void setCurrentSongLabel(Label songLabel){}
    public List<Song> getSongs(int getPlaylistWithId, String searchFilter) {
        return playLists.stream()
                .filter(playlist -> playlist.playlistId() == getPlaylistWithId)
                .findFirst()
                .map(Playlist::getSongTable)
                .orElse(new ArrayList<>())
                .stream()
                .filter(song -> searchFilter == null || isSimilar(song.getName().toLowerCase(), searchFilter.toLowerCase()))
                .collect(Collectors.toList());
    }

    public ArrayList<Song> createSong(int playListId, String playListName, String songPath, String songTitle){
        //New File Name
        String baseEncoded = Base64.getEncoder().encodeToString(songTitle.getBytes());

        // Store the new song in the DB, and update the cache
        Song newSong = mySongs.newSong(playListId, songTitle);
        Optional<Playlist> songsTable = playLists.stream()
                .filter(playlist -> playlist.playlistId() == playListId)
                .findFirst();
        songsTable.get().addSong(newSong);


        // Store / rename the audio file
        fileController.createSong(songPath, baseEncoded, playListId, playListName);

        return songsTable.get().getSongTable();
    }

    public void setPlaylistSongs(Playlist playlist){ mediaController.setPlaylist(playlist); }


    //OTHER
    public File callFileChooser(ActionEvent event, String filters){
        return fileController.promptFilerChooser(event, filters);
    }

    public File getFile(String path, String filter) {
        return fileController.findFile(path, filter);
    }

    public void bindProgressObserver(MediaPlayerObservable observable) {
        this.mediaController.bindProgressListener(observable);
    }

    private boolean isSimilar(String str1, String str2) {
        // Adjust this condition based on your similarity criteria
        return str1.contains(str2);
    }
}
