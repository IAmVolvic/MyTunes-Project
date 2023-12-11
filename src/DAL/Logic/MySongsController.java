package DAL.Logic;


import BE.Song;
import DAL.ConnectionManager;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MySongsController {
    private final ConnectionManager cm = new ConnectionManager();


    public ArrayList<Song> getPlaylistSongs(int playlistId) {

        ArrayList<Song> songList = new ArrayList<>();

        try(Connection con = cm.getConnection())
        {
            String sql = "SELECT * FROM songs WHERE playlist_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, playlistId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id              = rs.getInt("song_id");
                String name         = rs.getString("song_name");
                String createdDate  = rs.getString("created_date");

                Song song = new Song(id, name, createdDate, 0);
                songList.add(song);
            }

            return songList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Song newSong(int playlistId, String songTitle) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        try(Connection con = cm.getConnection())
        {
            String sql = "INSERT INTO songs(playlist_id, song_name, created_date) VALUES (?, ?, ?)";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, playlistId);
            pt.setString(2, songTitle);
            pt.setString(3, dateFormat.format(cal.getTime()));

            int affectedRows = pt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating playlist failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Song(generatedKeys.getInt(1),songTitle, dateFormat.format(cal.getTime()), 0);
                }
                else {
                    throw new SQLException("Creating playlist failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSongFromPlaylistSingle(int songId){
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM songs WHERE song_id = ?";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, songId);
            pt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteSongFromPlaylistAll(int playlistId){
        try(Connection con = cm.getConnection())
        {
            String sql = "DELETE FROM songs WHERE playlist_id = ?";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setInt(1, playlistId);
            pt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void editSong(int songId, String newName){
        try(Connection con = cm.getConnection())
        {
            String sql = "UPDATE songs SET song_name = ? WHERE song_id = ?";
            PreparedStatement pt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.setString(1, newName);
            pt.setInt(2, songId);
            pt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}