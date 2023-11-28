package GUI.Components;

import DLL.DllController;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.event.ActionEvent;


public class PlayButton {
    public FontAwesomeIconView iPlay;
    private Boolean playState = true;

    // Controller
    private final DllController dllController = new DllController();

    //Constructor
    public PlayButton(FontAwesomeIconView Icon){
        iPlay   = Icon;
    }


    public void PlayButtonClicked(ActionEvent actionEvent){
        System.out.println(dllController.getSongName("Test.mp3"));

        changeIcon();
        playSong();

        playState = !playState;
    }


    private void playSong(){
        dllController.PlaySong();
    }

    private void changeIcon(){
        String switcherIcon = (playState) ? "PAUSE" : "PLAY";
        double switcherTransform = (playState) ? -0.5 : 1;

        iPlay.setIcon(FontAwesomeIcon.valueOf(switcherIcon));
        iPlay.setTranslateX(switcherTransform);
    }

}
