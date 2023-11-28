package GUI.Components;

import com.jfoenix.controls.JFXSlider;

public class VolumeControl {
    private final JFXSlider volumeControl;

    public VolumeControl(JFXSlider volume){
        volumeControl = volume;
    }


    public void Initialize() {
        volumeControl.valueProperty().addListener((observable, oldValue, newValue) -> {
            Number value = observable.getValue();
            System.out.println("Slider value: " + value);
        });
    }
}
