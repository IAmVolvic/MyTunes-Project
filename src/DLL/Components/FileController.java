package DLL.Components;

import java.io.File;
public class FileController {
    File musicFolder = new File("resources/music");
    File[] listOfFiles = musicFolder.listFiles();


    private boolean isValid() {
        if (listOfFiles == null) {
            System.out.println("No files found in the specified directory: " + musicFolder.getAbsolutePath());
            return false;
        }
        return true;
    }

    public File getSong(String songName) {
        isValid();

        for (File file : listOfFiles) {
            if (file.getName().equals(songName)) {
                return file;
            }
        }

        System.out.println("Song not found");
        return null;
    }

}
