package my.tunesProject.gui;

import my.tunesProject.bll.BLLManager;
import my.tunesProject.exceptions.PetShopException;
import my.tunesProject.be.Person;
import my.tunesProject.be.Pet;
import my.tunesProject.dal.IPersonDAO;
import my.tunesProject.dal.IPetDAO;
import my.tunesProject.dal.PersonDAO;
import my.tunesProject.dal.PetDAO;

public class MainUI {
    public void run(){

        IPersonDAO mgr = new PersonDAO();
        IPetDAO petDAO = new PetDAO();

        for(Person p: mgr.getAllPersons()){
            System.out.println("Person: " + p);
        }
        try {
            for(Pet p: petDAO.getPetsFromOwner(3)){
                System.out.println("Pet: " + p);
            }
        } catch (PetShopException e) {
            System.err.println("You have a problem: " + e.getMessage());
        }

        BLLManager bll = new BLLManager();

        try {
            bll.createPerson(new Person("Jeppe", "invalidmail"));
        } catch (PetShopException e) {
            System.err.println("There is a problem: " +  e.getMessage());
        }


        //Person per = new Person(5,"Henrik the nice guy", "hk3st@easv.dk");
        //mgr.createPerson(per);
        //mgr.updatePerson(per);
        //mgr.deletePerson(6);
        Person p = mgr.getPerson(5);
        System.out.println("Person no 5: " + p);
        /*
        System.out.println("...");
        System.out.println("...");
        System.out.println("...");
        for(Person p: mgr.getAllPersons()){
            System.out.println("Person: " + p);
        }
        */

        System.out.println("Done...");
    }
}
