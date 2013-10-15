import java.awt.*;

public class MainMenu {
    private double alpha;
    private double fadeRate;
    private int minFade;
    private int maxFade;
    private int alphaType;
    private boolean fadeOut;

    public MainMenu(){
        fadeRate = 0.015;
        minFade = 0;
        maxFade = 1;
        alphaType = AlphaComposite.SRC_OVER;
    }
    public void draw(Graphics2D g){
        /*g.drawImage(Window.tt.title, (int)(Math.random()*5),(int)(Math.random()*5),null);
        g.setComposite(AlphaComposite.getInstance(alphaType, (float)alpha));
        g.drawImage(Window.tt.zombieLarge, 230+(int)(Math.random()*10),5+(int)(Math.random()*10),null);
        g.setComposite(AlphaComposite.getInstance(alphaType, 1.0f));*/
    }
    public void update(double mod){
        //fade the zombie
        if (fadeOut){
            if (alpha - fadeRate > minFade) alpha-=fadeRate;
            else{ alpha = minFade; fadeOut = false; }
        }
        else {
            if (alpha + fadeRate < maxFade) alpha+=fadeRate;
            else{ alpha = maxFade; fadeOut = true; }
        }
    }
    public void loadGame(){
        Window.panel.setPanelState(1);
    }
}
