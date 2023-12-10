package GUI.Components.Modal.ModalConfigs;

import BE.Song;
import DLL.DllController;
import GUI.Components.Modal.ModalController;
import GUI.Components.Modal.ModalView;
import GUI.Components.SongList;
import GUI.GUISingleton;
import GUI.PlaylistController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class DeleteSongModalView extends ModalView {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();

    private final Song songData;

    public DeleteSongModalView(Song song) {
        super();
        songData = song;


        this.modalBase.setMaxHeight(200);

        this.setTitle("Delete Song From Playlist");
        this.setActionTitle("Delete");
        this.createBody();
        this.createActionButton();
    }


    protected void createBody() {
        super.createBody();

        modalBody.setPrefHeight(50);

        Label warningMessage = new Label();
        warningMessage.getStyleClass().addAll("t-lg", "t-muted");
        warningMessage.setText("WARNING, this action cannot be undone! Are you sure you - \n want to proceed?");

        modalBody.getChildren().add(warningMessage);
    }


    protected void createActionButton() {
        super.createActionButton();
        modalAction.getStyleClass().remove("modal-actionButton");
        modalAction.getStyleClass().add("modal-actionButton-Danger");
        modalAction.setOnAction(event -> {
            deleteSong();
        });
    }


    private void deleteSong(){
        if (single.getPlaylistController().getPlaylistId() < 1) {
            System.out.println("Something went wrong");
            return;
        }

        // Ask DLL to remove the song
        List<Song> newSongList = single.getDllController().deleteSong(single.getPlaylistController().getPlaylistId(), songData.getId());

        // Remove the song from the list
        if(newSongList != null) {
            single.getPlaylistController().deleteSong(newSongList);
        }

        single.getModalController().closeModal();
    }

    public HBox getView() {
        return this.getModalView();
    }
}
