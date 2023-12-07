package GUI.Components;


import BE.Song;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SongList {
    private TableView songList;
    private final TableColumn<String, Integer>  col1;
    private final TableColumn<String, String>   col2;
    private final TableColumn<String, String>   col3;
    private final TableColumn<String, Long>     col4;


    public SongList(TableView sl, TableColumn<String, Integer> coL1, TableColumn<String, String> coL2, TableColumn<String, String> coL3, TableColumn<String, Long> coL4){
        songList = sl;
        col1 = coL1;
        col2 = coL2;
        col3 = coL3;
        col4 = coL4;

        initialize();
    }


    private void initialize() {
        // Disable Resort
        col1.setReorderable(false);
        col2.setReorderable(false);
        col3.setReorderable(false);
        col4.setReorderable(false);


        // Set the tables cell value
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("date"));
        col4.setCellValueFactory(new PropertyValueFactory<>("duration"));

        //Resize the list
        songList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void addSong(ObservableList<Song> newSong) {
        songList.setItems(newSong);
    }

}
