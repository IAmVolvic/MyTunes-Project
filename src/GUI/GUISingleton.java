package GUI;

//This is the GUI singleton
//this will handle all states for the GUI ONLY


import BLL.DllController;
import GUI.Components.Modal.ModalController;

public class GUISingleton {
    // Single instance of GUISingleton
    private static final GUISingleton instance = new GUISingleton();

    // Controllers
    private final DllController dllController;
    private PlaylistController playlistController;
    private final ModalController modalController;


    // Private constructor to prevent instantiation from outside
    private GUISingleton() {
        // Initialize controllers
        dllController = new DllController();
        modalController = new ModalController();
    }



    // Getters
    public DllController getDllController() {
        return dllController;
    }


    // This prevents a circular dependency.
    public PlaylistController getPlaylistController() {
        if (playlistController == null) {
            playlistController = new PlaylistController(this);
        }
        return playlistController;
    }


    public ModalController getModalController() {
        return modalController;
    }


    // Get the singleton instance
    public static GUISingleton getInstance() {
        return instance;
    }
}