package GUI.Components.Modal;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ModalController {
    private boolean isOpen = false;
    private final StackPane modal_main;
    private Node currentView;

    public ModalController(StackPane modalMain) {
        modal_main = modalMain;
    }


    public boolean isModalOpen() {
        return isOpen;
    }

    public void openModal(Node view) {
        if(currentView != view){
            //Remove the old view
            modal_main.getChildren().remove(currentView);
            //Add the new view
            modal_main.getChildren().add(view);
            //Set the view in memory
            currentView = view;
        }

        modal_main.setVisible(true);
        isOpen = true;
    }

    public void closeModal() {
        modal_main.setVisible(false);
        isOpen = false;
    }
}
