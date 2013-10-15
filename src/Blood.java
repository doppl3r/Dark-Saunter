import textures.SpriteSheet;

import java.awt.*;
import java.util.LinkedList;

public class Blood {
    private LinkedList<Splatter> blood;
    int alphaType;
    public Blood(){
        blood = new LinkedList<Splatter>();
        alphaType = AlphaComposite.SRC_OVER;
    }
    public void draw(Graphics2D g){
        int size = blood.size();
        for (int i = 0; i < size; i++) blood.get(i).draw(g);
    }
    public void update(double mod){
        for (int i = 0; i < blood.size(); i++){
            blood.get(i).update(mod);
            if (!blood.get(i).isVisible()) blood.remove(i);
        }
    }
    public void add(int x, int y){
        blood.add(new Splatter(x,y));
    }
    public class Splatter {
        private double currentTime;
        private double maxTime;
        private boolean visible;

        private SpriteSheet sprite;

        public Splatter(int x, int y){
            sprite = new SpriteSheet(Window.tt.blood, 4, 1, 0.0);
            sprite.resize(64,64);
            sprite.center();
            sprite.update(x,y);
            sprite.animate((int)(Math.random()*4));
            currentTime = maxTime = 1000;
            visible = true;
        }
        public void draw(Graphics2D g){
            g.setComposite(AlphaComposite.getInstance(alphaType, (float)(currentTime/maxTime)));
            sprite.draw(g);
            g.setComposite(AlphaComposite.getInstance(alphaType, 1.0f));
        }
        public void update(double mod){
            if (currentTime <= 0) visible = false;
            else currentTime--;
        }
        public boolean isVisible(){ return visible; }
    }
}
