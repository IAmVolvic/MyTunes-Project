package BLL.FIle;
import APP_SETTINGS.AppConfig;

import BE.Playlist;
import DAL.Logic.MyPlaylistController;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import java.io.IOException;
import java.nio.file.*;

import javafx.scene.Node;

public class FileController {
    private final MyPlaylistController myPlaylist;
    private final String playlistPath = AppConfig.getPlaylistPath();


    public FileController(MyPlaylistController mPlayList) {
        myPlaylist = mPlayList;
        createPlaylistPath();
        playlistPathCleanup();
    }


    public void createSong(String songPath, String songName, int playListId, String playListName) {
        copyTo(songPath, playlistPath + playListId + "_" + playListName + "/", songName);
    }


    public void createPlaylistPathSingle(String folderName, int playlistID) {
        createPath(playlistPath + playlistID + "_" + folderName);
    }


    public void createPlaylistIcon(String icon, String playlistName, int playlistId){
        copyTo(icon, playlistPath + playlistId + "_" + playlistName + "/", "icon");
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

    public void deleteItem(String path) {
        deletePath(new File(path));
    }

    private void copyTo(String filePath, String toPath, String newFileName) {
        Path from = Paths.get(filePath);
        Path to = Paths.get(toPath + from.getFileName());

        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };

        try {
            Files.copy(from, to.resolveSibling(newFileName + "." + getFileExtension( from.getFileName().toString() ) ), options);
        } catch (IOException e) {}
    }

    public void renameTo(String path, String newName, boolean isFolder) {
        Path from = Paths.get(path);
        String fullPath;

        if(!isFolder){
            fullPath = newName + "." + getFileExtension(path);
        }else{
            fullPath = newName;
        }

        try {
            Files.move(from, from.resolveSibling(fullPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {}
    };

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            // No file extension or the dot is at the end of the filename
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }


    private void createPlaylistPath(){
        int numberOfPlaylists = myPlaylist.getAllPlaylists().size();

        for(Playlist playlist : myPlaylist.getAllPlaylists()){
            if (numberOfPlaylists > 0){
                createPath(playlistPath + playlist.playlistId() + "_" + playlist.playlistName());
            }
        }
    }


    private void playlistPathCleanup() {
        File[] allContents = new File(playlistPath).listFiles();

        if (allContents != null) {
            for (File file : allContents) {
                String originalString = file.getName();
                int startIndex = originalString.indexOf("_") + 1;

                if (checkIfPlaylistExists(originalString.substring(startIndex))) {
                    deletePath(file);
                }
            }
        }
    }


    private boolean checkIfPlaylistExists(String check) {
        for (Playlist playlist : myPlaylist.getAllPlaylists()) {
            if (playlist.playlistName().equals(check)) {
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


    public File promptFilerChooser(ActionEvent event, String filters){
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        String fileTitle;
        FileChooser.ExtensionFilter fileFilters = null;

        switch (filters) {
            case "music_edit":
                fileTitle = "Edit Song";
                fileFilters = new FileChooser.ExtensionFilter("AUDIO FILES", "*.mp3", "*.wav", "*.ogg");
                break;

            case "music_add":
                fileTitle = "Add Song";
                fileFilters = new FileChooser.ExtensionFilter("AUDIO FILES", "*.mp3", "*.wav", "*.ogg");
                break;

            case "playlist_edit", "playlist_add":
                fileTitle = "Playlist Icon";
                fileFilters = new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.jpeg", "*.png", "*.gif");
                break;

            default:
                return null;
        }

        fileChooser.setTitle(fileTitle);
        fileChooser.getExtensionFilters().addAll(fileFilters);

        return fileChooser.showOpenDialog(stage);
    }
}
