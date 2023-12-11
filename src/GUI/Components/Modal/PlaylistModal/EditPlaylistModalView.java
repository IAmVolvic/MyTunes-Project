package GUI.Components.Modal.PlaylistModal;

import APP_SETTINGS.AppConfig;
import BE.Playlist;
import GUI.Components.Modal.ModalView;
import GUI.Components.PlaylistButton;
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


public class EditPlaylistModalView extends ModalView {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();


    //View FXML Elements
    private final Button imageSelect = new Button();
    private final StackPane imageSelectStack = new StackPane();
    private final Label imageSelectSelected = new Label();
    private FontAwesomeIconView btnIcon = new FontAwesomeIconView();
    private final TextField playlistTitleInput = new TextField();


    //Class verbs
    private String pathToImage;

    private final PlaylistButton  playlistButton;
    private final int             currentPlaylistId;
    private final Playlist          playlistData;


    public EditPlaylistModalView(PlaylistButton playlistButtonNode, int plId) {
        super();
        playlistButton = playlistButtonNode;
        currentPlaylistId = plId;
        playlistData = single.getPlaylistController().getPlaylistData(plId);

        this.setTitle("Edit This Playlist");
        this.setActionTitle("Save");
        this.createBody();
        this.createActionButton();
    }


    protected void createBody() {
        super.createBody();

        imageSelect.setOnAction(event -> {
            File file = single.getDllController().callFileChooser(event, "playlist_add");

            if(file != null){
                imageSelectSelected.setText(file.getName());
                pathToImage = file.getPath();
            }
        });

        imageSelect.setCursor(Cursor.HAND);
        imageSelect.getStyleClass().add("modal-imgButton");
        imageSelect.setPrefWidth(210);
        imageSelect.setPrefHeight(170);

        btnIcon.setIcon(FontAwesomeIcon.IMAGE);
        btnIcon.setSize("55");
        btnIcon.getStyleClass().add("t-sub2");

        imageSelectSelected.setText(" ");
        imageSelectSelected.getStyleClass().add("t-white");
        imageSelectSelected.getStyleClass().add("t-sm");
        imageSelectSelected.getStyleClass().add("bold");
        imageSelectSelected.setTranslateY(55);

        imageSelectStack.getChildren().add(btnIcon);
        imageSelectStack.getChildren().add(imageSelectSelected);
        imageSelect.setGraphic(imageSelectStack);

        playlistTitleInput.getStyleClass().add("modal-input");
        playlistTitleInput.setText(playlistData.playlistName());
        playlistTitleInput.setPromptText("Playlist Name");
        playlistTitleInput.setPrefHeight(40);
        playlistTitleInput.setPrefWidth(210);


        modalBody.getChildren().addAll(imageSelect, playlistTitleInput);
    }


    protected void createActionButton() {
        super.createActionButton();
        modalAction.setOnAction(event -> {
            editPlaylist();
        });
    }


    private void editPlaylist(){
        if (playlistTitleInput.getText() == null || playlistTitleInput.getText().trim().isEmpty() || !playlistTitleInput.getText().matches("^[a-zA-Z0-9][a-zA-Z0-9\\s]{0,30}[a-zA-Z0-9]$") || currentPlaylistId < 1) {
            System.out.println("Something went wrong");
            return;
        }

        // Ask the DLL to do stuff
        single.getDllController().editPlaylist(currentPlaylistId, playlistTitleInput.getText(), pathToImage);

        // Change button look
        if(pathToImage != null){
            playlistButton.setIcon(single.getDllController().getFile(AppConfig.getPlaylistPath() + playlistButton.getId() + "_" + playlistData.playlistName(),"icon"));
        }
        playlistButton.setTitle(playlistTitleInput.getText());

        // Set view if selected
        if(playlistData == single.getPlaylistController().getSelected()){
            single.getPlaylistController().updateFullView();
        }

        single.getModalController().closeModal();
    }

    public HBox getView() {
        return this.getModalView();
    }
}
