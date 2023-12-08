package GUI.Components;


import BE.Song;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.stream.IntStream;

public class SongList {
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    private final TableView<Song> songList;
    private final TableColumn<String, Integer>  col1;
    private final TableColumn<String, String>   col2;
    private final TableColumn<String, String>   col3;
    private final TableColumn<String, Long>     col4;
    private final TableColumn<String, Button>   col5;

    public SongList(TableView<Song> sl, TableColumn<String, Integer> coL1, TableColumn<String, String> coL2, TableColumn<String, String> coL3, TableColumn<String, Long> coL4, TableColumn<String, Button> coL5){
        songList = sl;
        col1 = coL1;
        col2 = coL2;
        col3 = coL3;
        col4 = coL4;
        col5 = coL5;

        initialize();
    }


    private void initialize() {
        // Disable Resort
        col1.setReorderable(false);
        col2.setReorderable(false);
        col3.setReorderable(false);
        col4.setReorderable(false);
        col5.setReorderable(false);

        // Set the tables cell value
        col1.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("date"));
        col4.setCellValueFactory(new PropertyValueFactory<>("duration"));

        col5.setCellValueFactory(new PropertyValueFactory<>("editButton"));

        //Resize the list
        songList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        songList.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();


            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    int index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });


            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != (Integer) db.getContent(SERIALIZED_MIME_TYPE)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });


            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    Song draggedSong = (Song) songList.getItems().remove(draggedIndex);

                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = songList.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    songList.getItems().add(dropIndex, draggedSong);
                    IntStream.range(0, songList.getItems().size()).forEach(idx -> {
                        songList.getItems().get(idx).setTableId(idx + 1);
                    });

                    event.setDropCompleted(true);
                    songList.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row;
        });
    }

    public void addSong(ObservableList<Song> newSong) {
        songList.setItems(newSong);
    }

}
