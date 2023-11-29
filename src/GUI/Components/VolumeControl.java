package GUI.Components;

import DLL.DllController;
import com.jfoenix.controls.JFXSlider;

public class VolumeControl {
    private final JFXSlider volumeControl;

    // Controller
    private final DllController dllController;

    public VolumeControl(JFXSlider volume, DllController dllController){
        volumeControl = volume;
        this.dllController = dllController;
    }


    public void initialize() {
        volumeControl.valueProperty().addListener(e -> {
            dllController.SetVolume(volumeControl.getValue()/100);
        });
    }
}
