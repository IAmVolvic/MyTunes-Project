package GUI.Components.Modal.ModalConfigs;

import APP_SETTINGS.AppConfig;
import BE.Playlist;
import BE.Song;
import DLL.DllController;
import GUI.Components.FXMLCustom.PlaylistButton;
import GUI.Components.Modal.ModalController;
import GUI.Components.Modal.ModalView;
import GUI.Components.SongList;
import GUI.PlaylistController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AddSongModalView extends ModalView {
    //Class verbs
    String pathToImage;

    //View FXML Elements
    private final Button songSelect = new Button();
    private final StackPane songSelectStack = new StackPane();
    private final Label songSelectSelected = new Label();
    private FontAwesomeIconView btnIcon = new FontAwesomeIconView();
    private final TextField songTitleInput = new TextField();


    //Outside Controllers
    DllController dllController;
    ModalController modalController;
    SongList tableController;


    public AddSongModalView(DllController dllC, ModalController modalC, SongList tC) {
        super();

        //Setting outside controllers
        dllController = dllC;
        modalController = modalC;
        tableController = tC;

        this.setTitle("Add Song To Playlist");
        this.setActionTitle("Add");
        this.createBody();
        this.createActionButton();
    }


    protected void createBody() {
        super.createBody();

        songSelect.setOnAction(event -> {
            File file = dllController.callFileChooser(event, "music_add");

            if(file != null){
                songTitleInput.setText(file.getName());
                songSelectSelected.setText(file.getName());
                pathToImage = file.getPath();
            }
        });

        songSelect.setCursor(Cursor.HAND);
        songSelect.getStyleClass().add("modal-imgButton");
        songSelect.setPrefWidth(210);
        songSelect.setPrefHeight(170);

        btnIcon.setIcon(FontAwesomeIcon.MUSIC);
        btnIcon.setSize("55");
        btnIcon.getStyleClass().add("t-sub2");

        songSelectSelected.setText(" ");
        songSelectSelected.getStyleClass().add("t-white");
        songSelectSelected.getStyleClass().add("t-sm");
        songSelectSelected.getStyleClass().add("bold");
        songSelectSelected.setTranslateY(55);

        songTitleInput.getStyleClass().add("modal-input");
        songTitleInput.setPromptText("Song Name");
        songTitleInput.setPrefHeight(40);
        songTitleInput.setPrefWidth(210);


        songSelectStack.getChildren().addAll(btnIcon, songSelectSelected);
        songSelect.setGraphic(songSelectStack);

        modalBody.getChildren().addAll(songSelect, songTitleInput);
    }


    protected void createActionButton() {
        super.createActionButton();
        modalAction.setOnAction(event -> {
            addSong();
        });
    }


    private void addSong(){
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//
//        ObservableList<Song> songData = FXCollections.observableArrayList();
//
//        Song test = new Song(1,  "Cool song name", formatter.format(now), 2 );
//        songData.add(test);
//
//        tableController.addSong(songData);
    }


    public HBox getView() {
        return this.getModalView();
    }
}
