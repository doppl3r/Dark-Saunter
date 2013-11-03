package editor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class FileBrowser {
    private JFileChooser browser;
    private String directory;
    private boolean exitAfterSave;

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
        browser.setDialogTitle("Save your map file");
        browser.setCurrentDirectory(new File(directory));
        int actionDialog = browser.showSaveDialog(null);
        switch (actionDialog) {
            case JFileChooser.APPROVE_OPTION:
                //ds.save(level, blockSize, rows, cols, map);
                //ds.setFile(fc.getSelectedFile()+"");
            break;
            case JFileChooser.CANCEL_OPTION:

            break;
            case JFileChooser.ERROR_OPTION:

            break;
        }
        if (exitAfterSave) EditorWindow.exit();
    }
    public void newMap(){
        int actionDialog = JOptionPane.showConfirmDialog(null,"Create new map without saving?");
        if (actionDialog == JOptionPane.NO_OPTION){ }
        else if (actionDialog == JOptionPane.YES_OPTION){ EditorWindow.panel.editor.resetMap(); }
        else if (actionDialog == JOptionPane.CANCEL_OPTION){ }
    }
    public void deleteMap(){
        int actionDialog = JOptionPane.showConfirmDialog(null,"Clear map without saving?");
        if (actionDialog == JOptionPane.NO_OPTION){ }
        else if (actionDialog == JOptionPane.YES_OPTION){ EditorWindow.panel.editor.clearMap(); }
        else if (actionDialog == JOptionPane.CANCEL_OPTION){ }
    }
    public void exit(){
        int actionDialog = JOptionPane.showConfirmDialog(null,"Would you like to save first?");
        if (actionDialog == JOptionPane.NO_OPTION){ EditorWindow.exit(); }
        else if (actionDialog == JOptionPane.YES_OPTION){ saveMap(); exitAfterSave = true; }
        else if (actionDialog == JOptionPane.CANCEL_OPTION){ }
    }
    public void display() {
        JTextField field1 = new JTextField("4");
        JTextField field2 = new JTextField("4");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Rows: "));
        panel.add(field1);
        panel.add(new JLabel("Columns: "));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Adjust Texture Dimension",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println(" " + field1.getText()
                    + " " + field2.getText());
        } else {
            System.out.println("Cancelled");
        }
    }
}
