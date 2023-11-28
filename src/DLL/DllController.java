package DLL;

import java.io.File;
import DLL.Components.FileController;

public class DllController {
    FileController fileController = new FileController();

    public File getSongName(String songName){
        return fileController.getSong(songName);
    }
}
