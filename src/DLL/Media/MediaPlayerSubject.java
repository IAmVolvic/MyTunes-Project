package DLL.Media;

import java.util.ArrayList;
import java.util.List;

public class MediaPlayerSubject {
    private final List<MediaPlayerObservable> observers;

    public MediaPlayerSubject() {
        this.observers = new ArrayList<>();
    }

    public void registerObserver(MediaPlayerObservable observable) {
        this.observers.add(observable);
    }

    public void removeObserver(MediaPlayerObservable observable) {
        this.observers.remove(observable);
    }

    public void update(double progress) {
        this.observers.forEach((observable -> {
            observable.update(progress);
        }));
    }
}
