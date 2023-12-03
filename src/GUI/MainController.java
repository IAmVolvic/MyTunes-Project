package GUI;


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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.jfoenix.controls.JFXSlider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;


public class MainController {
    // Playlist list container
    public VBox playlist_list;

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

    // Backend Controllers
    private final DllController dllController = new DllController();


    //Constructor
    public MainController() {
        // Run later when everything is being created
        Platform.runLater(this::postInitialize);
    }


    //New Playlist Button
    public void newPlaylist(ActionEvent actionEvent) {
        ModalNewPlaylist modalPlaylistView = new ModalNewPlaylist(dllController, playlist_list);
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
        createButtonTEST();
    }

    private void createButtonTEST(){
        int index = 0;
        ArrayList<Playlist> playlist = dllController.getPlaylists();

        for(Playlist val : playlist){
            File icon = dllController.getFile("resources/Playlists/"+val.PlaylistName(), "icon");

            PlaylistButton playlistButton = new PlaylistButton();
            playlistButton.setTitle(val.PlaylistName());

            if(icon != null){
                playlistButton.setIcon(icon);
            }

            if(index < 1){
                playlistButton.toggleActive();
            }

            playlist_list.getChildren().add(playlistButton.getButton());
            index++;
        }
    }

    public void createPlaylist(){
        System.out.println("btn clicked");
    }
}