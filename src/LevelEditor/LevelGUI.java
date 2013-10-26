package LevelEditor;

import java.awt.*;
import Menu.MenuBar;

public class LevelGUI {
    MenuBar menuBar;

    public LevelGUI(){
        menuBar = new MenuBar();
    }
    public void draw(Graphics2D g){
        menuBar.draw(g);
        /*if (Window.panel.getPanelState() == 1){
            g.drawLine(0,26,Window.getWidth(),26);
            g.drawLine(0,30,Window.getWidth(),30);
        }*/
    }
    public void update(){
        menuBar.updateMenuWidth(LevelWindow.getWindowWidth());
    }
    public void down(int x1, int y1){
        menuBar.down(x1,y1);
    }
    public void move(int x1, int y1){
        menuBar.move(x1,y1);
    }
    public void up(int x1, int y1){
        menuBar.up(x1,y1);
    }
    public void hover(int x1, int y1){
        menuBar.hover(x1,y1);
    }
}
