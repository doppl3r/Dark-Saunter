package editor;
import java.awt.*;
import buttons.Button;
import textures.SpriteSheet;

public class EditorGUI {
    private boolean active; //allows prioritiy if a button is hit
    //nav1
    private Button openArray;
    private Button saveArray;
    private Button addRow;
    private Button removeRow;
    private Button addColumn;
    private Button removeColumn;
    private Button newArray;
    private Button deleteArray;
    private Button undo;
    private Button redo;
    private Button exit;
    //nav2
    private Button zoomIn;
    private Button zoomOut;
    private Button zoomFit;
    private Button navUp;
    private Button navRight;
    private Button navDown;
    private Button navLeft;
    //nav3
    private Button dragTool;
    private Button fillTool;
    private Button eraseTool;
    private Button drawTool;
    //nav4
    private Button importTexture;
    private Button settings;
    public TextureBox textureBox;

    public EditorGUI(){
        //nav1
        openArray = new Button(EditorWindow.tt.openArray,0,0,1,2,false);
        saveArray = new Button(EditorWindow.tt.saveArray,0,0,1,2,false);
        addRow = new Button(EditorWindow.tt.addRow,0,0,1,2,false);
        removeRow = new Button(EditorWindow.tt.removeRow,0,0,1,2,false);
        addColumn = new Button(EditorWindow.tt.addColumn,0,0,1,2,false);
        removeColumn = new Button(EditorWindow.tt.removeColumn,0,0,1,2,false);
        newArray = new Button(EditorWindow.tt.newArray,0,0,1,2,false);
        deleteArray = new Button(EditorWindow.tt.deleteArray,0,0,1,2,false);
        undo = new Button(EditorWindow.tt.navLeft,0,0,1,2,false);
        redo = new Button(EditorWindow.tt.navRight,0,0,1,2,false);
        exit = new Button(EditorWindow.tt.exit,0,0,1,2,false);
        //nav2
        zoomIn = new Button(EditorWindow.tt.zoomIn,0,0,1,2,false);
        zoomOut = new Button(EditorWindow.tt.zoomOut,0,0,1,2,false);
        zoomFit = new Button(EditorWindow.tt.zoomFit,0,0,1,2,false);
        navUp = new Button(EditorWindow.tt.navUp,0,0,1,2,false);
        navRight = new Button(EditorWindow.tt.navRight,0,0,1,2,false);
        navDown = new Button(EditorWindow.tt.navDown,0,0,1,2,false);
        navLeft = new Button(EditorWindow.tt.navLeft,0,0,1,2,false);
        //nav3
        dragTool = new Button(EditorWindow.tt.dragTool,0,0,1,2,false);
        fillTool = new Button(EditorWindow.tt.fillTool,0,0,1,2,false);
        eraseTool = new Button(EditorWindow.tt.eraseTool,0,0,1,2,false);
        drawTool = new Button(EditorWindow.tt.drawTool,0,0,1,2,false);
        //nav4
        importTexture = new Button(EditorWindow.tt.importTexture,0,0,1,2,false);
        settings = new Button(EditorWindow.tt.settings,0,0,1,2,false);
        textureBox = new TextureBox();
        //position each button
        positionGUI();
    }
    public void draw(Graphics2D g){
        openArray.draw(g);
        saveArray.draw(g);
        addRow.draw(g);
        removeRow.draw(g);
        addColumn.draw(g);
        removeColumn.draw(g);
        newArray.draw(g);
        deleteArray.draw(g);
        undo.draw(g);
        redo.draw(g);
        exit.draw(g);
        //nav2
        zoomIn.draw(g);
        zoomOut.draw(g);
        zoomFit.draw(g);
        navUp.draw(g);
        navRight.draw(g);
        navDown.draw(g);
        navLeft.draw(g);
        //nav3
        dragTool.draw(g);
        fillTool.draw(g);
        eraseTool.draw(g);
        drawTool.draw(g);
        //nav4
        importTexture.draw(g);
        settings.draw(g);
        textureBox.draw(g);
    }
    public void update(){
        //update gui position according to window size
        positionGUI(); //maybe update only once the screenwidth has changed
    }
    public void down(int x1, int y1){
        if (openArray.down(x1,y1)) active = true;
        if (saveArray.down(x1,y1)) active = true;
        if (addRow.down(x1,y1)) active = true;
        if (removeRow.down(x1,y1)) active = true;
        if (addColumn.down(x1,y1)) active = true;
        if (removeColumn.down(x1,y1)) active = true;
        if (newArray.down(x1,y1)) active = true;
        if (deleteArray.down(x1,y1)) active = true;
        if (undo.down(x1,y1)) active = true;
        if (redo.down(x1,y1)) active = true;
        if (exit.down(x1,y1)) active = true;
        //nav2
        if (zoomIn.down(x1,y1)) active = true;
        if (zoomOut.down(x1,y1)) active = true;
        if (zoomFit.down(x1,y1)) active = true;
        if (navUp.down(x1,y1)) active = true;
        if (navRight.down(x1,y1)) active = true;
        if (navDown.down(x1,y1)) active = true;
        if (navLeft.down(x1,y1)) active = true;
        //nav3
        if (dragTool.down(x1,y1)) active = true;
        if (fillTool.down(x1,y1)) active = true;
        if (eraseTool.down(x1,y1)) active = true;
        if (drawTool.down(x1,y1)) active = true;
        //nav4
        if (importTexture.down(x1,y1)) active = true;
        if (settings.down(x1,y1)) active = true;
        if (textureBox.down(x1,y1)) active = true;
    }
    public void move(int x1, int y1){
        openArray.move(x1,y1);
        saveArray.move(x1,y1);
        addRow.move(x1,y1);
        removeRow.move(x1,y1);
        addColumn.move(x1,y1);
        removeColumn.move(x1,y1);
        newArray.move(x1,y1);
        deleteArray.move(x1,y1);
        exit.move(x1,y1);
        //nav2
        zoomIn.move(x1,y1);
        zoomOut.move(x1,y1);
        zoomFit.move(x1,y1);
        navUp.move(x1,y1);
        navRight.move(x1,y1);
        navDown.move(x1,y1);
        navLeft.move(x1,y1);
        //nav3
        dragTool.move(x1,y1);
        fillTool.move(x1,y1);
        eraseTool.move(x1,y1);
        drawTool.move(x1,y1);
        //nav4
        importTexture.move(x1,y1);
        settings.move(x1,y1);

        textureBox.move(x1,y1);
    }
    public void up(int x1, int y1){
        if (openArray.up(x1,y1)){ EditorWindow.browser.openMap(); }
        else if (saveArray.up(x1,y1)){ EditorWindow.browser.saveMap(); }
        else if (addRow.up(x1,y1)){ EditorWindow.panel.editor.addRow(); }
        else if (removeRow.up(x1,y1)){ EditorWindow.panel.editor.removeRow(); }
        else if (addColumn.up(x1,y1)){ EditorWindow.panel.editor.addCol(); }
        else if (removeColumn.up(x1,y1)){ EditorWindow.panel.editor.removeCol(); }
        else if (newArray.up(x1,y1)){ EditorWindow.browser.newMap(); }
        else if (deleteArray.up(x1,y1)){ EditorWindow.browser.deleteMap(); }
        else if (undo.up(x1,y1)) { EditorWindow.panel.editor.undo(); }
        else if (redo.up(x1,y1)) { EditorWindow.panel.editor.redo(); }
        else if (exit.up(x1,y1)){ EditorWindow.browser.exit(); }
        //nav2
        else if (zoomIn.up(x1,y1)){ EditorWindow.panel.editor.zoomIn(true); }
        else if (zoomOut.up(x1,y1)){ EditorWindow.panel.editor.zoomOut(true); }
        else if (zoomFit.up(x1,y1)){ EditorWindow.panel.editor.zoomFit(false); }
        else if (navUp.up(x1,y1)){ EditorWindow.panel.editor.moveDown(); }
        else if (navRight.up(x1,y1)){ EditorWindow.panel.editor.moveLeft(); }
        else if (navDown.up(x1,y1)){ EditorWindow.panel.editor.moveUp(); }
        else if (navLeft.up(x1,y1)){ EditorWindow.panel.editor.moveRight(); }
        //nav3
        else if (dragTool.up(x1,y1)){ EditorWindow.panel.editor.setCurrentTool(0); }
        else if (fillTool.up(x1,y1)){ EditorWindow.panel.editor.setCurrentTool(1); }
        else if (eraseTool.up(x1,y1)){ EditorWindow.panel.editor.setCurrentTool(2); }
        else if (drawTool.up(x1,y1)){ EditorWindow.panel.editor.setCurrentTool(3); }
        //nav4
        else if (importTexture.up(x1,y1)){  }
        else if (settings.up(x1,y1)){  }

        textureBox.up(x1,y1);
        active = false;
    }
    public void hover(int x1, int y1){
        openArray.hover(x1,y1);
        saveArray.hover(x1,y1);
        addRow.hover(x1,y1);
        removeRow.hover(x1,y1);
        addColumn.hover(x1,y1);
        removeColumn.hover(x1,y1);
        newArray.hover(x1,y1);
        deleteArray.hover(x1,y1);
        undo.hover(x1,y1);
        redo.hover(x1,y1);
        exit.hover(x1,y1);
        //nav2
        zoomIn.hover(x1,y1);
        zoomOut.hover(x1,y1);
        zoomFit.hover(x1,y1);
        navUp.hover(x1,y1);
        navRight.hover(x1,y1);
        navDown.hover(x1,y1);
        navLeft.hover(x1,y1);
        //nav3
        dragTool.hover(x1,y1);
        fillTool.hover(x1,y1);
        eraseTool.hover(x1,y1);
        drawTool.hover(x1,y1);
        //nav4
        importTexture.hover(x1,y1);
        settings.hover(x1,y1);

        //textureBox.hover(x1,y1);
    }
    public void positionGUI(){
        int width = EditorWindow.getPanelWidth();
        int height = EditorWindow.getPanelHeight();
        //nav1 - update according to left
        openArray.update(4,4); openArray.setHint("Open Map [1]");
        saveArray.update(32,4); saveArray.setHint("Save Map [2]");
        addRow.update(72,4); addRow.setHint("Add Row [3]");
        removeRow.update(100,4); removeRow.setHint("Remove Row [4]");
        addColumn.update(140,4); addColumn.setHint("Add Column [5]");
        removeColumn.update(168,4); removeColumn.setHint("Remove Column [6]");
        newArray.update(208,4); newArray.setHint("New Map [7]");
        deleteArray.update(236,4); deleteArray.setHint("Clear Map [8]");
        undo.update(276,4) ; undo.setHint("Undo [Ctrl + z]");
        redo.update(304,4) ; redo.setHint("Redo [Ctrl + y]");
        exit.update(332,4); exit.setHint("Exit [Esc]");
        //nav2 - update according to right
        zoomIn.update(width-84,4); zoomIn.setHint("Zoom In [e]");
        zoomOut.update(width-28,4); zoomOut.setHint("Zoom Out [q]");
        zoomFit.update(width-56,32); zoomFit.setHint("Zoom Fit [z]");
        navUp.update(width-56,4); navUp.setHint("Move Up [Up Arrow]");
        navRight.update(width-28,32); navRight.setHint("Move Right [Right Arrow]");
        navDown.update(width-56,60); navDown.setHint("Move Down [Down Arrow]");
        navLeft.update(width-84,32); navLeft.setHint("Move Left [Left Arrow]");
        //nav3 - update according to left and bottom
        dragTool.update(4,height-28); dragTool.setHint("Drag Tool [Space Bar]");
        fillTool.update(32,height-28); fillTool.setHint("Fill Tool [f]");
        eraseTool.update(60,height-28); eraseTool.setHint("Erase Tool [v]");
        drawTool.update(88,height-28); drawTool.setHint("Draw Tool [b]");
        //nav4 - update according to right and bottom
        importTexture.update(width - 168, height - 56); importTexture.setHint("Import Texture");
        settings.update(width-168,height-28); settings.setHint("Settings");
        textureBox.update(width-140,height-140);
    }
    public boolean isActive(){ return active; }
}
