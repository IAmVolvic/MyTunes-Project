package GUI.Components;

import DLL.DllController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.event.ActionEvent;


public class PlayButton {
    public FontAwesomeIconView iPlay;
    private Boolean playState = false;

    private final DllController dllController;

    //Constructor
    public PlayButton(FontAwesomeIconView Icon, DllController dllController){
        iPlay   = Icon;
        this.dllController = dllController;
    }


    public void PlayButtonClicked(ActionEvent actionEvent){
        playSong();
    }


    private void playSong(){
        if (playState){
            dllController.PauseSong();
        }else{
            dllController.PlaySong();
        }
        changeIcon();

        playState = !playState;
    }


    private void changeIcon(){
        String switcherIcon = (!playState) ? "PAUSE" : "PLAY";
        double switcherTransform = (!playState) ? -0.5 : 1;

        iPlay.setIcon(FontAwesomeIcon.valueOf(switcherIcon));
        iPlay.setTranslateX(switcherTransform);
    }
}
