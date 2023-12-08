package GUI.Components;

import DLL.DllController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.event.ActionEvent;


public class MediaButtons {
    // Play Button
    public FontAwesomeIconView iPlay;
    private Boolean playState = false;

    //Controller
    private final DllController dllController;


    //Constructor
    public MediaButtons(FontAwesomeIconView Icon, DllController dllController){
        iPlay   = Icon;
        this.dllController = dllController;
    }


    // Play button methods
    public void playButtonClicked(ActionEvent actionEvent){
        playSong();
    }

    public void resetIcon(){
        changeIcon();
    }

    private void playSong(){
        if (playState){
            dllController.PauseSong();
        }else{
            dllController.PlaySong();
        }

        playState = !playState;
        changeIcon();
    }

    
    private void changeIcon(){
        String switcherIcon = (playState) ? "PAUSE" : "PLAY";
        double switcherTransform = (playState) ? -0.5 : 1;

        iPlay.setIcon(FontAwesomeIcon.valueOf(switcherIcon));
        iPlay.setTranslateX(switcherTransform);
    }


    // Skip methods
    public void skipSong(ActionEvent actionEvent){
        dllController.skipSong();
    }

    // Prev methods
    public void prevSong(ActionEvent actionEvent){
        dllController.prevSong();
    }
}
