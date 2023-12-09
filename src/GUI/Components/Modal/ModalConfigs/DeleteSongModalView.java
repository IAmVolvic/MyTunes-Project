package GUI.Components.Modal.ModalConfigs;

import BE.Song;
import DLL.DllController;
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

import java.io.File;
import java.util.ArrayList;


public class DeleteSongModalView extends ModalView {
    //Class verbs
    String pathToSong;


    //Outside Controllers
    DllController dllController; // Ask DLL to delete file / db / update the cache
    ModalController modalController; // When done ask modal to close
    SongList tableController; // updat
    PlaylistController playlistController;


    public DeleteSongModalView(DllController dllC, ModalController modalC, SongList tC, PlaylistController pC) {
        super();

        //Setting outside controllers
        dllController = dllC;
        modalController = modalC;
        tableController = tC;
        playlistController = pC;

        this.setTitle("Delete Song From Playlist");
        this.setActionTitle("Add");
        this.createBody();
        this.createActionButton();
    }


    protected void createBody() {
        super.createBody();
    }


    protected void createActionButton() {
        super.createActionButton();
        modalAction.setOnAction(event -> {
            deleteSong();
        });
    }


    private void deleteSong(){
        if (playlistController.getPlaylistId() < 1) {
            System.out.println("Something went wrong");
            return;
        }

        modalController.closeModal();
    }

    public HBox getView() {
        return this.getModalView();
    }
}
