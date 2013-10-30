package editor;
import java.awt.*;
import buttons.Button;
import textures.SpriteSheet;

public class EditorGUI {

    //nav1
    private Button openArray;
    private Button saveArray;
    private Button addRow;
    private Button removeRow;
    private Button addColumn;
    private Button removeColumn;
    private Button newArray;
    private Button deleteArray;
    private Button exit;
    //nav2
    private Button zoomIn;
    private Button zoomOut;
    private Button zoomFit;
    //nav3
    private Button fillTool;
    private Button eraseTool;
    private Button drawTool;
    //nav4
    private Button importTexture;
    private Button settings;
    private SpriteSheet textureBox;

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
        exit = new Button(EditorWindow.tt.exit,0,0,1,2,false);
        //nav2
        zoomIn = new Button(EditorWindow.tt.zoomIn,0,0,1,2,false);
        zoomOut = new Button(EditorWindow.tt.zoomOut,0,0,1,2,false);
        zoomFit = new Button(EditorWindow.tt.zoomFit,0,0,1,2,false);
        //nav3
        fillTool = new Button(EditorWindow.tt.fillTool,0,0,1,2,false);
        eraseTool = new Button(EditorWindow.tt.eraseTool,0,0,1,2,false);
        drawTool = new Button(EditorWindow.tt.drawTool,0,0,1,2,false);
        //nav4
        importTexture = new Button(EditorWindow.tt.importTexture,0,0,1,2,false);
        settings = new Button(EditorWindow.tt.settings,0,0,1,2,false);
        textureBox = new SpriteSheet(EditorWindow.tt.textureBox,1,1,0);
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
        exit.draw(g);
        //nav2
        zoomIn.draw(g);
        zoomOut.draw(g);
        zoomFit.draw(g);
        //nav3
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
        openArray.down(x1,y1);
        saveArray.down(x1,y1);
        addRow.down(x1,y1);
        removeRow.down(x1,y1);
        addColumn.down(x1,y1);
        removeColumn.down(x1,y1);
        newArray.down(x1,y1);
        deleteArray.down(x1,y1);
        exit.down(x1,y1);
        //nav2
        zoomIn.down(x1,y1);
        zoomOut.down(x1,y1);
        zoomFit.down(x1,y1);
        //nav3
        fillTool.down(x1,y1);
        eraseTool.down(x1,y1);
        drawTool.down(x1,y1);
        //nav4
        importTexture.down(x1,y1);
        settings.down(x1,y1);

        //textureBox.down(x1,y1);
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
        //nav3
        fillTool.move(x1,y1);
        eraseTool.move(x1,y1);
        drawTool.move(x1,y1);
        //nav4
        importTexture.move(x1,y1);
        settings.move(x1,y1);

        //textureBox.move(x1,y1);
    }
    public void up(int x1, int y1){
        if (openArray.up(x1,y1)){ EditorWindow.browser.openMap(); }
        else if (saveArray.up(x1,y1)){  }
        else if (addRow.up(x1,y1)){ EditorWindow.panel.editor.addRow(); }
        else if (removeRow.up(x1,y1)){ EditorWindow.panel.editor.removeRow(); }
        else if (addColumn.up(x1,y1)){ EditorWindow.panel.editor.addCol(); }
        else if (removeColumn.up(x1,y1)){ EditorWindow.panel.editor.removeCol(); }
        else if (newArray.up(x1,y1)){  }
        else if (deleteArray.up(x1,y1)){  }
        else if (exit.up(x1,y1)){ EditorWindow.exit(); }
        //nav2
        else if (zoomIn.up(x1,y1)){  }
        else if (zoomOut.up(x1,y1)){  }
        else if (zoomFit.up(x1,y1)){  }
        //nav3
        else if (fillTool.up(x1,y1)){  }
        else if (eraseTool.up(x1,y1)){  }
        else if (drawTool.up(x1,y1)){  }
        //nav4
        else if (importTexture.up(x1,y1)){  }
        else if (settings.up(x1,y1)){  }

        //textureBox.up(x1,y1);
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
        exit.hover(x1,y1);
        //nav2
        zoomIn.hover(x1,y1);
        zoomOut.hover(x1,y1);
        zoomFit.hover(x1,y1);
        //nav3
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
        openArray.update(4,4); openArray.setHint("Open Map");
        saveArray.update(32,4); saveArray.setHint("Save Map");
        addRow.update(60,4); addRow.setHint("Add Row");
        removeRow.update(88,4); removeRow.setHint("Remove Row");
        addColumn.update(116,4); addColumn.setHint("Add Column");
        removeColumn.update(144,4); removeColumn.setHint("Remove Column");
        newArray.update(172,4); newArray.setHint("New Map");
        deleteArray.update(200,4); deleteArray.setHint("Delete Map");
        exit.update(228,4); exit.setHint("Exit");
        //nav2 - update according to right
        zoomIn.update(width-84,4); zoomIn.setHint("Zoom In");
        zoomOut.update(width-56,4); zoomOut.setHint("Zoom Out");
        zoomFit.update(width-28,4); zoomFit.setHint("Zoom Fit");
        //nav3 - update according to left and bottom
        fillTool.update(4,height-28); fillTool.setHint("Fill Tool");
        eraseTool.update(32,height-28); eraseTool.setHint("Erase Tool");
        drawTool.update(60,height-28); drawTool.setHint("Draw Tool");
        //nav4 - update according to right and bottom
        importTexture.update(width-168,height-56); importTexture.setHint("Import Texture");
        settings.update(width-168,height-28); settings.setHint("Settings");
        textureBox.update(width-140,height-140);
    }
}
