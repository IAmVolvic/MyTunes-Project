package DAL.Logic;

import BE.Song;
import DAL.ConnectionManager;
import javafx.scene.control.Button;

import java.sql.*;

import java.util.ArrayList;

public class MySongsController {
    private final ConnectionManager cm = new ConnectionManager();


    public ArrayList<Song> getAllSongs() {

        ArrayList<Song> songList = new ArrayList<>();

        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM songs WHERE playlist_id=? ";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id              = rs.getInt("song_id");
                String name         = rs.getString("song_name");
                Button button       = new Button("Bob");
                button.getStyleClass().add("playlist");

                Song song = new Song(id, name, button);
                songList.add(song);
            }

            return songList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
