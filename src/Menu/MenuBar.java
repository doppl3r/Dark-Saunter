package Menu;

import java.awt.*;

public class MenuBar {
    private Color background;
    private Color top;
    private Color right;
    private Color bottom;
    private Color left;
    private int width;
    private int height;
    //menu tabs
    MenuTab file;
    MenuTab view;
    MenuTab textures;

    public MenuBar(){
        //set default values
        height = 20;
        background = new Color(60,63,65);

        left = new Color(60,63,65);
        right = new Color(60,63,65);
        top = new Color(85,85,85);
        bottom = new Color(40,40,40);
        //menu tabs
        file = new MenuTab("File");
        view = new MenuTab("View");
        textures = new MenuTab("Textures");
    }
    public void draw(Graphics2D g){
        //background
        g.setColor(background);
        g.fillRect(0,0,width,height);
        //borders
        g.setColor(left);
        g.fillRect(0,0,1,height);
        g.setColor(right);
        g.fillRect(width, 0, width - 1, height); //1 pixel
        g.setColor(top);
        g.fillRect(0,0,width,1); //1 pixel
        g.setColor(bottom);
        g.fillRect(0, height - 1, width, 1);
        //draw tabs
        file.draw(g);
        view.draw(g);
        textures.draw(g);
    }
    public void down(int x1, int y1){
        file.down(x1,y1);
        view.down(x1,y1);
        textures.down(x1,y1);
    }
    public void move(int x1, int y1){
        file.move(x1,y1);
        view.move(x1,y1);
        textures.down(x1,y1);
    }
    public void up(int x1, int y1){
        file.up(x1,y1);
        view.up(x1,y1);
        textures.up(x1,y1);
    }
    public void hover(int x1, int y1){
        file.hover(x1,y1);
        view.hover(x1,y1);
        textures.hover(x1,y1);
    }
    public void updateMenuWidth(int width){
        this.width=width;
    }
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
}
