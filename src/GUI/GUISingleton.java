/**
 * This class represents the GUI singleton, responsible for handling all states for the GUI only.
 *
 * Methods:
 * - getInstance
 * - getBllController
 * - getPlaylistController
 * - getModalController
 */
package GUI;

import BLL.BllController;
import GUI.Components.Modal.ModalController;

public class GUISingleton {
    // Single instance of GUISingleton
    private static final GUISingleton instance = new GUISingleton();

    // Controllers
    private final BllController bllController;
    private PlaylistController playlistController;
    private final ModalController modalController;

    // Private constructor to prevent instantiation from outside
    private GUISingleton() {
        // Initialize controllers
        bllController = new BllController();
        modalController = new ModalController();
    }

    // Get the singleton instance
    public static GUISingleton getInstance() {
        return instance;
    }

    // Getters

    /**
     * Gets the BllController instance.
     *
     * @return BllController instance
     */
    public BllController getBllController() {
        return bllController;
    }

    /**
     * Gets the PlaylistController instance, preventing circular dependency.
     *
     * @return PlaylistController instance
     */
    public PlaylistController getPlaylistController() {
        if (playlistController == null) {
            playlistController = new PlaylistController(this);
        }
        return playlistController;
    }

    /**
     * Gets the ModalController instance.
     *
     * @return ModalController instance
     */
    public ModalController getModalController() {
        return modalController;
    }
}