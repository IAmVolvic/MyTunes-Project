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

    public Playlist createPlaylist(String playlistTitle) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO playlists(name, created_date) VALUES (?, ?)";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1, playlistTitle);
            pt.setString(2, dateFormat.format(cal.getTime()));
            int affectedRows = pt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating playlist failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Playlist(playlistTitle, generatedKeys.getInt(1), dateFormat.format(cal.getTime()));
                }
                else {
                    throw new SQLException("Creating playlist failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
