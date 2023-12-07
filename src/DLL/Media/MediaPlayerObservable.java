package DLL.Media;

import APP_SETTINGS.AppConfig;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MediaPlayerObservable {
    private final Text songProgressNum;
    private final ProgressBar progressBar;
    private final Text progTextTotal;

    public MediaPlayerObservable(ProgressBar progBar, Text progText, Text progTextTotal) {
        this.progressBar = progBar;
        this.songProgressNum = progText;
        this.progTextTotal = progTextTotal;
    }

    public void update(Duration currentTime, Duration totalDuration) {
        this.progTextTotal.setText(AppConfig.getTimeFormat(totalDuration));
        this.songProgressNum.setText(AppConfig.getTimeFormat(currentTime));
        this.progressBar.setProgress(currentTime.toMillis() / totalDuration.toMillis());
    }
}
