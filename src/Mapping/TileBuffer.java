package mapping;
import editor.EditorWindow;
import textures.SpriteSheet;

import java.awt.*;

public class TileBuffer {
    private TileMap map;
    private SpriteSheet texture;
    private Font font;
    private int tileID;
    private double mainX;
    private double mainY;
    private double blockSize;
    private boolean grid;

    public TileBuffer(Image image){
        map = new TileMap(10,8);
        texture = new SpriteSheet(image, 4, 4, 0.0);
        font = new Font("Arial", Font.PLAIN, 10);
        grid = true;
    }
    public void draw(Graphics2D g){
        int windowX = EditorWindow.getPanelWidth();
        int windowY = EditorWindow.getPanelHeight();
        double tempX;
        double tempY;
        for (int row = 0; row < map.getRows(); row++){
            for (int col = 0; col < map.getCols(); col++){
                tempX = mainX+(col*blockSize);
                tempY = mainY+(row*blockSize);
                texture.animate(map.getTile(row,col).getID()-1);
                texture.update(tempX,tempY,(int)blockSize,(int)blockSize);
                texture.draw(g,windowX,windowY);
                //grid
                if (grid){
                    if (tempX >= -blockSize && tempX < windowX && tempY >= -blockSize && tempY < windowY){
                        g.setColor(Color.LIGHT_GRAY);
                        g.drawRect((int)tempX,(int)tempY,(int)blockSize,(int)blockSize);
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
    public void down(int x, int y, int buttonID){
        map.saveMap();
    }
    public void move(int x, int y, int buttonID){

    }
    public void up(int x, int y, int buttonID){

    }
    public void hover(int x, int y){

    }
    public void addRow(){ map.saveMap(); map.addRow(); }
    public void removeRow(){ map.saveMap(); map.removeLastRow(); }
    public void addCol(){ map.saveMap(); map.addCol(); }
    public void removeCol(){ map.saveMap(); map.removeLastCol(); }
    public void resetMap(){ map.saveMap(); map.resetMap(); }
    public void clearMap(){ map.saveMap(); map.clearMap(); }
    public void setTileID(int row, int col, int tileID){ map.setTileID(row,col,tileID); }
    public void undo(){ map.undo(); }
    public void redo(){ map.redo(); }
    //getters
    public TileMap getMap(){ return map; }
    public int getMapPixelWidth(){ return (int)blockSize*map.getCols(); }
    public int getMapPixelHeight(){ return (int)blockSize*map.getRows(); }
    public SpriteSheet getTexture(){ return texture; }
    public int getBlockSize(){ return (int)blockSize; }
}
