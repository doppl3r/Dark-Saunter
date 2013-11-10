package editor;

import java.awt.*;
import mapping.TileBuffer;
import textures.SpriteSheet;

public class Editor {
    private TileBuffer tileBuffer;
    private int zoomAmount;
    private int blockSize;
    private int tileID;
    private int currentTool;
    private int savedTool;
    private double mainX;
    private double mainY;
    private double mouseX;
    private double mouseY;
    private double downX;
    private double downY;
    private double dragX;
    private double dragY;
    private boolean dragDraw;
    private boolean dragErase;
    private boolean controlKey;
    private boolean shiftKey;

    public Editor(){
        tileBuffer = new TileBuffer();
        blockSize = 32;
        zoomAmount = 4;
        tileID = 1;
        currentTool = 3;
        savedTool = -1;
        tileBuffer.update(mainX, mainY, blockSize, 0, 0);
        zoomFit(true);
    }
    public void draw(Graphics2D g, SpriteSheet texture){
        tileBuffer.draw(g, texture);
    }
    public void update(double mod){
        tileBuffer.update(mainX-dragX, mainY-dragY, blockSize, tileID, mod);
    }
    //key pressed
    public void keyUpPressed(){ moveDown(); }
    public void keyDownPressed(){ moveUp(); }
    public void keyLeftPressed(){ moveRight(); }
    public void keyRightPressed(){ moveLeft(); }
    public void spaceBarPressed(){ forceDrag(true); }

    //key released
    public void keyUpReleased(){  }
    public void keyDownReleased(){  }
    public void keyLeftReleased(){  }
    public void keyRightReleased(){  }
    public void spaceBarReleased(){ forceDrag(false); }

    //mouse actions
    public boolean down(int x, int y, int buttonID){
        boolean inBounds = false;
        switch(currentTool){
            case(0): //drag tool
                downX = x;
                downY = y;
                inBounds=true;
                break;
            case(1): //fill tool
                if (tileBuffer.down(x,y,buttonID)) inBounds=true;
                if (buttonID == 1) floodFill(x,y,-1,tileID); //flood with current id
                else if (buttonID != 0) floodFill(x,y,-1,0); //erase flood
                break;
            case(2): //erase tool
                if (tileBuffer.down(x,y,buttonID)) inBounds=true;
                if (buttonID == 1){ dragErase = true; move(x,y,buttonID); }
                else if (buttonID != 0){ dragDraw = true; move(x,y,buttonID); }
                break;
            case(3): //draw tool
                if (tileBuffer.down(x,y,buttonID)) inBounds=true;
                if (buttonID == 1){ dragDraw = true; move(x,y,buttonID); }
                else if (buttonID != 0){ dragErase = true; move(x,y,buttonID); }
                break;
        }
        if (inBounds) EditorWindow.browser.setSavedState(false);
        return inBounds;
    }
    public boolean move(int x, int y, int buttonID){
        boolean inBounds = false;
        tileBuffer.move(x,y,buttonID);
        if (currentTool == 0){ //drag the map if selected
            dragX = downX-x;
            dragY = downY-y;
            inBounds=true;
        }
        if (dragDraw){ //draw with current id
            if (x-mainX > 0  && x-mainX < tileBuffer.getMapPixelWidth() &&
                    y-mainY > 0  && y-mainY < tileBuffer.getMapPixelHeight()){
                if (tileBuffer.setTileID((int)(y-mainY)/blockSize,(int)(x-mainX)/blockSize, tileID)) inBounds = true;
            }
        }
        else if (dragErase){ //erase
            if (x-mainX > 0  && x-mainX < tileBuffer.getMapPixelWidth() &&
                    y-mainY > 0  && y-mainY < tileBuffer.getMapPixelHeight()){
                if (tileBuffer.setTileID((int)(y-mainY)/blockSize,(int)(x-mainX)/blockSize, 0)) inBounds = true;
            }
        }
        if (inBounds) EditorWindow.browser.setSavedState(false);
        return inBounds;
    }
    public void up(int x, int y, int buttonID){
        //reset all drag values
        tileBuffer.up(x,y,buttonID);
        dragDraw = dragErase = false;
        if (dragX != 0) mainX -= dragX;
        if (dragY != 0) mainY -= dragY;
        dragX = dragY = downX = downY = 0;
        //check for performance
        EditorWindow.browser.memoryWarning(tileBuffer.getTime());
    }
    public boolean isNotClicked(){ return dragX == 0 && dragY == 0; }
    public void hover(int x, int y){
        mouseX = x;
        mouseY = y;
        tileBuffer.hover(x, y);
    }
    public boolean isActive(){ return (dragDraw || dragErase || !isNotClicked()); }
    public void addRow(){ EditorWindow.browser.setSavedState(false); tileBuffer.addRow(); }
    public void removeRow(){ EditorWindow.browser.setSavedState(false); tileBuffer.removeRow(); }
    public void addCol(){ EditorWindow.browser.setSavedState(false); tileBuffer.addCol(); }
    public void removeCol(){ EditorWindow.browser.setSavedState(false); tileBuffer.removeCol(); }
    public void setNewMap(int cols, int rows){
        EditorWindow.browser.setSavedState(false);
        tileBuffer.setNewMap(cols,rows);
    }
    public void setTileAt(int row, int col, int id){
        EditorWindow.browser.setSavedState(false);
        tileBuffer.setTileID(row,col,id);
    }
    public void setRows(int rows){ tileBuffer.setRows(rows); }
    public void setCols(int cols){ tileBuffer.setCols(cols); }
    public void setRowsAndCols(int rows, int cols,boolean clear){
        EditorWindow.browser.setSavedState(false);
        tileBuffer.setRowsAndCols(rows,cols,clear);
    }
    public void resetMap(){
        EditorWindow.browser.setSavedState(false);
        EditorWindow.browser.changeArrayProperties(true);
        //tileBuffer.resetMap(); zoomFit(false);
    }
    public void clearMap(){ EditorWindow.browser.setSavedState(false); tileBuffer.clearMap(); }
    public void setTileID(int id){ tileID = id; }
    public void undo(){ EditorWindow.browser.setSavedState(false); tileBuffer.undo(); }
    public void redo(){ EditorWindow.browser.setSavedState(false); tileBuffer.redo(); }
    public void zoomIn(boolean center){
        double x = EditorWindow.getPanelWidth()/2;
        double y = EditorWindow.getPanelHeight()/2;
        if ((mouseX-mainX)/tileBuffer.getMapPixelWidth() < 0 ||
            (mouseX-mainX)/tileBuffer.getMapPixelWidth() > 1 ||
            (mouseY-mainY)/tileBuffer.getMapPixelHeight() < 0 ||
            (mouseY-mainY)/tileBuffer.getMapPixelHeight() > 1){
            center = true;
        }
        if (!center){
            x = mouseX;
            y = mouseY;
        }
        if (blockSize < 128){
            blockSize += zoomAmount;
            mainX -= (x - mainX)/(((blockSize-zoomAmount))/zoomAmount);
            mainY -= (y - mainY)/(((blockSize-zoomAmount))/zoomAmount);
        }
    }
    public void zoomOut(boolean center){
        double x = EditorWindow.getPanelWidth()/2;
        double y = EditorWindow.getPanelHeight()/2;
        if ((mouseX-mainX)/tileBuffer.getMapPixelWidth() < 0 ||
            (mouseX-mainX)/tileBuffer.getMapPixelWidth() > 1 ||
            (mouseY-mainY)/tileBuffer.getMapPixelHeight() < 0 ||
            (mouseY-mainY)/tileBuffer.getMapPixelHeight() > 1){
            center = true;
        }
        if (!center){
            x = mouseX;
            y = mouseY;
        }
        if (blockSize > 4){
            blockSize -= zoomAmount;
            mainX += (x - mainX)/(((blockSize+zoomAmount))/zoomAmount);
            mainY += (y - mainY)/(((blockSize+zoomAmount))/zoomAmount);
        } else {
            if (tileBuffer.getMapPixelWidth() < EditorWindow.getPanelWidth() &&
                tileBuffer.getMapPixelHeight()< EditorWindow.getPanelHeight())
                zoomFit(false);
        }
    }
    public void zoomFit(boolean force){ //only work if controlKey is not being held
        int width;
        int height;
        if (!controlKey){
            if (force){
                width  = EditorWindow.getOriginalWidth();
                height = EditorWindow.getOriginalHeight();
            }
            else {
                width  = EditorWindow.getPanelWidth();
                height = EditorWindow.getPanelHeight();
            }
            mainX = (width/2) -(tileBuffer.getMapPixelWidth() /2);
            mainY = (height/2)-(tileBuffer.getMapPixelHeight()/2);
        }
    }
    public void toggleGrid(){ tileBuffer.toggleGrid(); }
    public void setControlKey(boolean controlKey){ this.controlKey=controlKey; }
    public void setShiftKey(boolean shiftKey){ this.shiftKey=shiftKey; }
    public void zKey(){
        if (controlKey) {
            if (!shiftKey) undo();
            else redo();
        } else zoomFit(false); }
    public void yKey(){ if (controlKey) { redo(); }}
    public void sKey(){ if (controlKey) { EditorWindow.browser.quickSave(false); } else EditorWindow.panel.changeCurrentID(1,0); }
    public void oKey(){ if (controlKey) { EditorWindow.browser.openMap(); }}
    public void renderMap(SpriteSheet texture){ tileBuffer.renderMap(texture); }
    public void setCurrentTool(int i){
        currentTool = i;
        EditorWindow.setCursor(i);
    }
    public void floodFill(int x, int y, int oldVal, int newVal){ tileBuffer.floodFill(x,y,oldVal,newVal); }
    public void setEnableHistory(boolean enableHistory){ tileBuffer.setEnableHistory(enableHistory); }
    public void forceDrag(boolean force){
        //this will force the drag-map feature so that the SPACE bar can drag the map when held
        if (force){
            if (savedTool == -1){
                savedTool = currentTool;
                setCurrentTool(0);
            }
        }
        else{
            setCurrentTool(savedTool);
            savedTool = -1;
        }
    }
    //navigation
    public void moveUp(){ mainY-=blockSize; }
    public void moveRight(){ mainX+=blockSize; }
    public void moveDown(){ mainY+=blockSize; }
    public void moveLeft(){ mainX-=blockSize; }
    //getters
    public TileBuffer getTileBuffer(){ return tileBuffer; }
    public double getTime(){ return tileBuffer.getTime(); }
    public int getTileID(){ return tileID; }
}
