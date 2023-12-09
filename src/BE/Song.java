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

    private long duration;

    private Button editButton = new Button();

    public Song(int id, String name, String date, long duration){
        this.id = id;
        this.name = name;
        this.date = date;
        this.duration = duration;

        editButton.getStyleClass().add("editButton");
        editButton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT, "18"));
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

    public Button getEditButton() {
        return this.editButton;
    }



    public long getDuration() {
        return this.duration;
    }

    public void setEditButton(Button newButton) {
        this.editButton = newButton;
    }

    public void setTableId(int newId) {
        this.tableId = newId;
    }
}
