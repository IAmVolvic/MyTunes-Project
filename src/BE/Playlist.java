package BE;

public class Playlist {
    private String playlistName;
    private int playlistId;
    private String playlistDate;
    public Playlist(String name, int id, String date) {
        playlistName = name;
        playlistId = id;
        playlistDate = date;
    }
    public String playlistName(){
        return this.playlistName;
    }
    public int playlistId(){
        return this.playlistId;
    }
}
