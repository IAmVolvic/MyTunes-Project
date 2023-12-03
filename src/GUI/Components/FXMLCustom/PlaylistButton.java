package GUI.Components.FXMLCustom;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;

public class PlaylistButton{
    //Button Settings
    int perWidth = 300;
    int perHeight = 66;
    boolean isActive;

    // ButtonBase
    Button buttonBase       = new Button("");
    // HBox Base
    HBox hboxBase           = new HBox();
    // PlayList Icon
    Pane plIcon             = new Pane();
    // VBox Base
    VBox vboxBase           = new VBox();
    // Title Label
    Label playlistTitle     = new Label();
    // Num of songs Label
    Label numOfSongs        = new Label();


    public PlaylistButton(){
        this.isActive = false;

        //Button Base
        buttonBase.setPadding(new Insets(10));
        buttonBase.setPrefWidth(perWidth);
        buttonBase.setPrefHeight(perHeight);
        buttonBase.setAlignment(Pos.CENTER);
        buttonBase.getStyleClass().add("playlist");
        buttonBase.setCursor(Cursor.HAND);

        //HBox Base
        hboxBase.setFillHeight(false);
        hboxBase.setSpacing(10);

        //Playlist Icon
        plIcon.getStyleClass().add("Image");
        plIcon.setMinHeight(50);
        plIcon.setMinWidth(50);
        plIcon.setMaxHeight(50);
        plIcon.setMaxWidth(50);

        //VBox Base
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
        vboxBase.getChildren().add(playlistTitle);
        vboxBase.getChildren().add(numOfSongs);
        hboxBase.getChildren().add(plIcon);
        hboxBase.getChildren().add(vboxBase);
        buttonBase.setGraphic(hboxBase);
    }


    public Button getButton(){
        return this.buttonBase;
    }

    public void setTitle(String newTitle){
        this.playlistTitle.setText(newTitle);
    }

    public void setIcon(File newIcon) {
        this.plIcon.setStyle("-fx-background-image: url('" + newIcon.toURI().toString() + "'); ");
    }

    public void toggleActive(){
        this.isActive = !this.isActive;

        if(this.isActive){
            buttonBase.getStyleClass().add("playlist-active");
        }else{
            buttonBase.getStyleClass().remove("playlist-active");
        }
    }
}