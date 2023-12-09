package GUI;


import APP_SETTINGS.AppConfig;
import BE.Playlist;
import DLL.Media.MediaPlayerObservable;
import DLL.DllController;
import GUI.Components.FXMLCustom.PlaylistButton;
import GUI.Components.Modal.ModalConfigs.AddSongModalView;
import GUI.Components.Modal.ModalConfigs.NewPlaylistModalView;
import GUI.Components.Modal.ModalController;
import GUI.Components.MediaButtons;
import GUI.Components.SongList;
import GUI.Components.VolumeControl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.jfoenix.controls.JFXSlider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.File;
import java.util.ArrayList;


public class MainController {
    // Playlist list nodes
    public VBox playlist_list;
    public Pane playlistViewIcon;
    public Label playlistViewTitle;
    public Label viewTotalSongs;

    //Currently Playing
    public Pane playlistIconPlaying;
    public Label playlistNamePlaying;
    public Label songLabel;


    // Volume slider
    public ProgressBar songProgressBar;
    public Text songProgressNum;
    public Text songProgressNumTotal;
    public JFXSlider volume;


    // Getting the table columns
    public TableView songList;
    public TableColumn<String, Integer> col1;
    public TableColumn<String, String>  col2;
    public TableColumn<String, String>  col3;
    public TableColumn<String, Long>    col4;
    public TableColumn<String, Button>  col5;



    //Icons
    public FontAwesomeIconView iPlay;


    //Modal Parts
    public StackPane modal_main;


    //Search
    public TextField searchInput;
    private final Timeline timeline = new Timeline();


    //Frontend Controllers
    private MediaButtons mediaButtons;
    private VolumeControl volumeController;
    private ModalController modalController;
    private final PlaylistController playlistController = new PlaylistController();
    private SongList tableController;

    // Backend Controllers
    private final DllController dllController = new DllController();
    private MediaPlayerObservable mediaPlayerObservable;


    //Constructor
    public MainController() {
        // Run later when everything is being created
        Platform.runLater(this::postInitialize);
    }


    //New Playlist Button
    public void newPlaylist(ActionEvent o) {
        NewPlaylistModalView modalView = new NewPlaylistModalView(dllController, modalController, playlistController, playlist_list);
        modalController.openModal(modalView.getView());
    }


    public void newSong(ActionEvent actionEvent) {
        AddSongModalView modalView = new AddSongModalView(dllController, modalController, tableController, playlistController);
        modalController.openModal(modalView.getView());
    }


    //Media Buttons
    public void playBtn(ActionEvent actionEvent) {
        //Send Button Logic to its own container
        mediaButtons.playButtonClicked(actionEvent);
    }

    public void skipSong(ActionEvent actionEvent) {
        mediaButtons.skipSong(actionEvent);
    }

    public void previousSong(ActionEvent actionEvent) {
        mediaButtons.prevSong(actionEvent);
    }


    //Close Modal Button /s
    public void closeModal(ActionEvent mouseEvent) {
        modalController.closeModal();
    }


    // Post Initialize
    private void postInitialize() {
        dllController.inzSongLabel(songLabel);


        // Get and start the songs table / initialize it
        tableController = new SongList(songList, col1, col2, col3, col4, col5);

        //Set PlayButton Controller
        mediaButtons = new MediaButtons(iPlay, dllController);


        //Set Playlist Controller
        playlistController.setNodes(
                mediaButtons,
                dllController,
                tableController,
                playlist_list,
                playlistViewIcon,
                playlistViewTitle,
                viewTotalSongs,
                playlistIconPlaying,
                playlistNamePlaying
        );



        // Get and start the volume controller / initialize it
        volumeController = new VolumeControl(volume, dllController);
        volumeController.initialize();


        // Get and start the modal controller
        modalController = new ModalController(modal_main);
        buildPlaylistButtons();

        mediaPlayerObservable = new MediaPlayerObservable(songProgressBar, songProgressNum, songProgressNumTotal);
        dllController.bindProgressObserver(this.mediaPlayerObservable);


        //Search
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            timeline.stop();

            // Create a new timeline
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(new KeyFrame(AppConfig.getDelay(), event -> {
                // This block will be executed after the delay
                playlistController.updateViewSongList(newValue);
            }));

            // Start the timeline
            timeline.playFromStart();
        });
    }


    private void buildPlaylistButtons(){
        int index = 0;
        ArrayList<Playlist> playlist = dllController.getPlaylistsINT();


        for(Playlist val : playlist){
            File icon = dllController.getFile(
                    AppConfig.getPlaylistPath() + val.playlistId() + "_" + val.playlistName(),
                    "icon"
            );

            PlaylistButton playlistButton = new PlaylistButton(playlistController);
            playlistButton.setId(val.playlistId());
            playlistButton.setTitle(val.playlistName());
            playlistButton.setNumOfSongs(AppConfig.getPlaylistTotalSongs(dllController.getSongs(val.playlistId(), null).size()));

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