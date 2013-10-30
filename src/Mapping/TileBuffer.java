package mapping;
import textures.SpriteSheet;

import java.awt.*;

public class TileBuffer {
    private TileMap map;
    private SpriteSheet texture;
    private int blockSize;

    public TileBuffer(Image image){
        map = new TileMap(16,8);
        texture = new SpriteSheet(image, 4, 4, 0.0);
        blockSize = 32;
    }
    public void draw(Graphics2D g){
        for (int row = 0; row < map.getRows(); row++){
            for (int col = 0; col < map.getCols(); col++){
                texture.animate(map.getTile(row,col).getID());
                texture.update((col*blockSize),(row*blockSize),blockSize,blockSize);
                texture.draw(g);
            }
        }
    }
    public void update(double mod){

    }
    //mouse actions
    public void down(int x, int y){

    }
    public void move(int x, int y){

    }
    public void up(int x, int y){

    }
    public void hover(int x, int y){

    }
    public void addRow(){ map.addRow(); }
    public void removeRow(){ map.removeLastRow(); }
    public void addCol(){ map.addCol(); }
    public void removeCol(){ map.removeLastCol(); }
}
