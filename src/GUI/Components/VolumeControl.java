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


    public void Initialize() {
        volumeControl.valueProperty().addListener(e -> {
            //Debug
            System.out.println(volumeControl.getValue());

            dllController.SetVolume(volumeControl.getValue()/100);
        });
    }
}
