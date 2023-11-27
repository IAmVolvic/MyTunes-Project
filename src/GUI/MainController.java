package GUI;


import BE.Song;
import javafx.application.Platform;
import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import DLL.DllController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
    public FontAwesomeIconView iPlay;
    public JFXSlider volume;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableView songList;

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


        // Disable Resort
        col1.setReorderable(false);
        col2.setReorderable(false);
        col3.setReorderable(false);
        col4.setReorderable(false);


        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("button"));

        DllController dllController = new DllController();

        songList.setItems(dllController.getSongs());
    }


    public void printSongs(ActionEvent actionEvent) {}
}