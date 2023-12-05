package GUI;


import APP_SETTINGS.AppConfig;
import BE.Playlist;
import DLL.DllController;
import GUI.Components.FXMLCustom.ModalNewPlaylist;
import GUI.Components.FXMLCustom.PlaylistButton;
import GUI.Components.Modal.ModalController;
import GUI.Components.PlayButton;
import GUI.Components.SongList;
import GUI.Components.VolumeControl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.jfoenix.controls.JFXSlider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;


public class MainController {
    // Playlist list nodes
    public VBox playlist_list;
    public Pane playlistViewIcon;
    public Label playlistViewTitle;


    // Volume slider
    public JFXSlider volume;

    // Getting the table columns
    public TableColumn<String, Integer> col1;
    public TableColumn<String, String>  col2;
    public TableColumn<String, Button>  col3;
    public TableColumn<String, String>  col4;

    //Icons
    public FontAwesomeIconView iPlay;


    //Modal Parts
    public StackPane modal_main;


    //Frontend Controllers
    private PlayButton playBTNController;
    private VolumeControl volumeController;
    private ModalController modalController;
    private final PlaylistController playlistController = new PlaylistController();

    // Backend Controllers
    private final DllController dllController = new DllController();


    //Constructor
    public MainController() {
        // Run later when everything is being created
        Platform.runLater(this::postInitialize);
    }


    //New Playlist Button
    public void newPlaylist(ActionEvent actionEvent) {
        ModalNewPlaylist modalPlaylistView = new ModalNewPlaylist(dllController, modalController, playlistController, playlist_list);
        modalController.openModal(modalPlaylistView.getNewPlaylistModal());
    }


    public void addSong(ActionEvent actionEvent) {}


    //Play Button
    public void playBtn(ActionEvent actionEvent) {
        //Send Button Logic to its own container
        playBTNController.playButtonClicked(actionEvent);
        volumeController.initialize();
    }


    //Close Modal Button /s
    public void closeModal(ActionEvent mouseEvent) {
        modalController.closeModal();
    }


    // Post Initialize
    private void postInitialize() {
        //Set Playlist Controller
        playlistController.setNodes(
                dllController,
                playlist_list,
                playlistViewIcon,
                playlistViewTitle
        );


        //Set PlayButton Controller
        playBTNController = new PlayButton(iPlay, dllController);


        // Get and start the songs table / initialize it
        SongList tableController = new SongList(col1, col2, col3, col4);
        tableController.initialize();


        // Get and start the volume controller / initialize it
        volumeController = new VolumeControl(volume, dllController);
        volumeController.initialize();


        // Get and start the modal controller
        modalController = new ModalController(modal_main);
        buildPlaylistButtons();
    }


    private void buildPlaylistButtons(){
        int index = 0;
        ArrayList<Playlist> playlist = dllController.getPlaylists();

        for(Playlist val : playlist){
            File icon = dllController.getFile(
                    AppConfig.getPlaylistPath() + val.playlistId() + "_" + val.playlistName(),
                    "icon"
            );

            PlaylistButton playlistButton = new PlaylistButton(playlistController);
            playlistButton.setId(val.playlistId());
            playlistButton.setTitle(val.playlistName());

            if(icon != null){
                playlistButton.setIcon(icon);
            }

            if(index < 1){
                playlistController.setPlaylistView(playlistButton);
            }

            playlist_list.getChildren().add(playlistButton.getButton());
            index++;
        }
    }
}