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
        sprite = new SpriteSheet(Window.tt.toon,8,4,0.2);
        sprite.resize(64,64);
        sprite.center();
        sprite.animate(16); //start looking down

        x = Window.getWidth()/2;
        y = Window.getHeight()/2;
        xSpeed = ySpeed = 1.0; //0.5 works
    }
    public void draw(Graphics2D g){
        sprite.draw(g);
    }
    public void update(double mod){
        //animations
        if (up) sprite.animate(0,8,mod);
        else if (right) sprite.animate(8,16,mod);
        else if (down) sprite.animate(16,24,mod);
        else if (left) sprite.animate(24,32,mod);
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
    public void moveUp(boolean up){
        if (!up) sprite.animate(0);
        this.up=up;
    }
    public void moveRight(boolean right){
        if (!right) sprite.animate(8);
        this.right=right;
    }
    public void moveDown(boolean down){
        if (!down) sprite.animate(16);
        this.down=down;
    }
    public void moveLeft(boolean left){
        if (!left) sprite.animate(24);
        this.left=left;
    }
    public void addPoint(){ score++; }
    public int getScore(){ return score; }
    public int getWidth(){ return sprite.getSpriteWidth(); }
    public int getHeight(){ return sprite.getSpriteHeight(); }
    public double getX(){ return x; }
    public double getY(){ return y; }
}
