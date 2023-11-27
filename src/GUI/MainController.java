package GUI;


import DLL.DllController;
import com.jfoenix.controls.JFXSlider;
// import com.sun.media.jfxmedia.MediaPlayer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
// import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.ArrayList;

public class MainController {
    public FontAwesomeIconView iPlay;
    public JFXSlider volume;
    public TableColumn col1;
    public TableColumn col2;
    public TableColumn col3;
    public TableColumn col4;
    public TableView songList;
    public Button playBtn, nextButton, previousButton;
    public ProgressBar songProgressBar;
    public Label songLabel;
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private boolean running;
    private Media media;
    private MediaPlayer mediaPlayer;

    Boolean playState = true;

    public MainController() {
        System.out.println("Project Started");

        // Run later when everything is being created
        Platform.runLater(this::PostInitialize);
    }


    public void playBtn(ActionEvent actionEvent) {
        String switcherIcon = (playState) ? "PAUSE" : "PLAY";
        double switcherTransform = (playState) ? -0.5 : 1;

        iPlay.setIcon(FontAwesomeIcon.valueOf(switcherIcon));
        iPlay.setTranslateX(switcherTransform);

        playState = !playState;

        System.out.println("Worked?");
    }


    public void PostInitialize() {

        // Volume Listener
        volume.valueProperty().addListener((observable, oldValue, newValue) -> {
            Number value = observable.getValue();
            System.out.println("Slider value: " + value);
        });


        // Disable Resort
        col1.setReorderable(false);
        col2.setReorderable(false);
        col3.setReorderable(false);
        col4.setReorderable(false);


        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("button"));

        DllController dllController = new DllController();

        songList.setItems(dllController.getSongs());

        //delete later --TESTING
        songs = new ArrayList<File>();
        directory = new File("resources/music");
        files = directory.listFiles();

        if(files != null){
            for(File file : files){
                songs.add(file);
                System.out.println(file);
            }
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        songLabel.setText(songs.get(songNumber).getName());

    }


    public void printSongs(ActionEvent actionEvent) {

    }

    public void previousMedia(ActionEvent actionEvent) {
    }

    public void playMedia() {

        mediaPlayer.play();
    }

    public void nextMedia(ActionEvent actionEvent) {
    }

    public void beginTimer() {      //timer for progress bar
    }
    public void cancelTimer() {
    }
}