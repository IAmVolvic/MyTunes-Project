package GUI.Components.Modal.SongModal;

import BE.Song;
import GUI.Components.Modal.ModalView;
import GUI.GUISingleton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.File;


public class EditSongModalView extends ModalView {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();


    //Class verbs
    private final Song songData;
    String pathToSong;

    //View FXML Elements
    private final Button songSelect = new Button();
    private final StackPane songSelectStack = new StackPane();
    private final Label songSelectSelected = new Label();
    private FontAwesomeIconView btnIcon = new FontAwesomeIconView();
    private final TextField songTitleInput = new TextField();


    public EditSongModalView(Song song) {
        super();
        songData = song;


        this.setTitle("Edit This Song");
        this.setActionTitle("Save");
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
        if (songTitleInput.getText() == null || songTitleInput.getText().trim().isEmpty() || single.getPlaylistController().getPlaylistId() < 1) {
            System.out.println("Something went wrong");
            return;
        }

        // Ask BLL to update the song
        single.getBllController().editSong(single.getPlaylistController().getPlaylistId(), songData.getId(), pathToSong, songTitleInput.getText());

        // Ask frontend to update its view
        single.getPlaylistController().editSong();

        single.getModalController().closeModal();
    }

    public HBox getView() {
        return this.getModalView();
    }
}
