package GUI.Components.FXMLCustom;

import BE.Playlist;
import DLL.DllController;
import GUI.Components.Modal.ModalController;
import GUI.PlaylistController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;


import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;

public class ModalNewPlaylist {
    // Outside Content
    private final VBox playlist_list;
    private final DllController dllController;
    private final ModalController modalController;
    private final PlaylistController playlistController;

    // FXML Elements
    private final HBox modalBase = new HBox();
    private final VBox modalBaseChild = new VBox();
    private final Label modalTitle = new Label();
    private final HBox  modalBody = new HBox();
    private final Button imageSelect = new Button();
    private final StackPane imageSelectStack = new StackPane();
    private final Label imageSelectSelected = new Label();
    private FontAwesomeIconView btnIcon = new FontAwesomeIconView();
    private final TextField playlistTitleInput = new TextField();
    private final Button modalAction = new Button();
    private final HBox modalActionBody = new HBox();
    private final Text modalActionTitle = new Text();

    // Extra
    private String pathToImage;


    public ModalNewPlaylist(DllController dc, ModalController modalC, PlaylistController plC, VBox pl) {
        playlist_list = pl;
        dllController = dc;
        modalController = modalC;
        playlistController = plC;

        modalBase.setId("modalView");
        modalBase.getStyleClass().add("modal-main");
        modalBase.setMaxHeight(300);
        modalBase.setMaxWidth(400);

        modalBaseChild.setAlignment(Pos.TOP_RIGHT);
        modalBaseChild.setSpacing(25);

        modalTitle.setText("Create New Playlist");
        modalTitle.setPrefWidth(600);
        modalTitle.setPrefHeight(30);
        modalTitle.getStyleClass().add("t-white");
        modalTitle.getStyleClass().add("t-2xlg");
        modalTitle.getStyleClass().add("bold");

        modalBody.setSpacing(25);
        modalBody.setPrefHeight(150);
        modalBody.setPrefWidth(420);


        //Body Inputs
        imageSelect.setOnAction(event -> {
            File file = dllController.callFileChooser(event);

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


        // Action Button
        modalAction.setOnAction(event -> {
            createPlaylist();
        });

        modalAction.setCursor(Cursor.HAND);
        modalAction.getStyleClass().add("modal-actionButton");

        modalActionBody.setAlignment(Pos.CENTER);
        modalActionBody.setPrefHeight(25);
        modalActionBody.setPrefWidth(100);

        modalActionTitle.setText("Create");
        modalActionTitle.getStyleClass().add("t-nm");
        modalActionTitle.getStyleClass().add("bold");

        modalActionBody.getChildren().add(modalActionTitle);
        modalAction.setGraphic(modalActionBody);




        // Build the node
        modalBody.getChildren().add(imageSelect);
        modalBody.getChildren().add(playlistTitleInput);

        modalBaseChild.getChildren().add(modalTitle);
        modalBaseChild.getChildren().add(modalBody);
        modalBaseChild.getChildren().add(modalAction);

        modalBase.getChildren().add(modalBaseChild);
    }

    public HBox getNewPlaylistModal() { return modalBase; }



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

        Playlist creatPlaylist = dllController.createPlaylist(pathToImage, playlistTitleInput.getText());

        if(creatPlaylist != null){
            File icon = dllController.getFile("resources/Playlists/"+playlistTitleInput.getText(), "icon");

            PlaylistButton playlistButton = new PlaylistButton(playlistController);
            playlistButton.setTitle(playlistTitleInput.getText());
            playlistButton.setIcon(icon);
            playlistButton.setId(creatPlaylist.playlistId());
            playlist_list.getChildren().add(playlistButton.getButton());

            modalController.closeModal();
        }
    }
}

