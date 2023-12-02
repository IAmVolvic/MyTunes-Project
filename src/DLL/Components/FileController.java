package DLL.Components;

import BE.Playlist;
import DAL.Logic.MyPlaylistController;

import java.io.File;
import java.util.ArrayList;

public class FileController {
    private MyPlaylistController myPlaylist;
    private String playlistPath = "resources/Playlists/";

    File musicFolder = new File("resources/music");
    File[] listOfFiles = musicFolder.listFiles();

    public FileController(MyPlaylistController mPlayList) {
        myPlaylist = mPlayList;
        createPlaylistPath();
        playlistPathCleanup();
    }


    private boolean isValid() {
        if (listOfFiles == null) {
            System.out.println("No files found in the specified directory: " + musicFolder.getAbsolutePath());
            return false;
        }
        return true;
    }


    public File getSong(String songName) {
        isValid();

        for (File file : listOfFiles) {
            if (file.getName().equals(songName)) {
                return file;
            }
        }

        System.out.println("Song not found");
        return null;
    }


    private void createPlaylistPath(){
        int numberOfPlaylists = myPlaylist.getAllPlaylists().size();

        for(Playlist playlist : myPlaylist.getAllPlaylists()){
            if (numberOfPlaylists > 0){
                createPath(playlist.PlaylistName());
            }
        }
    }


    private void playlistPathCleanup() {
        File[] allContents = new File(playlistPath).listFiles();

        if (allContents != null) {
            for (File file : allContents) {
                if (checkIfPlaylistExists(file.getName())) {
                    deletePath(file);
                }
            }
        }
    }


    private boolean checkIfPlaylistExists(String check) {
        for (Playlist playlist : myPlaylist.getAllPlaylists()) {
            if (playlist.PlaylistName().equals(check)) {
                return false;
            }
        }
        return true;
    }



    private void createPath(String fileName){
        File theDir = new File(playlistPath + fileName);

        if (theDir.mkdirs()) {
            System.out.println("Folder created successfully.");
        }
    }


    private boolean deletePath(File path) {
        File[] allContents = path.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deletePath(file);
            }
        }
        return path.delete();
    }
}
