package my.tunesProject.bll;

import my.tunesProject.exceptions.PetShopException;
import my.tunesProject.be.Person;
import my.tunesProject.be.Pet;
import my.tunesProject.dal.IPersonDAO;
import my.tunesProject.dal.IPetDAO;
import my.tunesProject.dal.PersonDAO;
import my.tunesProject.dal.PetDAO;

import java.util.List;

public class BLLManager {
    IPersonDAO personDAO = new PersonDAO();
    IPetDAO petDAO = new PetDAO();
    public void createPerson(Person p) throws PetShopException {
        if(!isEmailValid(p.getEmail()))
            throw new PetShopException("Email is not valid: " + p.getEmail());
        personDAO.createPerson(p);
    }

    private boolean isEmailValid(String email){
        return (email.contains("@"));
    }

    public List<Person> getAllPersons() {
        return personDAO.getAllPersons();
    }

    public List<Pet> getPetsFromOwner(Person selected) throws PetShopException {
        return petDAO.getPetsFromOwner(selected.getId());
    }
}
