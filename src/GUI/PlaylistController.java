package GUI;

import APP_SETTINGS.AppConfig;
import BE.Playlist;
import DLL.DllController;
import GUI.Components.FXMLCustom.PlaylistButton;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class PlaylistController {
    private PlaylistButton selectedPlaylistButton;
    private Playlist selectedPlaylistData;


    //Dll Controller
    private DllController dllController;

    //Extra
    private VBox playlist_list;
    private Pane playlist_viewIcon;
    private Label playlist_viewTitle;

    public void setNodes(DllController dc, VBox pl, Pane viewIcon, Label viewTitle){
        dllController = dc;

        playlist_list = pl;
        playlist_viewIcon = viewIcon;
        playlist_viewTitle = viewTitle;
    }

    public void setPlaylistView(PlaylistButton plBtn) {
        Playlist data = getDetails(plBtn.getId());

        if(selectedPlaylistData != data){
            selectedPlaylistButton = plBtn;
            selectedPlaylistData = data;

            changeButtonStyles();
            changeViewStyles();
        }
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
        }

        playlist_viewTitle.setText(selectedPlaylistData.playlistName());
    }


    private Playlist getDetails(int id) {
        ArrayList<Playlist> playlistTable = dllController.getPlaylists();

        for(Playlist val : playlistTable){
            if(val.playlistId() == id){
                return val;
            }
        }
        return null;
    }
}
