package GUI.Components.Modal.PlaylistModal;

import GUI.Components.Modal.ModalView;
import GUI.GUISingleton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class DeletePlaylistModalView extends ModalView {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();

    //Class verbs
    private final Button playlistButton;
    private final int currentPlaylistId;

    public DeletePlaylistModalView(Button playlistButtonNode, int plId) {
        super();
        playlistButton = playlistButtonNode;
        currentPlaylistId = plId;


        this.modalBase.setMaxHeight(200);

        this.setTitle("Delete Playlist");
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
        if (currentPlaylistId < 1) {
            System.out.println("Something went wrong");
            return;
        }

        //Ask BLL to delete the playlist
        single.getBllController().deletePlaylist(currentPlaylistId);
        //reset the playlist view
        single.getPlaylistController().resetFullView(playlistButton);

        single.getModalController().closeModal();
    }

    public HBox getView() {
        return this.getModalView();
    }
}
