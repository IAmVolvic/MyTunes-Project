package GUI;


import DLL.DllController;
import GUI.Components.PlayButton;
import GUI.Components.SongList;
import GUI.Components.VolumeControl;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.jfoenix.controls.JFXSlider;



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

    //Controllers
    private PlayButton playBTNController;
    private VolumeControl volumeController;
    private final DllController dllController = new DllController();


    //Constructor
    public MainController() {
        // Run later when everything is being created
        Platform.runLater(this::PostInitialize);
    }


    //Play Button
    public void playBtn(ActionEvent actionEvent) {
        //Send Button Logic to its own container
        playBTNController.PlayButtonClicked(actionEvent);
        volumeController.Initialize();
    }



    // Post Initialize
    private void PostInitialize() {
        //Set PlayButton Controller
        playBTNController = new PlayButton(iPlay, dllController);


        // Get and start the songs table / initialize it
        SongList tableController = new SongList(col1, col2, col3, col4);
        tableController.Initialize();

        // Get and start the volume controller / initialize it
        volumeController = new VolumeControl(volume, dllController);
        volumeController.Initialize();
    }
}