package my.tunesProject.gui;

import my.tunesProject.bll.BLLManager;
import my.tunesProject.exceptions.PetShopException;
import my.tunesProject.be.Person;
import my.tunesProject.be.Pet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML
    private ListView<Person> lstOwners;
    @FXML
    private ListView<Pet> lstPets;

    private Model model = new Model();

    private BLLManager bll = new BLLManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lstOwners.setItems(model.getPersons());
        lstPets.setItems(model.getPets());
    }

    public void loadOwnersClick(ActionEvent actionEvent) {
        model.loadOwners();
    }

    public void seePetsClick(ActionEvent actionEvent) {
        Person selected = lstOwners.getSelectionModel().getSelectedItem();
        try {
            model.loadPets(selected);
        } catch (PetShopException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }


}
