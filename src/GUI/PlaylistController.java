package GUI;

import GUI.Components.FXMLCustom.PlaylistButton;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class PlaylistController {
    private int selectedPlaylistId;

    //Extra
    private VBox playlist_list;

    public void setParentNode(VBox pl){
        playlist_list = pl;
    }

    public void setPlaylistView(int playlistID, PlaylistButton plBtn) {
        if(selectedPlaylistId != playlistID){
            selectedPlaylistId = playlistID;

            ObservableList<Node> children = playlist_list.getChildren();
            for (Node val : children) {
                val.getStyleClass().remove("playlist-active");
            }

            plBtn.toggleActive();
        }
    }
}
