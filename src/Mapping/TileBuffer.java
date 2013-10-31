package mapping;
import editor.EditorWindow;
import textures.SpriteSheet;

import java.awt.*;

public class TileBuffer {
    private TileMap map;
    private SpriteSheet texture;
    private int tileID;
    private int blockSize;
    private int mainX;
    private int mainY;

    public TileBuffer(Image image){
        map = new TileMap(10,8);
        texture = new SpriteSheet(image, 4, 4, 0.0);
    }
    public void draw(Graphics2D g){
        int windowX = EditorWindow.getPanelWidth();
        int windowY = EditorWindow.getPanelHeight();
        int tempX;
        int tempY;
        for (int row = 0; row < map.getRows(); row++){
            for (int col = 0; col < map.getCols(); col++){
                tempX = mainX+(col*blockSize);
                tempY = mainY+(row*blockSize);
                texture.animate(map.getTile(row,col).getID()-1);
                texture.update(tempX,tempY,blockSize,blockSize);
                texture.draw(g,windowX,windowY);
                //grid
                if (tempX >= -blockSize && tempX < windowX && tempY >= -blockSize && tempY < windowY){
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(tempX,tempY,blockSize,blockSize);
                }
            }
        }
    }
    public void update(int mainX, int mainY, int blockSize, int blockID, double mod){
        this.mainX=mainX;
        this.mainY=mainY;
        this.blockSize=blockSize;
        this.tileID =blockID;
    }
    //mouse actions
    public void down(int x, int y, int buttonID){
        //if (left) map.setTileID((y-mainY)/blockSize,(x-mainX)/blockSize,tileID); //draw
        //else map.setTileID((y-mainY)/blockSize,(x-mainX)/blockSize,0); //erase on right click
    }
    public void move(int x, int y, int buttonID){

    }
    public void up(int x, int y, int buttonID){

    }
    public void hover(int x, int y){

    }
    public void addRow(){ map.addRow(); }
    public void removeRow(){ map.removeLastRow(); }
    public void addCol(){ map.addCol(); }
    public void removeCol(){ map.removeLastCol(); }
    public void resetMap(){ map.resetMap(); }
    public void clearMap(){ map.clearMap(); }
    public void setTileID(int row, int col, int tileID){ map.setTileID(row,col,tileID); }
    //getters
    public TileMap getMap(){ return map; }
    public int getMapPixelWidth(){ return blockSize*map.getCols(); }
    public int getMapPixelHeight(){ return blockSize*map.getRows(); }
    public SpriteSheet getTexture(){ return texture; }
    public int getBlockSize(){ return blockSize; }
}
