package editor;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class FileBrowser {
    private JFileChooser browser;
    private String directory;
    private String mapName;
    private String imageName;
    private boolean disableWarningMessage;
    private boolean exitAfterSave;
    private boolean saved;
    private FileNameExtensionFilter imageFormats;
    private FileNameExtensionFilter fileFormats;

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
        browser.setDialogTitle("Open Map File");
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
        browser.setDialogTitle("Save Map File");
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
        browser.setDialogTitle("Import a Texture Image");
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
        EditorWindow.panel.editor.resetMap();
        EditorWindow.panel.texture.setImage(EditorWindow.tt.defaultTexture);
        EditorWindow.panel.texture.updateLayout(8,8);
        EditorWindow.panel.editor.setTileID(1);
        EditorWindow.panel.gui.textureBox.setTileID(1);
        imageName="null";
    }
    public void deleteMap(){ EditorWindow.panel.editor.clearMap(); }
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
    public void changeArrayProperties(boolean clear){
        boolean go = true;
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
            if (rows*cols >= 998001){ //999*999 = pretty slow performance
                int i = JOptionPane.showConfirmDialog(null,
                    "This data may take longer than usual to construct!\n" +
                    "Are you sure you want to continue this process?\n",
                    "",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if (i == JOptionPane.NO_OPTION) go = false;
            }
            if (go){
                double t = System.currentTimeMillis();
                if (clear) EditorWindow.panel.editor.getTileBuffer().clearMap();
                EditorWindow.panel.editor.setRowsAndCols(rows,cols,clear);
                memoryWarning((System.currentTimeMillis()-t)/1000);
            }
        } else { }
    }
    public void memoryWarning(double time){
        if (time > 0.25){
            if (!disableWarningMessage){
                int i = JOptionPane.showConfirmDialog(null,
                        "Your map data is too large to properly backup!\n"+
                        "Would you like to turn off the backup system\n"+
                        "to increase your performance?\n\n"+
                        "Features such as 'undo' and 'redo' will no\n" +
                        "longer be available for this session!\n\n"+
                        "Lag: "+time+" seconds\n",
                        "",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (i == JOptionPane.YES_OPTION){
                    disableWarningMessage = true;
                    EditorWindow.panel.editor.setEnableHistory(false);
                }
                else if (i == JOptionPane.NO_OPTION) disableWarningMessage = true;
                else memoryWarning(time);
            }
        }
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
        StringBuffer newMap = new StringBuffer();
        newMap.append(EditorWindow.panel.editor.getTileBuffer().getMap().mapToRawString());
        //String mapString = EditorWindow.panel.editor.getTileBuffer().getMap().mapToRawString();
        String path = "null";
        browser.setSelectedFile(new File(imageName));
        if (imageName.indexOf(".") >= 0){
            path = browser.getSelectedFile().getPath();
        }
        newMap.append("texture["+EditorWindow.panel.texture.framesToString()+"]="+path);
        BufferedWriter writer = null;
        try {
            if (mapName.indexOf(".txt")<0) mapName+=".txt";
            writer = new BufferedWriter(new FileWriter(mapName));
            writer.write(newMap.toString());
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
    public void launchCopyPaste(){
        //formats
        final String[] formats = new String[]{"Java","C++","ActionScript"};
        final String[] compiler = new String[]{
            EditorWindow.panel.editor.getTileBuffer().getMap().mapToJavaString(),
            EditorWindow.panel.editor.getTileBuffer().getMap().mapToCPlusPlusString(),
            EditorWindow.panel.editor.getTileBuffer().getMap().mapToActionScript()
        };
        final JComboBox box = new JComboBox(formats);
        //text area
        final JTextArea textArea = new JTextArea(EditorWindow.panel.editor.
            getTileBuffer().getMap().mapToJavaString());
        textArea.setColumns(32);
        textArea.setRows(8);
        textArea.setLineWrap( false );
        textArea.setSize(textArea.getPreferredSize().width, 1);
        final JScrollPane scroll = new JScrollPane (textArea,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //copy button settings
        final Clipboard clipboard =
            Toolkit.getDefaultToolkit().getSystemClipboard();
        final JButton copy = new JButton("Copy");

        final JPanel panel = new JPanel();
        panel.add(box);
        panel.add(scroll);
        panel.add(copy);
        //combo listener
        box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText(compiler[box.getSelectedIndex()]);
                textArea.setCaretPosition(0);
            }
        });
        //copy listener
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringSelection data = new StringSelection(textArea.getText());
                clipboard.setContents(data, data);
                textArea.requestFocusInWindow();
                textArea.selectAll();
                JOptionPane.showConfirmDialog(null,
                    "Map successfully copied to clipboard!",
                    "",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //ok listener
        JOptionPane.showConfirmDialog(null,panel, "Copy Map Code!",
            JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
    public void help(){
        JPanel panel = new JPanel();
        JButton button1 = new JButton("Developer Page (doppl3r)");
        JButton button2 = new JButton("Download Latest Version");
        panel.add(button1);
        panel.add(button2);
        //website
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try { java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/doppl3r"));
                } catch (IOException e1) { e1.printStackTrace(); }
            }
        });
        //download again
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try { java.awt.Desktop.getDesktop().
                    browse(java.net.URI.create("https://github.com/doppl3r/" +
                    "Dark-Saunter/raw/master/out/artifacts/Editor/Editor.jar"));
                } catch (IOException e1) { e1.printStackTrace(); }
            }
        });
        JOptionPane.showConfirmDialog(null,panel, "Learn More!",
            JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}
