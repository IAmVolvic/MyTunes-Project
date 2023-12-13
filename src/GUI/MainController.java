package GUI;


import APP_SETTINGS.AppConfig;
import BE.Playlist;
import BE.Song;
import BLL.Media.MediaPlayerObservable;
import GUI.Components.PlaylistButton;
import GUI.Components.Modal.PlaylistModal.NewPlaylistModalView;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.File;
import java.util.ArrayList;


public class MainController {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();


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
    public TableView<Song> songList;
    public TableColumn<String, Integer> col1;
    public TableColumn<String, String>  col2;
    public TableColumn<String, String>  col3;
    public TableColumn<String, String>    col4;



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
    private SongList tableController;


    private MediaPlayerObservable mediaPlayerObservable;


    //Constructor
    public MainController() {
        // Run later when everything is being created
        Platform.runLater(this::postInitialize);
    }


    //New Playlist Button
    public void newPlaylist(ActionEvent o) {
        NewPlaylistModalView modalView = new NewPlaylistModalView(playlist_list);
        single.getModalController().openModal(modalView.getView());
    }


    public void newSong(ActionEvent actionEvent) {
        GUI.Components.Modal.ModalConfigs.SongModal.SongModal.NewSongModalView modalView = new GUI.Components.Modal.ModalConfigs.SongModal.SongModal.NewSongModalView(tableController);
        single.getModalController().openModal(modalView.getView());
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
        single.getModalController().closeModal();
    }


    // Post Initialize
    private void postInitialize() {
        //Setting the Label for BLL -> MediaController to use
        single.getBllController().initializeSongLabel(songLabel);

        // Get and start the modal controller
        single.getModalController().setModalMain(modal_main);

        // Get and start the songs table / initialize it
        tableController = new SongList(songList, col1, col2, col3, col4);


        //Set PlayButton Controller
        mediaButtons = new MediaButtons(iPlay);


        //Set Playlist Controller
        single.getPlaylistController().setNodes(
                mediaButtons,
                tableController,
                playlist_list,
                playlistViewIcon,
                playlistViewTitle,
                viewTotalSongs,
                playlistIconPlaying,
                playlistNamePlaying
        );



        // Get and start the volume controller / initialize it
        volumeController = new VolumeControl(volume);
        volumeController.initialize();


        //Bind progress bar and info to media player
        mediaPlayerObservable = new MediaPlayerObservable(songProgressBar, songProgressNum, songProgressNumTotal);
        single.getBllController().bindProgressObserver(this.mediaPlayerObservable);


        //Search
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            timeline.stop();

            // Create a new timeline
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(new KeyFrame(AppConfig.getDelay(), event -> {
                // This block will be executed after the delay
                single.getPlaylistController().updateViewSongList(newValue);
            }));

            // Start the timeline
            timeline.playFromStart();
        });

        buildPlaylistButtons();
    }


    // Start creating the playlist buttons on first load
    private void buildPlaylistButtons(){
        int index = 0;
        ArrayList<Playlist> playlist = single.getBllController().getPlaylistsINT();


        for(Playlist val : playlist){
            File icon = single.getBllController().getFile(
                    AppConfig.getPlaylistPath() + val.playlistId() + "_" + val.playlistName(),
                    "icon"
            );

            PlaylistButton playlistButton = new PlaylistButton();
            playlistButton.setId(val.playlistId());
            playlistButton.setTitle(val.playlistName());
            playlistButton.setNumOfSongs(AppConfig.getPlaylistTotalSongs(single.getBllController().getSongs(val.playlistId(), null).size()));

            if(icon != null){
                playlistButton.setIcon(icon);
            }

            if(index < 1){
                single.getPlaylistController().setPlaylistView(playlistButton);
            }

            playlist_list.getChildren().add(playlistButton.getButton());
            index++;
        }
    }

}