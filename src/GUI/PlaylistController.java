package GUI;

import APP_SETTINGS.AppConfig;
import BE.Playlist;
import BE.Song;
import DLL.DllController;
import GUI.Components.FXMLCustom.PlaylistButton;
import GUI.Components.SongList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlaylistController {
    private PlaylistButton selectedPlaylistButton;
    private Playlist selectedPlaylistData;


    //Dll Controller
    private DllController dllController;
    private SongList tableController;

    //Extra
    private VBox playlist_list;
    private Pane playlist_viewIcon;
    private Label playlist_viewTitle;
    private Label playlist_viewTotalSongs;

    public void setNodes(DllController dc, SongList tC, VBox pl, Pane viewIcon, Label viewTitle, Label viewTotalSongsLabel){
        dllController = dc;
        tableController = tC;

        playlist_list = pl;
        playlist_viewIcon = viewIcon;
        playlist_viewTitle = viewTitle;
        playlist_viewTotalSongs = viewTotalSongsLabel;
    }

    public void setPlaylistView(PlaylistButton plBtn) {
        Playlist data = getDetails(plBtn.getId());

        if(selectedPlaylistData != data){
            selectedPlaylistButton = plBtn;
            selectedPlaylistData = data;

            changeButtonStyles();
            changeViewStyles();
            setViewSongList();
        }
    }


    public void updateViewSongList(String searchFilter) {
        int index = 1;
        List<Song> songs = dllController.getSongs(selectedPlaylistData.playlistId(), searchFilter);
        ObservableList<Song> songData = FXCollections.observableArrayList();

        for(Song val : songs){
            val.setTableId(index);
            songData.add(val);
            index++;
        }

        tableController.addSong(songData);
    }


    public int getPlaylistId(){
        return selectedPlaylistData.playlistId();
    }
    public String getPlaylistName(){
        return selectedPlaylistData.playlistName();
    }

    public void updateTotalSongsNum(int Number) {
        String newTitle = AppConfig.getPlaylistTotalSongs(Number);

        selectedPlaylistButton.setNumOfSongs(newTitle);
        playlist_viewTotalSongs.setText(newTitle);
    }



    private void changeButtonStyles() {
        ObservableList<Node> children = playlist_list.getChildren();
        for (Node val : children) {
            val.getStyleClass().remove("playlist-active");
        }
        selectedPlaylistButton.setActiveStyle();
    }

    private void changeViewStyles() {
        File icon = dllController.getFile(
                AppConfig.getPlaylistPath() + selectedPlaylistData.playlistId() + "_" + selectedPlaylistData.playlistName(),
                "icon"
        );

        if(icon != null){
            playlist_viewIcon.setStyle("-fx-background-image: url('" + icon.toURI() + "'); ");
        }else{
            playlist_viewIcon.setStyle("-fx-background-image: url('images/My.png');");
        }

        playlist_viewTitle.setText(selectedPlaylistData.playlistName());
        updateTotalSongsNum(dllController.getSongs(selectedPlaylistData.playlistId(), null).size());
    }


    private void setViewSongList() {
        int index = 1;
        List<Song> songs = dllController.getSongs(selectedPlaylistData.playlistId(), null);
        ObservableList<Song> songData = FXCollections.observableArrayList();

        songs.sort(Comparator.comparingInt(Song::getTableId));
        for(Song val : songs){
            val.setTableId(index);
            songData.add(val);
            index++;
        }

        tableController.addSong(songData);
    }


    private Playlist getDetails(int id) {
        ArrayList<Playlist> playlistTable = dllController.getPlaylistsSingle();

        for(Playlist val : playlistTable){
            if(val.playlistId() == id){
                return val;
            }
        }
        return null;
    }
}
