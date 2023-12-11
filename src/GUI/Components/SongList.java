package GUI.Components;


import BE.Song;
import GUI.Components.Modal.ModalConfigs.SongModal.SongModal.DeleteSongModalView;
import GUI.GUISingleton;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

import java.util.stream.IntStream;

public class SongList {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    private final TableView<Song> songList;
    private final TableColumn<String, Integer>  col1;
    private final TableColumn<String, String>   col2;
    private final TableColumn<String, String>   col3;
    private final TableColumn<String, Long>     col4;


    public SongList(TableView<Song> sl, TableColumn<String, Integer> coL1, TableColumn<String, String> coL2, TableColumn<String, String> coL3, TableColumn<String, Long> coL4){
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
        col1.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("date"));
        col4.setCellValueFactory(new PropertyValueFactory<>("duration"));

        //Resize the list
        songList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        songList.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();

            createContextMenu(row);

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


    public void clearTable(){ songList.getItems().clear(); }



    private void createContextMenu(TableRow<Song> row) {
        // Create Sub buttons
        MenuItem editButton = new MenuItem("Edit");
        SeparatorMenuItem spacer = new SeparatorMenuItem();
        MenuItem deleteButton = new MenuItem("Delete");

        editButton.getStyleClass().add("customContext-Btn");
        deleteButton.getStyleClass().add("customContext-Btn-Danger");


        // Add event handlers
        editButton.setOnAction(event -> {
            Song selectedItem = row.getItem();
            System.out.println(selectedItem.getName());
        });

        deleteButton.setOnAction(event -> {
            Song selectedItem = row.getItem();

            DeleteSongModalView modalView = new DeleteSongModalView(selectedItem);
            single.getModalController().openModal(modalView.getView());
        });


        ContextMenu rowContextMenu = new ContextMenu(editButton, spacer, deleteButton);
        rowContextMenu.getStyleClass().add("customContext");


        // Set the context menu only for non-empty rows
        row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(rowContextMenu)
        );
    }
}
