package GUI.Components.Modal.ModalConfigs.SongModal.SongModal;

import BE.Song;

import GUI.Components.Modal.ModalView;
import GUI.Components.SongList;

import GUI.GUISingleton;
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

import java.io.File;
import java.util.ArrayList;


public class NewSongModalView extends ModalView {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();


    //Class verbs
    String pathToSong;

    //View FXML Elements
    private final Button songSelect = new Button();
    private final StackPane songSelectStack = new StackPane();
    private final Label songSelectSelected = new Label();
    private FontAwesomeIconView btnIcon = new FontAwesomeIconView();
    private final TextField songTitleInput = new TextField();


    //Outside Controllers
    SongList tableController;


    public NewSongModalView(SongList tC) {
        super();

        //Setting outside controllers
        tableController = tC;

        this.setTitle("Add Song To Playlist");
        this.setActionTitle("Add");
        this.createBody();
        this.createActionButton();
    }


    protected void createBody() {
        super.createBody();

        songSelect.setOnAction(event -> {
            File file = single.getBllController().callFileChooser(event, "music_add");

            if(file != null){
                songTitleInput.setText(file.getName().substring(0, file.getName().lastIndexOf('.')));
                songSelectSelected.setText(file.getName());
                pathToSong = file.getPath();
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
        if (songTitleInput.getText() == null || songTitleInput.getText().trim().isEmpty() || pathToSong == null || single.getPlaylistController().getPlaylistId() < 1) {
            System.out.println("Something went wrong");
            return;
        }

        int index = 1;

        ArrayList<Song> newSongConstruct = single.getBllController().createSong(single.getPlaylistController().getPlaylistId(), single.getPlaylistController().getPlaylistName(), pathToSong, songTitleInput.getText());
        ObservableList<Song> songData = FXCollections.observableArrayList();

        for(Song val : newSongConstruct){
            val.setTableId(index);
            songData.add(val);
            index++;
        }

        tableController.addSong(songData);
        single.getPlaylistController().updateTotalSongsNum(newSongConstruct.size());
        single.getPlaylistController().setMediaPlaylist();

        single.getModalController().closeModal();
    }

    public HBox getView() {
        return this.getModalView();
    }
}
