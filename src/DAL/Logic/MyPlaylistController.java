package DAL.Logic;

import BE.Playlist;
import BE.Song;
import DAL.ConnectionManager;
import javafx.scene.control.Button;

import java.sql.*;
import java.util.ArrayList;

public class MyPlaylistController {
    private final ConnectionManager cm = new ConnectionManager();



    public ArrayList<Playlist> getAllPlaylists() {

        ArrayList<Playlist> playlist = new ArrayList<>();

        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM playlists";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id              = rs.getInt("id");
                String name         = rs.getString("name");
                String date       = rs.getString("created_date");

                Playlist pl = new Playlist(name, id, date);
                playlist.add(pl);
            }

            return playlist;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
