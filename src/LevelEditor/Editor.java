package LevelEditor;
import Mapping.Tile;

import java.awt.*;
import java.util.LinkedList;

public class Editor {
    private LinkedList<Tile> map;


    public Editor(){
        map = new LinkedList<Tile>();
    }
    public void draw(Graphics2D g){
        //blood.draw(g);
    }
    public void update(double mod){
        //blood.update(mod);
    }
    //key pressed
    public void keyUpPressed(){  }
    public void keyDownPressed(){  }
    public void keyLeftPressed(){  }
    public void keyRightPressed(){  }

    //key released
    public void keyUpReleased(){  }
    public void keyDownReleased(){  }
    public void keyLeftReleased(){  }
    public void keyRightReleased(){  }

    //mouse actions
    public void down(int x, int y){

    }
    public void move(int x, int y){

    }
    public void up(int x, int y){

    }
    public void hover(int x, int y){

    }
}
