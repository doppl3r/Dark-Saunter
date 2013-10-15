import textures.SpriteSheet;

import java.awt.*;

public class Player {
    private SpriteSheet sprite;
    private double x;
    private double y;
    private double xSpeed;
    private double ySpeed;

    private int score;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    public Player(){
        //sprite = new SpriteSheet(Window.tt.mage1,4,4,0.1);
        sprite.resize(32,64);
        sprite.center();

        x = Window.getWidth()/2;
        y = Window.getHeight()/2;
        xSpeed = ySpeed = 4;
    }
    public void draw(Graphics2D g){
        sprite.draw(g);
    }
    public void update(double mod){
        //animations
        if (up) sprite.animate(12,16);
        else if (down) sprite.animate(8,12);
        else if (left) sprite.animate(4,8);
        else if (right) sprite.animate(0,4);
        if (!up && !down && !left && !right) sprite.animate(8);
        //direction
        if (up) y-=ySpeed*mod;
        if (down) y+=ySpeed*mod;
        if (left) x-=xSpeed*mod;
        if (right) x+=xSpeed*mod;
        //keep in bounds
        if (y < 0) y = 0;
        else if (y > Window.getOriginalHeight()) y = Window.getOriginalHeight();
        if (x < 0) x = 0;
        else if (x > Window.getOriginalWidth()) x = Window.getOriginalWidth();
        //update the x value to the sprite
        sprite.update(x,y);
    }
    public void moveUp(boolean up){ this.up=up; }
    public void moveRight(boolean right){ this.right=right; }
    public void moveDown(boolean down){ this.down=down; }
    public void moveLeft(boolean left){ this.left=left; }
    public void addPoint(){ score++; }
    public int getScore(){ return score; }
    public int getWidth(){ return sprite.getSpriteWidth(); }
    public int getHeight(){ return sprite.getSpriteHeight(); }
    public double getX(){ return x; }
    public double getY(){ return y; }
}
