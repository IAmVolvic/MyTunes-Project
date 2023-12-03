package GUI.Components.FXMLCustom;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ModalNewPlaylist {
    private final HBox modalBase = new HBox();
    private final VBox modalBaseChild = new VBox();
    private final Label modalTitle = new Label();
    private final HBox  modalBody = new HBox();

    private final Button imageSelect = new Button();
    private FontAwesomeIconView btnIcon = new FontAwesomeIconView();
    private TextField playlistTitleInput = new TextField();

    private final Button modalAction = new Button();
    private final HBox modalActionBody = new HBox();
    private final Text modalActionTitle = new Text();


    public ModalNewPlaylist() {
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
        imageSelect.setCursor(Cursor.HAND);
        imageSelect.getStyleClass().add("modal-imgButton");
        imageSelect.setPrefWidth(210);
        imageSelect.setPrefHeight(170);
        btnIcon.setIcon(FontAwesomeIcon.IMAGE);
        btnIcon.setSize("55");
        btnIcon.getStyleClass().add("t-sub2");
        imageSelect.setGraphic(btnIcon);

        playlistTitleInput.getStyleClass().add("modal-input");
        playlistTitleInput.setPromptText("Playlist Name");
        playlistTitleInput.setPrefHeight(40);
        playlistTitleInput.setPrefWidth(210);


        // Action Button
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
}
