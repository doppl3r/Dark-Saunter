import textures.SpriteSheet;

public class Enemy1 extends Enemy{
    public Enemy1(){
        //setSprite(new SpriteSheet(Window.tt.mage2,4,4,0.1));
        resizeSprite(32,64);
        centerSprite();
        setXDirection((Math.random()*5)-3);
        setYDirection((Math.random()*5)-3);
    }
}
