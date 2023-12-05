package APP_SETTINGS;

public class AppConfig {
    private static String playlistPath;

    public static String getPlaylistPath() {
        return playlistPath;
    }

    public static void setPlaylistPath(String path) {
        playlistPath = path;
    }
}