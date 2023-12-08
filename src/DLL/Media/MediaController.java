package DLL.Media;

import APP_SETTINGS.AppConfig;
import BE.Playlist;
import DLL.DllController;
import DLL.FIle.FileController;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Base64;

public class MediaController {
    private MediaPlayer mPlayer;

    private boolean isPlaying = false;
    private double lastVolume = 0.01;
    private final MediaPlayerSubject mPlayerSubject;

    private Playlist currentPlaylist;
    private int currentSongIndex = 0;

    //Controller
    private final FileController fileController;

    //JFX Element
    private Label currentlyPlaying;

    //Constructor
    public MediaController(FileController fc) {
        fileController = fc;
        this.mPlayerSubject = new MediaPlayerSubject();
    }


    //Basic Media Controls
    public void setLabel(Label currentlyPlayingLabel){ currentlyPlaying = currentlyPlayingLabel; }


    public void playSong() {
        if(mPlayer == null){ return; }

        isPlaying = true;

        mPlayer.setVolume(lastVolume);
        mPlayer.play();


        mPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if(mPlayer == null){ return; }
            this.mPlayerSubject.update(newValue, mPlayer.getTotalDuration());
        });

        mPlayer.setOnEndOfMedia(() -> {
            currentSongIndex = (currentSongIndex + 1) % currentPlaylist.getSongTable().size();
            setSong();
            playSong();
        });
    }


    public void pauseSong() {
        if(mPlayer == null){ return; }
        mPlayer.pause();
        isPlaying = false;
    }

    //Volume Control
    public void setVolume(Double newVolume) {
        if(mPlayer == null){ return; }
        mPlayer.setVolume(newVolume);
        lastVolume = newVolume;
    }

    //Skip / Prev
    public void skipSong() {
        currentSongIndex = (currentSongIndex + 1) % currentPlaylist.getSongTable().size();
        clearSong();
        setSong();

        if(isPlaying){
            playSong();
        }
    }

    public void prevSong() {
        int playlistSize = currentPlaylist.getSongTable().size();
        currentSongIndex = (currentSongIndex - 1 + playlistSize) % playlistSize;
        clearSong();
        setSong();

        if(isPlaying){
            playSong();
        }
    }



    //Load Song List
    public void setPlaylist(Playlist playList) {
        currentSongIndex = 0;
        currentPlaylist = playList;

        clearSong();
        setSong();
    }


    private void setSong() {
        if(currentPlaylist != null && !currentPlaylist.getSongTable().isEmpty()) {
            String decodeSong = Base64.getEncoder().encodeToString(currentPlaylist.getSongTable().get(currentSongIndex).getName().getBytes());
            String playlistPath = AppConfig.getPlaylistPath() + currentPlaylist.playlistId() + "_" + currentPlaylist.playlistName() + "/";
            File songPath = fileController.findFile(playlistPath, decodeSong);

            if(songPath != null){
                mPlayer = new MediaPlayer(new Media(songPath.toURI().toString()));
                currentlyPlaying.setText(currentPlaylist.getSongTable().get(currentSongIndex).getName());
            }
        }
    }


    private void clearSong() {
        if(mPlayer == null){ return; }
        mPlayer.stop();
        mPlayer = null;
        this.mPlayerSubject.update(new Duration(0), new Duration(0));
    }




    //Listeners
    public void bindProgressListener(MediaPlayerObservable listener) {
        this.mPlayerSubject.registerObserver(listener);
    }
}
