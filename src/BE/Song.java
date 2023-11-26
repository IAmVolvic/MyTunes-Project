package BE;

public class Song {
    private int id=-1;
    private String name;


    public Song(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
