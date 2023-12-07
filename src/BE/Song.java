package BE;

import javafx.scene.control.Button;

import java.util.Date;

public class Song {
    private int id;
    private String name;
    private String date;
    private String genre;

    private long duration;

    private Button editButton;

    public Song(int id, String name, String date, long duration){
        this.id = id;
        this.name = name;
        this.date = date;
        this.duration = duration;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public Button getEditButton() {
        return this.editButton;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setEditButton(Button newButton) {
        this.editButton = newButton;
    }
}
