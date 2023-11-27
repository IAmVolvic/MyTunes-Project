package GUI;


import javafx.application.Platform;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;

import DLL.DllController;


public class MainController {
    public FontAwesomeIconView iPlay;
    public JFXSlider volume;

    Boolean playState = true;

    public MainController() {
        System.out.println("Project Started");

        // Run later when everything is being created
        Platform.runLater(this::PostInitialize);
    }


    public void playBtn(ActionEvent actionEvent) {
        String switcherIcon = (playState) ? "PAUSE" : "PLAY";
        double switcherTransform = (playState) ? -0.5 : 1;

        iPlay.setIcon(FontAwesomeIcon.valueOf(switcherIcon));
        iPlay.setTranslateX(switcherTransform);

        playState = !playState;

        System.out.println("Worked?");
    }


    public void PostInitialize() {

        // Volume Listener
        volume.valueProperty().addListener((observable, oldValue, newValue) -> {
            Number value = observable.getValue();
            System.out.println("Slider value: " + value);
        });
    }


    public void printSongs(ActionEvent actionEvent) {
        DllController dllController = new DllController();
        dllController.getSongs();
    }
}