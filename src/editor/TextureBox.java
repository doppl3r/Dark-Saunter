package editor;

import textures.SpriteSheet;
import java.awt.*;

public class TextureBox {
    private SpriteSheet textureBox;
    private SpriteSheet texture;
    private int tileID;

    public TextureBox(){
        textureBox = new SpriteSheet(EditorWindow.tt.textureBox,1,1,0);
        //cloneTextureProperties();
    }
    public void draw(Graphics2D g){
        textureBox.draw(g);

    }
    public void update(int x, int y){
        textureBox.update(x,y);
    }
    public boolean down(int x1, int y1){
        return (setTileID(x1,y1));
    }
    public void up(int x1, int y1){

    }
    public boolean setTileID(int x1, int y1){
        boolean active = false;
        int x2 = textureBox.getDestRectLeft()+4;
        int y2 = textureBox.getDestRectTop()+4;
        int cols = EditorWindow.panel.editor.getTileBuffer().getTexture().getVFrames();
        int rows = EditorWindow.panel.editor.getTileBuffer().getTexture().getHFrames();
        if (x1 >= x2 && x1 < x2+128 &&
            y1 >= y2 && y2 < y2+128){
            tileID = (((x1-x2)/(128/cols))+((y1-y2)/(128/rows))*cols)+1; //128 is the textBox area
            EditorWindow.panel.editor.setTileID(tileID);
            active = true;
        }
        return active;
    }
    public void cloneTextureProperties(){
        texture = new SpriteSheet(
            EditorWindow.panel.editor.getTileBuffer().getTexture().getImage(),
            EditorWindow.panel.editor.getTileBuffer().getTexture().getHFrames(),
            EditorWindow.panel.editor.getTileBuffer().getTexture().getVFrames(),0);
    }
}
