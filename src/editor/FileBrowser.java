package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileBrowser {
    private JFileChooser browser;
    private String directory;
    private String mapName;
    private String imageName;
    private boolean exitAfterSave;

    public FileBrowser(String directory){
        browser = new JFileChooser();
        this.directory=directory;
        mapName=imageName="";
    }
    public void openMap(){
        browser.setSelectedFile(new File(mapName));
        browser.setDialogTitle("Open a map file");
        browser.setCurrentDirectory(new File(directory));
        browser.setFileFilter(new FileNameExtensionFilter("Map Files '.txt'","txt"));
        int result = browser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                mapName = browser.getSelectedFile().toString();
            break;
            case JFileChooser.CANCEL_OPTION:

            break;
            case JFileChooser.ERROR_OPTION:

            break;
        }
    }
    public void saveMap(){
        browser.setSelectedFile(new File(mapName));
        browser.setDialogTitle("Save your map file");
        browser.setCurrentDirectory(new File(directory));
        browser.setFileFilter(new FileNameExtensionFilter("Map Files '.txt'","txt"));
        int actionDialog = browser.showSaveDialog(null);
        switch (actionDialog) {
            case JFileChooser.APPROVE_OPTION:
                mapName = browser.getSelectedFile().toString();
                if (exitAfterSave) System.exit(0);
            break;
            case JFileChooser.CANCEL_OPTION:

            break;
            case JFileChooser.ERROR_OPTION:

            break;
        }
    }
    public void importTexture(){
        browser.setSelectedFile(new File(imageName));
        browser.setDialogTitle("Import a texture image");
        browser.setCurrentDirectory(new File(directory));
        browser.setFileFilter(new FileNameExtensionFilter("Image Files '.jpg', '.png', '.gif'","jpg","png","gif"));
        int result = browser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                try {
                    BufferedImage img = ImageIO.read(browser.getSelectedFile());
                    EditorWindow.panel.texture.setImage(img);
                    EditorWindow.panel.texture.updateLayout();
                    imageName = browser.getSelectedFile().toString();
                }
                catch(IOException e1){}
                break;
            case JFileChooser.CANCEL_OPTION:

                break;
            case JFileChooser.ERROR_OPTION:

                break;
        }
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
        if (actionDialog == JOptionPane.NO_OPTION){ System.exit(0); }
        else if (actionDialog == JOptionPane.YES_OPTION){ exitAfterSave = true; saveMap();  }
        else if (actionDialog == JOptionPane.CANCEL_OPTION){ }
    }
    public void changeTextureProperties() {
        JTextField field1 = new JTextField(EditorWindow.panel.texture.getHFrames()+"");
        JTextField field2 = new JTextField(EditorWindow.panel.texture.getVFrames()+"");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Columns (left to right): "));
        panel.add(field1);
        panel.add(new JLabel("Rows (top to bottom): "));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Adjust Texture Dimension",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            EditorWindow.panel.texture.updateLayout(
                (int)Double.parseDouble(field2.getText()),(int)Double.parseDouble(field1.getText()));
            EditorWindow.panel.editor.setTileID(1);
            EditorWindow.panel.gui.textureBox.setTileID(1);
        } else { }
    }
    public void changeArrayProperties(){
        JTextField field1 = new JTextField(EditorWindow.panel.editor.getTileBuffer().getMap().getCols()+"");
        JTextField field2 = new JTextField(EditorWindow.panel.editor.getTileBuffer().getMap().getRows()+"");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Columns (left to right): "));
        panel.add(field1);
        panel.add(new JLabel("Rows (top to bottom): "));
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Adjust 2D Map Dimension",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            EditorWindow.panel.editor.setRowsAndCols(
                (int)Double.parseDouble(field2.getText()),(int)Double.parseDouble(field1.getText()));
        } else { }
    }
}
