package GUI.Components.Modal;

import javafx.scene.layout.StackPane;

public class ModalController {
    private boolean isOpen = false;
    private final StackPane modal_main;

    public ModalController(StackPane modalMain) {
        modal_main = modalMain;
    }


    public boolean isModalOpen() {
        return isOpen;
    }

    public void openModal() {
        modal_main.setVisible(true);
        isOpen = true;
    }

    public void closeModal() {
        modal_main.setVisible(false);
        isOpen = false;
    }
}
