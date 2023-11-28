package GUI.Components;


import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class SongList {
    private final TableColumn<String, Integer>  col1;
    private final TableColumn<String, String>   col2;
    private final TableColumn<String, Button>   col3;
    private final TableColumn<String, String>   col4;


    public SongList(TableColumn<String, Integer> coL1, TableColumn<String, String> coL2, TableColumn<String, Button> coL3, TableColumn<String, String> coL4){
        col1 = coL1;
        col2 = coL2;
        col3 = coL3;
        col4 = coL4;
    }


    public void Initialize() {
        // Disable Resort
        col1.setReorderable(false);
        col2.setReorderable(false);
        col3.setReorderable(false);
        col4.setReorderable(false);


        // Set the tables cell value
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("button"));
    }
}
