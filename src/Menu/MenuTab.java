package Menu;
import java.awt.*;

public class MenuTab {
    private String name;
    private int x;
    private int y;
    private int xPadding;
    private int yPadding;
    private int width;
    private int height;

    public MenuTab(String name, int height){
        this.name=name;
        this.height=height;
    }
    public void draw(Graphics2D g){
        g.setColor(new Color(187,187,187));
        g.drawString(name, x+xPadding, y+yPadding);
    }
    public void update(double mod){

    }
    public void down(int x1, int y1){

    }
    public void move(int x1, int y1){

    }
    public void up(int x1, int y1){

    }
    public void hover(int x1, int y1){ }
}
