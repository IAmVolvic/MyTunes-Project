package BE;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Playlist {
    private String  playlistName;
    private int     playlistId;
    private String  playlistDate;

    private ArrayList<Song> songs = new ArrayList<>();

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

    public void addSongTable(ArrayList<Song> newSongTable){
        this.songs = newSongTable;
    }

    public ArrayList<Song> getSongTable(){
        return this.songs;
    }

    public void addSong(Song newSong){
        this.songs.add(newSong);
    }

    public void newName(String name){
        this.playlistName = name;
    }
}
