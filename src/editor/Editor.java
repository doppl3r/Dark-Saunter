package editor;

import java.awt.*;
import mapping.TileBuffer;

public class Editor {
    private TileBuffer tileBuffer;
    private int zoomAmount;
    private int blockSize;
    private int mainX;
    private int mainY;
    private int tileID;
    private int currentTool;
    private int savedTool;
    private int downX;
    private int downY;
    private int dragX;
    private int dragY;
    private boolean dragDraw;
    private boolean dragErase;

    public Editor(){
        tileBuffer = new TileBuffer(EditorWindow.tt.defaultTexture);
        blockSize = 32;
        zoomAmount = 16;
        tileID = 1;
        currentTool = 3;
        savedTool = -1;
        tileBuffer.update(mainX, mainY, blockSize, 0, 0);
        zoomFit(true);
    }
    public void draw(Graphics2D g){
        tileBuffer.draw(g);
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
    public void down(int x, int y, int buttonID){
        switch(currentTool){
            case(0): //drag tool
                downX = x;
                downY = y;

                break;
            case(1): //fill tool
                floodFill((y-mainY)/blockSize,(x-mainX)/blockSize,-1,tileID);
                break;
            case(2): //erase tool
                dragErase = true;
                break;
            case(3): //draw tool
                if (buttonID == 1){ dragDraw = true; move(x,y,buttonID); }
                else if (buttonID != 0){ dragErase = true; move(x,y,buttonID); }
                break;
        }
    }
    public void move(int x, int y, int buttonID){
        if (currentTool == 0){
            dragX = downX-x;
            dragY = downY-y;
        }
        if (dragDraw){
            tileBuffer.setTileID((y-mainY)/blockSize, (x-mainX)/blockSize, tileID);
        }
        else if (dragErase){
            tileBuffer.setTileID((y-mainY)/blockSize, (x-mainX)/blockSize, 0);
        }
    }
    public void up(int x, int y, int buttonID){
        tileBuffer.up(x,y,buttonID);
        dragDraw = dragErase = false;
        if (dragX != 0) mainX -= dragX;
        if (dragY != 0) mainY -= dragY;
        dragX = dragY = downX = downY = 0;
    }
    public void hover(int x, int y){
        tileBuffer.hover(x, y);
    }
    public void addRow(){ tileBuffer.addRow(); }
    public void removeRow(){ tileBuffer.removeRow(); }
    public void addCol(){ tileBuffer.addCol(); }
    public void removeCol(){ tileBuffer.removeCol(); }
    public void resetMap(){ tileBuffer.resetMap(); zoomFit(false); }
    public void clearMap(){ tileBuffer.clearMap(); }
    public void setTileID(int id){ tileID = id; }
    public void zoomIn(){
        if (blockSize < 128){
            blockSize += zoomAmount;
            mainX -= ((EditorWindow.getPanelWidth()/2)  - mainX)/((blockSize-zoomAmount)/zoomAmount);
            mainY -= ((EditorWindow.getPanelHeight()/2) - mainY)/((blockSize-zoomAmount)/zoomAmount);
        }
    }
    public void zoomOut(){
        if (blockSize > 16){
            blockSize -= zoomAmount;
            mainX += ((EditorWindow.getPanelWidth()/2)  - mainX)/((blockSize+zoomAmount)/zoomAmount);
            mainY += ((EditorWindow.getPanelHeight()/2) - mainY)/((blockSize+zoomAmount)/zoomAmount);
        } else zoomFit(false);
    }
    public void zoomFit(boolean force){
        int width;
        int height;
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
    public void setCurrentTool(int i){
        currentTool = i;
        EditorWindow.setCursor(i);
    }
    public boolean floodFill(int row, int col, int oldVal, int newVal){
        if (row >= 0 && row <= tileBuffer.getMap().getRows()-1 &&
            col >= 0 && col <= tileBuffer.getMap().getCols()-1){
            if (oldVal == -1) oldVal = tileBuffer.getMap().getTile(row,col).getID();
            if (tileBuffer.getMap().getTile(row,col).getID() != oldVal) return true; //breaker
            if (oldVal == newVal) return true; //breaker
            tileBuffer.setTileID(row,col,newVal);
            if (col > 0) floodFill(row, col-1, oldVal, newVal);
            if (row > 0) floodFill(row-1, col, oldVal, newVal);
            if (col < tileBuffer.getMap().getCols()-1) floodFill(row, col+1, oldVal, newVal);
            if (row < tileBuffer.getMap().getRows()-1) floodFill(row+1, col, oldVal, newVal);
        }
        return true;
    }
    public void forceDrag(boolean force){
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
}
