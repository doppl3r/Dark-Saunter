package editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class FileBrowser {
    private JFileChooser browser;
    private String directory;
    private String mapName;
    private String imageName;
    private boolean exitAfterSave;
    private boolean saved;
    FileNameExtensionFilter imageFormats;
    FileNameExtensionFilter fileFormats;

    public FileBrowser(String directory){
        saved = true;
        browser = new JFileChooser();
        this.directory=directory;
        mapName="";
        imageName="null";
        imageFormats =
            new FileNameExtensionFilter("Image Files '.jpg', '.png', '.gif'","jpg","png","gif");
        fileFormats = new FileNameExtensionFilter("Map Files '.txt'","txt");
    }
    public void openMap(){
        browser.setSelectedFile(new File(mapName));
        browser.setDialogTitle("Open a map file");
        browser.setCurrentDirectory(new File(directory));
        browser.setFileFilter(fileFormats);
        int result = browser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                mapName = browser.getSelectedFile().toString();
                convertFileToMap(browser.getSelectedFile());
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
        browser.setFileFilter(fileFormats);
        int actionDialog = browser.showSaveDialog(null);
        switch (actionDialog) {
            case JFileChooser.APPROVE_OPTION:
                if (browser.getSelectedFile().exists()){
                    int actionDialog2 = JOptionPane.showConfirmDialog(null,"Replace existing file?");
                    if (actionDialog2 == JOptionPane.NO_OPTION){ saveMap(); }
                    else if (actionDialog2 == JOptionPane.YES_OPTION){
                        mapName = browser.getSelectedFile().toString();
                        quickSave(true);
                    }
                    else if (actionDialog2 == JOptionPane.CANCEL_OPTION){ }
                }
                else{
                    mapName = browser.getSelectedFile().toString();
                    quickSave(true);
                }
                if (exitAfterSave) System.exit(0);
            break;
            case JFileChooser.CANCEL_OPTION:

            break;
            case JFileChooser.ERROR_OPTION:

            break;
        }
    }
    public void changeTexture(boolean force){
        //System.out.println(imageName);
        if (!imageName.equals("null") || force){
            try {
                //System.out.println("hey");
                BufferedImage img = ImageIO.read(browser.getSelectedFile());
                EditorWindow.panel.texture.setImage(img);
                EditorWindow.panel.texture.updateLayout();
                imageName = browser.getSelectedFile().toString();
            }
            catch(IOException e1){}
        }
        else {
            EditorWindow.panel.texture.setImage(EditorWindow.tt.defaultTexture);
            EditorWindow.panel.texture.updateLayout();
            imageName = browser.getSelectedFile().toString();
            changeTexture(true); //recursive!
        }
    }
    public void importTexture(){
        browser.setSelectedFile(new File(imageName));
        browser.setDialogTitle("Import a texture image");
        browser.setCurrentDirectory(new File(directory));
        browser.setFileFilter(imageFormats);
        int result = browser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION: changeTexture(false); break;
            case JFileChooser.CANCEL_OPTION: break;
            case JFileChooser.ERROR_OPTION: break;
        }
    }
    public void newMap(){
        int actionDialog = JOptionPane.showConfirmDialog(null,"Create new map without saving?");
        if (actionDialog == JOptionPane.NO_OPTION){ }
        else if (actionDialog == JOptionPane.YES_OPTION){
            EditorWindow.panel.editor.resetMap();
            EditorWindow.panel.texture.setImage(EditorWindow.tt.defaultTexture);
            EditorWindow.panel.texture.updateLayout(4,4);
            EditorWindow.panel.editor.setTileID(1);
            EditorWindow.panel.gui.textureBox.setTileID(1);
            imageName="null";
        }
        else if (actionDialog == JOptionPane.CANCEL_OPTION){ }
    }
    public void deleteMap(){
        int actionDialog = JOptionPane.showConfirmDialog(null,"Clear map without saving?");
        if (actionDialog == JOptionPane.NO_OPTION){ }
        else if (actionDialog == JOptionPane.YES_OPTION){ EditorWindow.panel.editor.clearMap(); }
        else if (actionDialog == JOptionPane.CANCEL_OPTION){ }
    }
    public void exit(){
        if (!saved){
            int actionDialog = JOptionPane.showConfirmDialog(null,"Would you like to save first?");
            if (actionDialog == JOptionPane.NO_OPTION){ System.exit(0); }
            else if (actionDialog == JOptionPane.YES_OPTION){ exitAfterSave = true; quickSave(false);  }
            else if (actionDialog == JOptionPane.CANCEL_OPTION){ }
        }
        else System.exit(0);
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
            EditorWindow.panel.setGlobalID(1);
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
            int rows = (int)Double.parseDouble(field2.getText());
            int cols = (int)Double.parseDouble(field1.getText());
            if (rows > 999) rows = 999;
            if (cols > 999) cols = 999;
            EditorWindow.panel.editor.setRowsAndCols(rows,cols);
        } else { }
    }
    public void convertFileToMap(File file){
        LinkedList<String> stack = new LinkedList<String>();
        Scanner scan = null;
        int rows = 0;
        int cols = 0;
        boolean countRows = true;
        boolean errorMessage = false;
        //read file content
        try { scan = new Scanner(new FileReader(file)); }
        catch(IOException e1){}
        while (scan.hasNextLine()){
            stack.add(scan.nextLine());
            if (countRows &&
                stack.get(stack.size()-1).length() >= 2){ //= a number
                if (stack.get(stack.size()-1).substring(0,2).matches("-?\\d+")) rows++;
                //else errorMessage = true;
            }
            else countRows = false;
        }
        //initialize parameters
        if (!errorMessage && rows > 0){ cols = stack.get(0).length()/2; //parse according to 0-99
            //contruct new array;
            EditorWindow.panel.editor.setNewMap(cols, rows);
            int id = 0;
            String stringNum;
            for (int row = 0; row < rows; row++){
                for (int col = 0; col < cols; col++){
                    if (stack.get(row).length() == cols*2){
                        stringNum = stack.get(row).substring(col*2, (col*2)+2);
                        if (stringNum.matches("-?\\d+")){
                            id = Integer.parseInt(stringNum);
                            EditorWindow.panel.editor.setTileAt(row,col,id);

                        } else errorMessage = true;
                    } else errorMessage = true;
                }
            }
            EditorWindow.panel.editor.zoomFit(false);
        }
        if (errorMessage) JOptionPane.showMessageDialog(null, "Error: Corrupt or invalid file!");
        //set up texture properties
        imageName = stack.get(rows).substring(14);
        if (imageName.indexOf(".") < 0) imageName = "null";
        browser.setSelectedFile(new File(imageName));
        EditorWindow.panel.texture.updateLayout(
            Integer.parseInt(stack.get(rows).substring(8,10)),
            Integer.parseInt(stack.get(rows).substring(10,12)));
        EditorWindow.panel.editor.setTileID(1);
        EditorWindow.panel.gui.textureBox.setTileID(1);
        changeTexture(false);
    }
    public void convertMapToFile(){
        String mapString = EditorWindow.panel.editor.getTileBuffer().getMap().mapToRawString();
        String path = "null";
        browser.setSelectedFile(new File(imageName));
        if (imageName.indexOf(".") >= 0){
            path = browser.getSelectedFile().getPath();
        }
        mapString+="texture["+EditorWindow.panel.texture.framesToString()+"]="+path+  //path info
        System.getProperty("line.separator")+System.getProperty("line.separator")+    //space
        EditorWindow.panel.editor.getTileBuffer().getMap().mapToCPlusPlusString()+    //c++ sample
        System.getProperty("line.separator")+System.getProperty("line.separator")+    //space
        EditorWindow.panel.editor.getTileBuffer().getMap().mapToJavaString();         //java sample
        BufferedWriter writer = null;
        try {
            if (mapName.indexOf(".txt")<0) mapName+=".txt";
            writer = new BufferedWriter(new FileWriter(mapName));
            writer.write(mapString);
            saved = true;
        }
        catch ( IOException e) { }
        finally {
            try { if ( writer != null) writer.close( ); }
            catch ( IOException e){}
        }
    }
    public void quickSave(boolean force){
        if (mapName.length()>0 || force){
            convertMapToFile();
            if (exitAfterSave) System.exit(0);
        }
        else saveMap();
    }
    public boolean isSaved(){ return saved; }
    public void setSavedState(boolean saved){ this.saved=saved; }
}
