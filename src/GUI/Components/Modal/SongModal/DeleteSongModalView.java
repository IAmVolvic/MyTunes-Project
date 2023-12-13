package GUI.Components.Modal.ModalConfigs.SongModal.SongModal;

import BE.Song;
import GUI.Components.Modal.ModalView;
import GUI.GUISingleton;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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

        // Ask BLL to remove the song
        List<Song> newSongList = single.getBllController().deleteSong(single.getPlaylistController().getPlaylistId(), songData.getId());

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
