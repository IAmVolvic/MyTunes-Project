package GUI;

import javafx.application.Platform;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class MainController {
    public FontAwesomeIconView iPlay;
    public JFXSlider volume;

    Parent root;
    Stage stage;

    Boolean playState = true;

    public MainController() {
        System.out.println("Worked");
        initialize();
    }

    public void newScreen1(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ModalPop.fxml")));
            stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public void newScreen2(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainWindow.fxml")));
            stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        } catch (IOException e) {
            // e.printStackTrace();
        }
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