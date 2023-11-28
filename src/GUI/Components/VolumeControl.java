package GUI.Components;

import DLL.DllController;
import com.jfoenix.controls.JFXSlider;

public class VolumeControl {
    private final JFXSlider volumeControl;

    // Controller
    private final DllController dllController = new DllController();


    public VolumeControl(JFXSlider volume){
        volumeControl = volume;
    }


    public void Initialize() {
        volumeControl.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            System.out.println("Slider value: " + value/100);

            dllController.SetVolume(value/100);
        });
    }
}
