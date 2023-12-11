package BE;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;

public class Song {
    private int tableId;
    private int id;
    private String name;
    private String date;
    private String genre;

    private String duration;

    public Song(int id, String name, String date, String duration){
        this.id = id;
        this.name = name;
        this.date = date;
        this.duration = duration;
    }

    public int getTableId() {
        return this.tableId;
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


    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String newDuration) { this.duration = newDuration;}

    public void setTableId(int newId) {
        this.tableId = newId;
    }


    public void setName(String newName) {
        this.name = newName;
    }
}
