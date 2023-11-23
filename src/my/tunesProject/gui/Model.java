package my.tunesProject.gui;

import my.tunesProject.be.Person;
import my.tunesProject.be.Pet;
import my.tunesProject.bll.BLLManager;
import my.tunesProject.exceptions.PetShopException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private final ObservableList<Person> persons;
    private final ObservableList<Pet> pets;

    private BLLManager bll = new BLLManager();

    public Model() {
        persons = FXCollections.observableArrayList();
        pets = FXCollections.observableArrayList();
    }

    public ObservableList<Person> getPersons() {
        return persons;
    }

    public ObservableList<Pet> getPets() {
        return pets;
    }

    public void loadOwners() {
        persons.clear();
        persons.addAll(bll.getAllPersons());
    }

    public void loadPets(Person selected) throws PetShopException {
        pets.clear();
        pets.addAll(bll.getPetsFromOwner(selected));
    }
}
