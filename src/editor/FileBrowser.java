package editor;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class FileBrowser {
    private JFileChooser browser;
    private String directory;

    public FileBrowser(String directory){
        browser = new JFileChooser();
        this.directory=directory;
    }
    public void openMap(){
        browser.setDialogTitle("Open a map file");
        browser.setCurrentDirectory(new File(directory));
        int result = browser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:

            break;
            case JFileChooser.CANCEL_OPTION:

            break;
            case JFileChooser.ERROR_OPTION:

            break;
        }
    }
    public void saveMap(){
        browser.setDialogTitle("Save a map file");
        browser.setCurrentDirectory(new File(directory));
    }
}
