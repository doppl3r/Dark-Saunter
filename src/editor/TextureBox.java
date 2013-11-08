package editor;

import textures.SpriteSheet;
import java.awt.*;

public class TextureBox {
    private SpriteSheet textureBox;
    private SpriteSheet textureBoxSmall;
    private int tileID;
    private int hoverX;
    private int hoverY;

    public TextureBox(){
        textureBox = new SpriteSheet(EditorWindow.tt.textureBox,1,1,0);
        textureBoxSmall = new SpriteSheet(EditorWindow.tt.textureBoxSmall,1,1,0);
        tileID = 1;
    }
    public void draw(Graphics2D g, SpriteSheet texture){
        int x1 = textureBox.getDestRectLeft()+4;
        int y1 = textureBox.getDestRectTop()+4;
        int x2 = textureBox.getDestRectLeft();
        int y2 = textureBox.getDestRectTop();
        int width = 128;
        int height = 128;
        int rows = texture.getVFrames();
        int cols = texture.getHFrames();
        g.setColor(new Color(49,51,53));
        g.fillRect(x1,y1,width,height);
        g.drawImage(texture.getImage(),
                x1, y1, x2+width+4, y2+height+4,
                0,
                0,
                texture.getOriginalImageWidth(),
                texture.getOriginalImageHeight(),
                null);
        textureBox.draw(g);
        //draw grid
        //g.setColor(new Color(0,0,0,50));
        g.setColor(new Color(33,35,37));
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                g.drawRect(x1+(col)*(width/cols), y1+(row)*(height/rows),
                    (width/cols),(height/rows));
            }
        }
        //draw selector
        g.setColor(new Color(255,255,255,100));
        g.fillRect(
            x1+((tileID-1)%cols)*(width/cols)+1,
            y1+((tileID-1)/cols)*(height/rows)+1,
            (width/cols)-2,
            (height/rows)-2);
        g.setColor(Color.WHITE);
        g.drawRect(
                x1+((tileID-1)%cols)*(width/cols)+1,
                y1+((tileID-1)/cols)*(height/rows)+1,
                (width/cols)-2,
                (height/rows)-2);
        //show hover box
        if (hoverX !=0 && hoverY != 0){
            g.setColor(new Color(49,51,53));
            g.fillRect(
                hoverX-textureBoxSmall.getImageWidth()+4,
                hoverY-textureBoxSmall.getImageHeight()+4,64,64);
            textureBoxSmall.draw(g);
            texture.animate(tileID-1);
            texture.update(hoverX-textureBoxSmall.getImageWidth()+4,
                hoverY-textureBoxSmall.getImageHeight()+4,64,64);
            texture.draw(g);
            g.setColor(new Color(33,35,37));
            g.drawRect(
                hoverX-textureBoxSmall.getImageWidth()+4,
                hoverY-textureBoxSmall.getImageHeight()+4,64,64);
        }
    }
    public void update(int x, int y){
        textureBox.update(x,y);
        textureBoxSmall.update(hoverX-textureBoxSmall.getImageWidth(),
            hoverY-textureBoxSmall.getImageHeight());
    }
    public boolean down(int x1, int y1){
        boolean hover = setTileID(x1,y1,true);
        if (hover){
            hoverX = x1;
            hoverY = y1;
        }
        return hover;
    }
    public void move(int x1, int y1){
        boolean hover = setTileID(x1,y1,true);
        if (hover){
            hoverX = x1;
            hoverY = y1;
        }
    }
    public void up(int x1, int y1){
        setTileID(x1,y1,true);
        hoverX = hoverY = 0;
    }
    public void hover(int x1, int y1){}
    public void setTileID(int tileID){ this.tileID=tileID; }
    public boolean setTileID(int x1, int y1, boolean force){
        boolean active = false;
        int x2 = textureBox.getDestRectLeft()+4;
        int y2 = textureBox.getDestRectTop()+4;
        int rows = EditorWindow.panel.texture.getVFrames();
        int cols = EditorWindow.panel.texture.getHFrames();
        if (x1 >= x2 && x1 < x2+128 &&
            y1 >= y2 && y1 < y2+128){
            if (force){
                tileID = (((x1-x2)/(128/cols))+((y1-y2)/(128/rows))*cols)+1; //128 is the textBox area
                EditorWindow.panel.editor.setTileID(tileID);
            }
            active = true;
        }
        return active;
    }
}
