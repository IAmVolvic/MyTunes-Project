package DLL;

import APP_SETTINGS.AppConfig;
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
    public void initializeSongLabel(Label songLabel){ mediaController.setLabel(songLabel); }

    public void playSong(){
        mediaController.playSong();
    }

    public void pauseSong(){
        mediaController.pauseSong();
    }

    public void skipSong() { mediaController.skipSong(); }

    public void prevSong() {
        mediaController.prevSong();
    }

    public void setVolume(Double newVolume){
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

    public void deletePlaylist(int playlistId){
        mediaController.stopEverything();

        // Get playlist data
        Optional<Playlist> data = playLists.stream()
                .filter(playlist -> playlist.playlistId() == playlistId)
                .findFirst();


        // Delete playlist
        myPlaylist.deletePlaylist(playlistId);
        // Delete playlist songs
        mySongs.deleteSongFromPlaylistAll(playlistId);

        // Delete Folder
        fileController.deleteItem(AppConfig.getPlaylistPath() + playlistId + "_" + data.get().playlistName());

        // Update cache
        data.ifPresent(playLists::remove);
    }


    //SONGS
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

        songsTable.ifPresent(song -> song.addSong(newSong));

        // Store / rename the audio file
        fileController.createSong(songPath, baseEncoded, playListId, playListName);

        return songsTable.get().getSongTable();
    }


    public void setPlaylistSongs(Playlist playlist){ mediaController.setPlaylist(playlist); }


    public List<Song> deleteSong(int playListId, int songId) {
        mediaController.stopEverything();

        // Get playlist data
        Optional<Playlist> playlistData = playLists.stream()
                .filter(playlist -> playlist.playlistId() == playListId)
                .findFirst();

        List<Song> updatedSongTable = new ArrayList<>();

        playlistData.ifPresent(playlist -> {
            Optional<Song> songToRemove = playlist.getSongTable().stream()
                    .filter(song -> song.getId() == songId)
                    .findFirst();

            songToRemove.ifPresent(song -> {
                String dirPath = AppConfig.getPlaylistPath() + playListId + "_" + playlistData.get().playlistName();
                String baseEncoded = Base64.getEncoder().encodeToString(song.getName().getBytes());
                File getFile = fileController.findFile(dirPath, baseEncoded);

                // Delete File
                fileController.deleteItem(getFile.getPath());

                // Remove the song from the SongTable
                playlist.getSongTable().remove(song);

                updatedSongTable.addAll(playlist.getSongTable());
            });

            // Delete the data from DB
            mySongs.deleteSongFromPlaylistSingle(songId);


            mediaController.setPlaylist(playlist);
        });

        return updatedSongTable;
    }



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
