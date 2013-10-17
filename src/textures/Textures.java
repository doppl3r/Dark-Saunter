package textures;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Textures {
    public Image icon_hd;
    public Image blood;
    public Image terrain;
    public Image toon;

	
	public Textures(){
		addResources();
	}
	public void addResources(){
        //graphics
        blood = new ImageIcon(this.getClass().getResource("/graphics/blood.png")).getImage();
        terrain = new ImageIcon(this.getClass().getResource("/graphics/terrain.png")).getImage();
        toon = new ImageIcon(this.getClass().getResource("/graphics/toon.png")).getImage();
        //gui
        icon_hd = new ImageIcon(this.getClass().getResource("/gui/icon_hd.png")).getImage();
	}
}
