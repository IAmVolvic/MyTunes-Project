package GUI.Components.Modal.ModalConfigs;

import APP_SETTINGS.AppConfig;

import BE.Playlist;

import GUI.Components.PlaylistButton;
import GUI.Components.Modal.ModalView;
import GUI.GUISingleton;
import javafx.scene.Cursor;
import javafx.scene.layout.HBox;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;

public class NewPlaylistModalView extends ModalView {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();

    //Class verbs
    String pathToImage;

    //View FXML Elements
    private final Button imageSelect = new Button();
    private final StackPane imageSelectStack = new StackPane();
    private final Label imageSelectSelected = new Label();
    private FontAwesomeIconView btnIcon = new FontAwesomeIconView();
    private final TextField playlistTitleInput = new TextField();


    //Outside Controllers
    VBox playlist_list;


    public NewPlaylistModalView(VBox pl) {
        super();

        //Setting outside controllers
        playlist_list = pl;

        this.setTitle("Create New Playlist");
        this.setActionTitle("Create");
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
        playlistTitleInput.setPromptText("Playlist Name");
        playlistTitleInput.setPrefHeight(40);
        playlistTitleInput.setPrefWidth(210);


        modalBody.getChildren().addAll(imageSelect, playlistTitleInput);
    }


    protected void createActionButton() {
        super.createActionButton();
        modalAction.setOnAction(event -> {
            createPlaylist();
        });
    }


    private void createPlaylist(){
        //  Make sure the file selected isn't null
        //  Make sure title isn't null or empty
        //  Make sure title can follow set of rules
        //  1, The string cannot have a space at the beginning or end of the string ( anywhere in between is fine ) : [a-zA-Z0-9]...
        //  2, The string cannot at any point have any special characters : ...[a-zA-Z0-9\s]...
        //  4, The string can only be up to 32 characters long : ...{0,30}
        if (playlistTitleInput.getText() == null || playlistTitleInput.getText().trim().isEmpty() || !playlistTitleInput.getText().matches("^[a-zA-Z0-9][a-zA-Z0-9\\s]{0,30}[a-zA-Z0-9]$") || pathToImage == null) {
            System.out.println("Something went wrong");
            return;
        }

        Playlist creatPlaylist = single.getDllController().createPlaylist(pathToImage, playlistTitleInput.getText());

        if(creatPlaylist != null){
            File icon = single.getDllController().getFile(
                    AppConfig.getPlaylistPath() + creatPlaylist.playlistId() + "_" + playlistTitleInput.getText(),
                    "icon"
            );

            PlaylistButton playlistButton = new PlaylistButton();
            playlistButton.setTitle(playlistTitleInput.getText());
            playlistButton.setIcon(icon);
            playlistButton.setId(creatPlaylist.playlistId());
            playlist_list.getChildren().add(playlistButton.getButton());

            single.getPlaylistController().setPlaylistView(playlistButton);

            single.getModalController().closeModal();
        }
    }


    public HBox getView() {
        return this.getModalView();
    }
}
