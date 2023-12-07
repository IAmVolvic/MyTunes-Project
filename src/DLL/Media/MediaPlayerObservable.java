package DLL.Media;

import javafx.scene.control.ProgressBar;

public class MediaPlayerObservable {
    private final ProgressBar progressBar;

    public MediaPlayerObservable(ProgressBar progBar) {
        this.progressBar = progBar;
    }

    public void update(double progress) {
        this.progressBar.setProgress(progress);
    }
}
