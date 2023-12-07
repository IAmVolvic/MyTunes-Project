package GUI.Components.Modal;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ModalView {
    //Base
    private final HBox modalBase = new HBox();
    private final VBox modalBaseChild = new VBox();

    // Title and Body
    private final Label modalTitle = new Label();
    protected HBox modalBody = new HBox();

    //Action Button
    protected final Button modalAction = new Button();
    private final HBox modalActionBody = new HBox();
    private final Text modalActionTitle = new Text();


    public ModalView(){
        //Initialize FXML elements
        createModalBase();
        createTitle();

        //Build the view
        buildModalNode();
    }


    //Get the node
    protected HBox getModalView(){
        return modalBase;
    }

    // Modal Setting Setters
    protected void setTitle(String newTitle) {
        modalTitle.setText(newTitle);
    }
    protected void setActionTitle(String newTitle) {
        modalActionTitle.setText(newTitle);
    }

    //Build the basic
    private void createModalBase(){
        modalBase.setId("modalView");
        modalBase.getStyleClass().add("modal-main");
        modalBase.setMaxHeight(300);
        modalBase.setMaxWidth(400);

        modalBaseChild.setAlignment(Pos.TOP_RIGHT);
        modalBaseChild.setSpacing(25);
    }

    private void createTitle(){
        modalTitle.setText("Add A Song");
        modalTitle.setPrefWidth(600);
        modalTitle.setPrefHeight(30);
        modalTitle.getStyleClass().add("t-white");
        modalTitle.getStyleClass().add("t-2xlg");
        modalTitle.getStyleClass().add("bold");
    }

    protected void createBody(){
        modalBody.setSpacing(25);
        modalBody.setPrefHeight(150);
        modalBody.setPrefWidth(420);
    }

    protected void createActionButton(){
        modalAction.setCursor(Cursor.HAND);
        modalAction.getStyleClass().add("modal-actionButton");

        modalActionBody.setAlignment(Pos.CENTER);
        modalActionBody.setPrefHeight(25);
        modalActionBody.setPrefWidth(100);

        modalActionTitle.getStyleClass().add("t-nm");
        modalActionTitle.getStyleClass().add("bold");

        modalActionBody.getChildren().add(modalActionTitle);
        modalAction.setGraphic(modalActionBody);
    }

    //Create the full node
    private void buildModalNode() {
        modalBaseChild.getChildren().addAll(modalTitle, modalBody, modalAction);
        modalBase.getChildren().add(modalBaseChild);
    }
}
