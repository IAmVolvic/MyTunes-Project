package DLL.Components;

import BE.Playlist;
import DAL.Logic.MyPlaylistController;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

import javafx.scene.Node;

public class FileController {
    private final MyPlaylistController myPlaylist;
    private final String playlistPath = "resources/Playlists/";

    File musicFolder = new File("resources/music");
    File[] listOfFiles = musicFolder.listFiles();



    public FileController(MyPlaylistController mPlayList) {
        myPlaylist = mPlayList;
        createPlaylistPath();
        playlistPathCleanup();
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


    public void createPlaylistPathSingle(String folderName) {
        createPath(playlistPath + folderName);
    }

    public void createPlaylistIcon(String icon, String playlistName){
        copyTo(icon, playlistPath + playlistName + "/");
    }


    public File findFile(String directoryPath, String filter) {
        File[] allContents = Paths.get(directoryPath).toFile().listFiles();

        if (allContents != null) {
            for (File file : allContents) {
                if(file.getName().startsWith(filter)){
                    return file;
                }
            }
        }
        return null;
    }


    private void copyTo(String filePath, String toPath) {
        Path from = Paths.get(filePath);
        Path to = Paths.get(toPath + from.getFileName());

        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };

        try {
            Files.copy(from, to, options);
            Files.move(
                to,
                to.resolveSibling("icon." + getFileExtension( from.getFileName().toString() ) ),
                StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            // No file extension or the dot is at the end of the filename
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }


    private boolean isValid() {
        if (listOfFiles == null) {
            System.out.println("No files found in the specified directory: " + musicFolder.getAbsolutePath());
            return false;
        }
        return true;
    }


    private void createPlaylistPath(){
        int numberOfPlaylists = myPlaylist.getAllPlaylists().size();

        for(Playlist playlist : myPlaylist.getAllPlaylists()){
            if (numberOfPlaylists > 0){
                createPath(playlistPath + playlist.PlaylistName());
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


    private void createPath(String path){
        File theDir = new File(path);

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


    public File promptFilerChooser(ActionEvent event){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Playlist Icon");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );

        File file = fileChooser.showOpenDialog(stage);

        return file;
    }
}
