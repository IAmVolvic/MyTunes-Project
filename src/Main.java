import APP_SETTINGS.AppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    public static void main(String[] args){
        AppConfig.setPlaylistPath("resources/Playlists/");
        Application.launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));

        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 1280 , 720));
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);

        primaryStage.setTitle("ITunes Project");

        primaryStage.getIcons().addAll(
                new Image("/AppIcons/med.png"),
                new Image("/AppIcons/low.png")
        );

        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
