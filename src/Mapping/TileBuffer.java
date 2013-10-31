package mapping;
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
        for (int row = 0; row < map.getRows(); row++){
            for (int col = 0; col < map.getCols(); col++){
                texture.animate(map.getTile(row,col).getID()-1);
                texture.update(mainX+(col*blockSize),mainY+(row*blockSize),blockSize,blockSize);
                texture.draw(g);
                //grid
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(mainX+(col*blockSize),mainY+(row*blockSize),blockSize,blockSize);
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
}
