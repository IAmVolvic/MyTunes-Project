package APP_SETTINGS;

import javafx.util.Duration;

public class AppConfig {
    private static String playlistPath;

    public static String getPlaylistPath() {
        return playlistPath;
    }

    public static void setPlaylistPath(String path) {
        playlistPath = path;
    }

    public static String getTimeFormat(Duration currentTime){
        int songInMins = (int) Math.floor(currentTime.toMinutes());
        int songInSec = (int) Math.floor(currentTime.toSeconds());

        return songInMins + ":" + String.format("%02d", songInSec % 60);
    }


    public static String getPlaylistTotalSongs(int totalSongs){
        return "Playlist - " + totalSongs + " songs";
    }
}