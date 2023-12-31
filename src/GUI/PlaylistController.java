package GUI;

import APP_SETTINGS.AppConfig;
import BE.Playlist;
import BE.Song;
import GUI.Components.PlaylistButton;
import GUI.Components.MediaButtons;
import GUI.Components.SongList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * This class represents a controller for managing playlists in a media player application.
 *
 * Methods:
 * - PlaylistController(GUISingleton newSingle)
 * - setNodes(MediaButtons mb, SongList tC, VBox pl, Pane viewIcon, Label viewTitle, Label viewTotalSongsLabel, Pane cpi, Label cpl)
 * - resetFullView(Button toDelete)
 * - setPlaylistView(PlaylistButton plBtn)
 * - updateFullView()
 * - deleteSong(List<Song> newList)
 * - editSong()
 * - setMediaPlaylist()
 * - updateViewSongList(String searchFilter)
 * - getPlaylistId()
 * - getPlaylistName()
 * - updateTotalSongsNum(int Number)
 * - changeButtonStyles()
 * - changeViewStyles()
 * - setViewSongList(List<Song> songTable, boolean runMedia)
 * - resetMediaButtons()
 * - getPlaylistData(int playlistId)
 * - getSelected()
 * - getDetails(int id)
 */
public class PlaylistController {
    // GUI Singleton
    private final GUISingleton single;

    private PlaylistButton selectedPlaylistButton;
    private Playlist selectedPlaylistData;

    // BLL Controller
    private MediaButtons mediaButtons;
    private SongList tableController;

    // Extra
    private VBox playlist_list;
    private Pane playlist_viewIcon;
    private Label playlist_viewTitle;
    private Label playlist_viewTotalSongs;
    private Pane playlist_currentlyPlayingIcon;
    private Label playlist_currentlyPlayingTitle;


    public PlaylistController(GUISingleton newSingle) {
        single = newSingle;
    }

    public void setNodes(MediaButtons mb, SongList tC, VBox pl, Pane viewIcon, Label viewTitle, Label viewTotalSongsLabel, Pane cpi, Label cpl) {
        mediaButtons = mb;
        tableController = tC;

        playlist_list = pl;
        playlist_viewIcon = viewIcon;
        playlist_viewTitle = viewTitle;
        playlist_viewTotalSongs = viewTotalSongsLabel;

        playlist_currentlyPlayingIcon = cpi;
        playlist_currentlyPlayingTitle = cpl;
    }

    public void resetFullView(Button toDelete) {
        playlist_list.getChildren().remove(toDelete);

        tableController.clearTable();

        selectedPlaylistButton = null;
        selectedPlaylistData = null;

        resetMediaButtons();

        playlist_currentlyPlayingIcon.setStyle("-fx-background-image: url('images/My.png');");
        playlist_viewIcon.setStyle("-fx-background-image: url('images/My.png');");

        playlist_currentlyPlayingTitle.setText(" ");
        playlist_viewTitle.setText(" ");
        playlist_viewTotalSongs.setText("Playlist - 0 songs");
    }

    public void setPlaylistView(PlaylistButton plBtn) {
        Playlist data = getDetails(plBtn.getId());

        if (selectedPlaylistData != data) {
            selectedPlaylistButton = plBtn;
            selectedPlaylistData = data;

            changeButtonStyles();
            changeViewStyles(true);
            assert data != null;
            setViewSongList(data.getSongTable(), true);
        }
    }

    public void updateFullView(boolean resetMedia) {
        changeButtonStyles();
        changeViewStyles(resetMedia);
    }

    public void deleteSong(List<Song> newList) {
        resetMediaButtons();
        updateTotalSongsNum(selectedPlaylistData.getSongTable().size());

        setViewSongList(newList, false);
    }

    public void editSong() {
        resetMediaButtons();
        updateTotalSongsNum(selectedPlaylistData.getSongTable().size());
        tableController.clearTable();
        updateViewSongList(null);
    }

    public void setMediaPlaylist() {
        resetMediaButtons();
        single.getBllController().setPlaylistSongs(selectedPlaylistData);
        updateTotalSongsNum(selectedPlaylistData.getSongTable().size());
    }

    public void updateViewSongList(String searchFilter) {
        if (selectedPlaylistData == null) {
            return;
        }
        int index = 1;
        List<Song> songs = single.getBllController().getSongs(selectedPlaylistData.playlistId(), searchFilter);
        ObservableList<Song> songData = FXCollections.observableArrayList();

        for (Song val : songs) {
            val.setTableId(index);
            songData.add(val);
            index++;
        }

        tableController.addSong(songData);
    }

    public int getPlaylistId() {
        if (selectedPlaylistData == null) {
            return 0;
        }
        return selectedPlaylistData.playlistId();
    }

    public String getPlaylistName() {
        return selectedPlaylistData.playlistName();
    }

    public void updateTotalSongsNum(int Number) {
        String newTitle = AppConfig.getPlaylistTotalSongs(Number);

        selectedPlaylistButton.setNumOfSongs(newTitle);
        playlist_viewTotalSongs.setText(newTitle);
    }

    public Playlist getPlaylistData(int playlistId) {
        return getDetails(playlistId);
    }

    public Playlist getSelected() {
        return this.selectedPlaylistData;
    }

    private void changeButtonStyles() {
        ObservableList<Node> children = playlist_list.getChildren();
        for (Node val : children) {
            val.getStyleClass().remove("playlist-active");
        }
        selectedPlaylistButton.setActiveStyle();
    }

    private void changeViewStyles(boolean resetMedia) {
        if (resetMedia) {
            resetMediaButtons();
        }


        File icon = single.getBllController().getFile(
                AppConfig.getPlaylistPath() + selectedPlaylistData.playlistId() + "_" + selectedPlaylistData.playlistName(),
                "icon"
        );

        if (icon != null) {
            playlist_currentlyPlayingIcon.setStyle("-fx-background-image: url('" + icon.toURI() + "'); ");
            playlist_viewIcon.setStyle("-fx-background-image: url('" + icon.toURI() + "'); ");
        } else {
            playlist_currentlyPlayingIcon.setStyle("-fx-background-image: url('images/My.png');");
            playlist_viewIcon.setStyle("-fx-background-image: url('images/My.png');");
        }

        playlist_currentlyPlayingTitle.setText(selectedPlaylistData.playlistName());
        playlist_viewTitle.setText(selectedPlaylistData.playlistName());
        updateTotalSongsNum(single.getBllController().getSongs(selectedPlaylistData.playlistId(), null).size());
    }

    private void setViewSongList(List<Song> songTable, boolean runMedia) {
        int index = 1;
        ObservableList<Song> songData = FXCollections.observableArrayList();

        songTable.sort(Comparator.comparingInt(Song::getTableId));
        for (Song val : songTable) {
            val.setTableId(index);
            songData.add(val);
            index++;
        }

        tableController.addSong(songData);
        if (runMedia) {
            setMediaPlaylist();
        }
    }

    private void resetMediaButtons() {
        mediaButtons.resetIcon();
    }

    private Playlist getDetails(int id) {
        ArrayList<Playlist> playlistTable = single.getBllController().getPlaylistsSingle();

        for (Playlist val : playlistTable) {
            if (val.playlistId() == id) {
                return val;
            }
        }
        return null;
    }
}