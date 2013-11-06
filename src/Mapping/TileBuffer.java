package mapping;
import editor.EditorWindow;
import textures.SpriteSheet;

import java.awt.*;

public class TileBuffer {
    private TileMap map;
    private Font font;
    private int tileID;
    private double mainX;
    private double mainY;
    private double blockSize;
    private boolean grid;

    public TileBuffer(){
        map = new TileMap(10,8);
        font = new Font("Arial", Font.PLAIN, 10);
        grid = true;
    }
    public void draw(Graphics2D g, SpriteSheet texture){
        int windowX = EditorWindow.getPanelWidth();
        int windowY = EditorWindow.getPanelHeight();
        int tempX;
        int tempY;
        g.setColor(new Color(49,51,53));
        g.fillRect((int)mainX,(int)mainY,(int)(map.getCols()*blockSize),(int)(map.getRows()*blockSize));
        for (int row = 0; row < map.getRows(); row++){
            for (int col = 0; col < map.getCols(); col++){
                tempX = (int)(mainX+(col*blockSize));
                tempY = (int)(mainY+(row*blockSize));
                texture.animate(map.getTile(row,col).getID()-1);
                texture.update(tempX,tempY,(int)blockSize,(int)blockSize);
                texture.draw(g,windowX,windowY);
                //grid
                if (grid){
                    if (tempX >= -blockSize && tempX < windowX && tempY >= -blockSize && tempY < windowY){
                        g.setColor(new Color(33,35,37));
                        g.drawRect(tempX,tempY,(int)blockSize,(int)blockSize);
                    }
                }
            }
        }
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("["+map.getCols()+", "+map.getRows()+"]",(int)mainX, (int)mainY-3);
    }
    public void update(double mainX, double mainY, double blockSize, int blockID, double mod){
        this.mainX=mainX;
        this.mainY=mainY;
        this.blockSize=blockSize;
        this.tileID =blockID;
    }
    //mouse actions
    public void down(int x, int y, int buttonID){;
        //save the map before the next action! Very important!
        if (x-mainX > 0 && x-mainX < getMapPixelWidth() &&
            y-mainY > 0  && y-mainY < getMapPixelHeight()){
            map.saveMap();
        }
    }
    public void move(int x, int y, int buttonID){

    }
    public void up(int x, int y, int buttonID){

    }
    public void hover(int x, int y){

    }
    public void floodFill(int x, int y, int oldVal, int newVal){
        int row = (int)((int)(y-mainY)/blockSize);
        int col = (int)((int)(x-mainX)/blockSize);
        if (x-mainX > 0 && x-mainX < getMapPixelWidth() &&
            y-mainY > 0  && y-mainY < getMapPixelHeight()){
            map.floodFill(row,col,oldVal,newVal);
        }
    }
    public void toggleGrid(){ grid = !grid; }
    public void addRow(){ map.saveMap(); map.addRow(); }
    public void removeRow(){ map.saveMap(); map.removeLastRow(); }
    public void addCol(){ map.saveMap(); map.addCol(); }
    public void removeCol(){ map.saveMap(); map.removeLastCol(); }
    public void setNewMap(int cols, int rows){ map.saveMap(); map.setNewMap(cols,rows); }
    public void setRows(int rows){ map.setRows(rows); }
    public void setCols(int cols){ map.setCols(cols); }
    public void setRowsAndCols(int rows, int cols){ map.saveMap(); setRows(rows); setCols(cols); }
    public void resetMap(){ map.saveMap(); map.resetMap(); }
    public void clearMap(){ map.saveMap(); map.clearMap(); }
    public void setTileID(int row, int col, int tileID){ map.setTileID(row,col,tileID); }
    public void undo(){ map.undo(); }
    public void redo(){ map.redo(); }
    //getters
    public TileMap getMap(){ return map; }
    public int getMapPixelWidth(){ return (int)blockSize*map.getCols(); }
    public int getMapPixelHeight(){ return (int)blockSize*map.getRows(); }
    public int getBlockSize(){ return (int)blockSize; }
}
