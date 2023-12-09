package GUI.Components;

import DLL.DllController;

import GUI.GUISingleton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.event.ActionEvent;


public class MediaButtons {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();


    // Play Button
    public FontAwesomeIconView iPlay;
    private Boolean playState = false;


    //Constructor
    public MediaButtons(FontAwesomeIconView Icon){
        iPlay   = Icon;
    }


    // Play button methods
    public void playButtonClicked(ActionEvent actionEvent){
        playSong();
    }

    public void resetIcon(){
        playState = false;
        changeIcon();
    }

    private void playSong(){
        if (playState){
            single.getDllController().pauseSong();
        }else{
            single.getDllController().playSong();
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
        single.getDllController().skipSong();
    }

    // Prev methods
    public void prevSong(ActionEvent actionEvent){
        single.getDllController().prevSong();
    }
}
