package mapping;
import editor.EditorWindow;
import textures.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        map.defaultMap();
        font = new Font("Arial", Font.PLAIN, 10);
        grid = true;
    }
    public void draw(Graphics2D g, SpriteSheet texture){
        int windowX = EditorWindow.getPanelWidth();
        int windowY = EditorWindow.getPanelHeight();
        int tempX;
        int tempY;
        //set parameters
        int minCol = -(int)(mainX/blockSize);
        int maxCol = (int)(minCol+((windowX+(blockSize*2))/blockSize));
        if (minCol < 0) minCol = 0;
        if (maxCol < 0) maxCol = 0;
        if (maxCol > map.getCols()) maxCol = map.getCols();
        if (minCol > maxCol) minCol = maxCol;

        int minRow = -(int)(mainY/blockSize);
        int maxRow = (int)(minRow+((windowY+(blockSize*2))/blockSize));
        if (minRow < 0) minRow = 0;
        if (maxRow < 0) maxRow = 0;
        if (maxRow > map.getRows()) maxRow = map.getRows();
        if (minRow > maxRow) minRow = maxRow;

        g.setColor(new Color(49,51,53));
        g.fillRect((int)mainX,(int)mainY,(int)(map.getCols()*blockSize),(int)(map.getRows()*blockSize));
        for (int row = minRow; row < maxRow; row++){
            for (int col = minCol; col < maxCol; col++){
                tempX = (int)(mainX+(col*blockSize));
                tempY = (int)(mainY+(row*blockSize));
                texture.animate(map.getTile(row,col).getID()-1);
                texture.update(tempX,tempY,(int)blockSize,(int)blockSize);
                texture.draw(g,windowX,windowY);
                //grid
                if (grid){
                    g.setColor(new Color(33,35,37));
                    g.drawRect(tempX,tempY,(int)blockSize,(int)blockSize);
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
    public boolean down(int x, int y, int buttonID){;
        boolean inBounds = false;
        //save the map before the next action! Very important!
        if (x-mainX > 0 && x-mainX < getMapPixelWidth() &&
            y-mainY > 0  && y-mainY < getMapPixelHeight()){
            map.saveMap();
            inBounds = true;
        }
        return inBounds;
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
    public void setRowsAndCols(int rows, int cols, boolean clear){
        if (!clear) map.saveMap();
        setRows(rows);
        setCols(cols);
    }
    public void resetMap(){ map.saveMap(); map.resetMap(); }
    public void clearMap(){ map.saveMap(); map.clearMap(); }
    public boolean setTileID(int row, int col, int tileID){ return map.setTileID(row,col,tileID); }
    public void undo(){ map.undo(); }
    public void redo(){ map.redo(); }
    //getters
    public TileMap getMap(){ return map; }
    public int getMapPixelWidth(){ return (int)blockSize*map.getCols(); }
    public int getMapPixelHeight(){ return (int)blockSize*map.getRows(); }
    public int getBlockSize(){ return (int)blockSize; }
    //render map
    public void renderMap(SpriteSheet texture){
        int tempX;
        int tempY;
        boolean oldGrid = grid;
        BufferedImage img = new BufferedImage(texture.getSpriteWidth()*map.getCols(),
            texture.getSpriteHeight()*map.getRows(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        grid = false;

        for (int row = 0; row < map.getRows(); row++){
            for (int col = 0; col < map.getCols(); col++){
                tempX = (col*texture.getSpriteWidth());
                tempY = (row*texture.getSpriteHeight());
                texture.animate(map.getTile(row,col).getID()-1);
                texture.update(tempX,tempY,texture.getSpriteWidth(),texture.getSpriteHeight());
                texture.draw(g);
            }
        }
        grid = oldGrid;
        g.dispose();
        try {
            File outputfile = new File("map_image.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {}
    }
}
