package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import javafx.event.Event;

public class MainController {
    Parent root;
    Stage stage;

    public MainController() {
        System.out.println("Worked");
    }

    public void newScreen1(ActionEvent actionEvent) {

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ModalPop.fxml")));
            stage = (Stage)( (Node) actionEvent.getSource() ).getScene().getWindow();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        }
        catch (IOException e) {
//            e.printStackTrace();
        }

    }


    public void newScreen2(ActionEvent actionEvent) {

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainWindow.fxml")));
            stage = (Stage)( (Node) actionEvent.getSource() ).getScene().getWindow();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 1280, 720));
            stage.show();
        }
        catch (IOException e) {
//            e.printStackTrace();
        }

    }
}
