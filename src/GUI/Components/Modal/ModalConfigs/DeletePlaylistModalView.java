package GUI.Components.Modal.ModalConfigs;

import GUI.Components.Modal.ModalController;
import GUI.Components.Modal.ModalView;
import GUI.Components.SongList;
import GUI.GUISingleton;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class DeletePlaylistModalView extends ModalView {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();

    //Class verbs
    String pathToSong;

    //Outside Controllers
    SongList tableController;


    public DeletePlaylistModalView() {
        super();

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
        if (single.getPlaylistController().getPlaylistId() < 1) {
            System.out.println("Something went wrong");
            return;
        }

        single.getModalController().closeModal();
    }

    public HBox getView() {
        return this.getModalView();
    }
}
