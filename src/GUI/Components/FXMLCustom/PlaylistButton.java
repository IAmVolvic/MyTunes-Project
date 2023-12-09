package GUI.Components.FXMLCustom;

import GUI.PlaylistController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;

public class PlaylistButton{
    //Button Settings
    private int perWidth = 300;
    private int perHeight = 66;
    private int btnID;

    //Controller
    private final PlaylistController playlistController;

    // ButtonBase
    private final Button buttonBase       = new Button("");
    // Button Child
    private final HBox hboxBase = new HBox();
    // PlayList Icon
    private final Pane plIcon             = new Pane();
    // Title Label
    private final Label playlistTitle     = new Label();
    // Num of songs Label
    private final Label numOfSongs        = new Label();

    //Context Menu
    private final ContextMenu contextMenu = new ContextMenu();


    //Constructor
    public PlaylistButton(PlaylistController plController){
        playlistController = plController;

        //Button Base
        buttonBase.setOnAction(event -> {
            setPlaylistView(this);
        });

        buttonBase.setPadding(new Insets(10));
        buttonBase.setPrefWidth(perWidth);
        buttonBase.setPrefHeight(perHeight);
        buttonBase.setAlignment(Pos.CENTER);
        buttonBase.getStyleClass().add("playlist");
        buttonBase.setCursor(Cursor.HAND);


        // HBox Base
        hboxBase.setFillHeight(false);
        hboxBase.setSpacing(10);

        //Playlist Icon
        plIcon.getStyleClass().add("Image");
        plIcon.setMinHeight(50);
        plIcon.setMinWidth(50);
        plIcon.setMaxHeight(50);
        plIcon.setMaxWidth(50);

        //VBox Base
        // VBox Base
        VBox vboxBase = new VBox();
        vboxBase.setAlignment(Pos.CENTER_LEFT);
        vboxBase.setSpacing(3);
        vboxBase.setPrefWidth(213);
        vboxBase.setPrefHeight(65);

        //Playlist Title Label
        playlistTitle.setPrefWidth(160);
        playlistTitle.setPrefHeight(17);
        playlistTitle.setText("Summer Songs");
        playlistTitle.getStyleClass().add("t-white");
        playlistTitle.getStyleClass().add("bold");
        playlistTitle.getStyleClass().add("t-lg");

        //Playlist NumOfSongs Label
        numOfSongs.setPrefWidth(160);
        numOfSongs.setPrefHeight(17);
        numOfSongs.setText("Playlist - 0 songs");
        numOfSongs.getStyleClass().add("t-white");
        numOfSongs.getStyleClass().add("normal");
        numOfSongs.getStyleClass().add("t-sm");

        //Build the button
        createContextMenu();
        vboxBase.getChildren().add(playlistTitle);
        vboxBase.getChildren().add(numOfSongs);
        hboxBase.getChildren().add(plIcon);
        hboxBase.getChildren().add(vboxBase);
        buttonBase.setGraphic(hboxBase);
    }


    //Return the created button
    public Button getButton(){
        return this.buttonBase;
    }

    //Setters
    public void setTitle(String newTitle){
        this.playlistTitle.setText(newTitle);
    }

    public void setNumOfSongs(String newString){
        this.numOfSongs.setText(newString);
    }

    public void setIcon(File newIcon) {
        this.plIcon.setStyle("-fx-background-image: url('" + newIcon.toURI().toString() + "'); ");
    }

    public void setId(int newId) {
        this.btnID = newId;
    }

    public void setActiveStyle(){
        buttonBase.getStyleClass().add("playlist-active");
    }

    private void setPlaylistView(PlaylistButton btn) {
        playlistController.setPlaylistView(btn);
    }

    //Getters
    public int getId() {
        return this.btnID;
    }


    //Context Menu
    private void createContextMenu() {
        //Edit Context menu
        contextMenu.getStyleClass().add("customContext");
        //Create Sub buttons
        MenuItem editButton         = new MenuItem("Edit");
        SeparatorMenuItem spacer    = new SeparatorMenuItem();
        MenuItem deleteButton       = new MenuItem("Delete");

        editButton.getStyleClass().add("customContext-Btn");
        deleteButton.getStyleClass().add("customContext-Btn-Danger");


        contextMenu.getItems().addAll(editButton, spacer, deleteButton);
        this.buttonBase.setContextMenu(contextMenu);
    }
}