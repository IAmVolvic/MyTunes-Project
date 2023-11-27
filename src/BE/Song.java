package BE;

import javafx.scene.control.Button;

public class Song {
    private int id=-1;
    private String name;

    private Button buttonAction;

    public Song(int id, String name, Button btn){
        this.id = id;
        this.name = name;
        this.buttonAction = btn;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public Button getButton() {
        return this.buttonAction;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setButton(Button button) {
        this.buttonAction = button;
    }
}
