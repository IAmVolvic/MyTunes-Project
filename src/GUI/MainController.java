package GUI;


import DLL.DllController;
import GUI.Components.Modal.ModalController;
import GUI.Components.PlayButton;
import GUI.Components.SongList;
import GUI.Components.VolumeControl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.jfoenix.controls.JFXSlider;
import javafx.scene.layout.StackPane;


public class MainController {
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
        modalController.openModal();
    }


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
    }
}