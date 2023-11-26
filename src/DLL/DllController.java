package DLL;

import DAL.Logic.UseGetMySongs;
import BE.Song;
import java.util.ArrayList;

public class DllController {
    public DllController() {}

    public void getSongs() {
        UseGetMySongs songManager = new UseGetMySongs();
        ArrayList<Song> songList = songManager.getAllSongs();

        for (Song song : songList) {
            System.out.println(song);
        }
    }
}
