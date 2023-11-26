package GUI;

import javafx.application.Platform;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;


import javafx.scene.input.MouseEvent;




public class MainController {
    public FontAwesomeIconView iPlay;
    public JFXSlider volume;


    Boolean playState = true;

    public MainController() {
        System.out.println("Worked");
        initialize();
    }


    public void playBtn(ActionEvent actionEvent) {
        String switcherIcon = (playState) ? "PAUSE" : "PLAY";
        double switcherTransform = (playState) ? -0.5 : 1;

        iPlay.setIcon(FontAwesomeIcon.valueOf(switcherIcon));
        iPlay.setTranslateX(switcherTransform);

        playState = !playState;

        System.out.println("Worked?");
    }

    public void setVolume(MouseEvent mouseEvent) {
    }

    public void initialize() {
        Platform.runLater(() -> {
            volume.valueProperty().addListener((observable, oldValue, newValue) -> {
                double value = newValue.doubleValue();
                System.out.println("Slider value: " + value);
            });
        });
    }
}