package DAL.Logic;

import BE.Playlist;
import DAL.ConnectionManager;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    public void createPlaylist(String playlistTitle) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO playlists(name, created_date) VALUES (?, ?)";
            PreparedStatement pt = con.prepareStatement(sql);
            pt.setString(1, playlistTitle);
            pt.setString(2, dateFormat.format(cal.getTime()));
            pt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
