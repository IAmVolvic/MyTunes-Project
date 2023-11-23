package my.tunesProject.be;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id=-1;
    private String name;
    private String email;
    private final List<Pet> pets = new ArrayList<>();

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Person(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        this.email = null;
    }

    public List<Pet> getPets(){
        return pets;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
