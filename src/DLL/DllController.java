package DLL;

import DAL.Logic.UseGetMySongs;
import BE.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DllController {
    public DllController() {}

    public ObservableList getSongs() {
        ArrayList songManager = new UseGetMySongs().getAllSongs();

        ObservableList<Song> songData = FXCollections.observableArrayList();

        for (Object song : songManager) {
            songData.add((Song) song);
        }
        return songData;
    }
}
