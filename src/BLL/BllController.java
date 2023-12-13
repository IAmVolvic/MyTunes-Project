package BLL;

import APP_SETTINGS.AppConfig;
import BE.Playlist;
import BE.Song;
import DAL.Logic.MyPlaylistController;
import DAL.Logic.MySongsController;
import BLL.Media.MediaController;
import BLL.FIle.FileController;
import BLL.Media.MediaPlayerObservable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BllController {
    //Single
    private ArrayList<Playlist> playLists;


    // Controllers
    MyPlaylistController myPlaylist = new MyPlaylistController();
    MySongsController mySongs = new MySongsController();
    FileController fileController = new FileController(myPlaylist);
    MediaController mediaController = new MediaController(fileController);


    // CONSTRUCTOR
    public BllController(){}



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
            for(Song dbSongVal : dbSongsFromPlaylist){

                String dirPath = AppConfig.getPlaylistPath() + val.playlistId() + "_" + val.playlistName();
                String baseEncoded = Base64.getEncoder().encodeToString(dbSongVal.getName().getBytes());
                File getFile = fileController.findFile(dirPath, dbSongVal.getId() + "_" + baseEncoded);

                if(getFile != null) {
                    dbSongVal.setDuration(mediaController.getMediaDuration(getFile));
                }
            }

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



    public void editPlaylist(int playlistId, String newName, String newIcon) {
        // Playlist Data
        Optional<Playlist> playlistData = playLists.stream()
                .filter(playlist -> playlist.playlistId() == playlistId)
                .findFirst();

        //Verbs
        String playlistPath = AppConfig.getPlaylistPath() + playlistId + "_" + playlistData.get().playlistName();

        // Update DB
        myPlaylist.updatePlaylist(playlistId, newName);

        // Update Icon if set
        if(newIcon != null){
            File getFile = fileController.findFile(playlistPath, "icon");

            if(getFile != null){
                //This just deletes the old one, doing this allows me to not have left over icons because of the file type
                fileController.deleteItem(getFile.getPath());
            }

            fileController.createPlaylistIcon(newIcon, playlistData.get().playlistName(), playlistId);
        }

        // Update File
        fileController.renameTo(
                playlistPath,
                playlistId + "_" + newName,
                true
        );

        // Update cache
        playlistData.get().newName(newName);
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

        newSong.setDuration(mediaController.getMediaDuration( new File(songPath)));

        Optional<Playlist> songsTable = playLists.stream()
                .filter(playlist -> playlist.playlistId() == playListId)
                .findFirst();

        songsTable.ifPresent(song -> song.addSong(newSong));

        // Store / rename the audio file
        fileController.createSong(songPath, newSong.getId() + "_" + baseEncoded, playListId, playListName);

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
                File getFile = fileController.findFile(dirPath, songId + "_" + baseEncoded);

                // Delete File
                if(getFile != null) {
                    fileController.deleteItem(getFile.getPath());
                }

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

    public void editSong(int playlistId, int songId, String newSong, String newSongName) {
        mediaController.stopEverything();

        // Get playlist data
        Optional<Playlist> playlistData = playLists.stream()
                .filter(playlist -> playlist.playlistId() == playlistId)
                .findFirst();

        Optional<Song> songData = playlistData.get().getSongTable().stream()
                .filter(songs -> songs.getId() == songId)
                .findFirst();

        //Verbs
        String playlistPath = AppConfig.getPlaylistPath() + playlistId + "_" + playlistData.get().playlistName();
        String oldSongNameBase = Base64.getEncoder().encodeToString(songData.get().getName().getBytes());
        File getFile = fileController.findFile(playlistPath, songId + "_" + oldSongNameBase);

        songData.ifPresent(songFromData -> {
            // Edit the DB
            mySongs.editSong(songId, newSongName);

            if(getFile != null) {
                // Edit the file
                if(newSong != null){
                    // Delete the old file
                    fileController.deleteItem(getFile.getPath());

                    // Create the new song
                    String newSongNameBase = Base64.getEncoder().encodeToString(newSongName.getBytes());
                    fileController.createSong(newSong, songId + "_" + newSongNameBase, playlistId, playlistData.get().playlistName());
                }else{
                    File getOldFile = fileController.findFile(playlistPath, songId + "_" + oldSongNameBase);

                    fileController.renameTo(
                            getOldFile.getPath(),
                            songId + "_" + Base64.getEncoder().encodeToString(newSongName.getBytes()),
                            false
                    );
                }
            }

            songFromData.setName(newSongName);

            playlistData.ifPresent(playlist -> {
                mediaController.setPlaylist(playlist);
            });
        });
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
