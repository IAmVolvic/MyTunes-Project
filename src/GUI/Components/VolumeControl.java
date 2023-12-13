package GUI.Components;

import GUI.GUISingleton;
import com.jfoenix.controls.JFXSlider;

public class VolumeControl {
    // GUI SINGLETON
    private final GUISingleton single = GUISingleton.getInstance();

    private final JFXSlider volumeControl;

    public VolumeControl(JFXSlider volume){
        volumeControl = volume;
    }


    public void initialize() {
        volumeControl.valueProperty().addListener(e -> {
            single.getDllController().setVolume(volumeControl.getValue()/100);
        });
    }
}
