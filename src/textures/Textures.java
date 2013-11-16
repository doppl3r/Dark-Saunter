package textures;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Textures {
    public Image icon;
    public Image blood;
    public Image terrain;
    public Image toon;
    //editor
    public Image openArray;
    public Image saveArray;
    public Image addRow;
    public Image removeRow;
    public Image addColumn;
    public Image removeColumn;
    public Image newArray;
    public Image deleteArray;
    public Image copy;
    public Image exit;
    public Image zoomIn;
    public Image zoomOut;
    public Image zoomFit;
    public Image grid;
    public Image dragTool;
    public Image fillTool;
    public Image eraseTool;
    public Image drawTool;
    public Image importTexture;
    public Image settings;
    public Image textureBox;
    public Image textureBoxSmall;
    public Image navUp;
    public Image navRight;
    public Image navDown;
    public Image navLeft;

    //extra
    public Image defaultTexture;

    public Textures(){
		addResources();
	}
	public void addResources(){
        //graphics
        blood = new ImageIcon(this.getClass().getResource("/graphics/blood.png")).getImage();
        terrain = new ImageIcon(this.getClass().getResource("/graphics/terrain.png")).getImage();
        toon = new ImageIcon(this.getClass().getResource("/graphics/toon.png")).getImage();
        //gui
        icon = new ImageIcon(this.getClass().getResource("/gui/icon.png")).getImage();
        openArray = new ImageIcon(this.getClass().getResource("/gui/openArray.png")).getImage();
        saveArray = new ImageIcon(this.getClass().getResource("/gui/saveArray.png")).getImage();
        addRow = new ImageIcon(this.getClass().getResource("/gui/addRow.png")).getImage();
        removeRow = new ImageIcon(this.getClass().getResource("/gui/removeRow.png")).getImage();
        addColumn = new ImageIcon(this.getClass().getResource("/gui/addColumn.png")).getImage();
        removeColumn = new ImageIcon(this.getClass().getResource("/gui/removeColumn.png")).getImage();
        newArray = new ImageIcon(this.getClass().getResource("/gui/newArray.png")).getImage();
        deleteArray = new ImageIcon(this.getClass().getResource("/gui/deleteArray.png")).getImage();
        copy = new ImageIcon(this.getClass().getResource("/gui/copy.png")).getImage();
        exit = new ImageIcon(this.getClass().getResource("/gui/exit.png")).getImage();
        zoomIn = new ImageIcon(this.getClass().getResource("/gui/zoomIn.png")).getImage();
        zoomOut = new ImageIcon(this.getClass().getResource("/gui/zoomOut.png")).getImage();
        zoomFit = new ImageIcon(this.getClass().getResource("/gui/zoomFit.png")).getImage();
        grid = new ImageIcon(this.getClass().getResource("/gui/grid.png")).getImage();
        dragTool = new ImageIcon(this.getClass().getResource("/gui/dragTool.png")).getImage();
        fillTool = new ImageIcon(this.getClass().getResource("/gui/fillTool.png")).getImage();
        eraseTool = new ImageIcon(this.getClass().getResource("/gui/eraseTool.png")).getImage();
        drawTool = new ImageIcon(this.getClass().getResource("/gui/drawTool.png")).getImage();
        importTexture = new ImageIcon(this.getClass().getResource("/gui/importTexture.png")).getImage();
        settings = new ImageIcon(this.getClass().getResource("/gui/settings.png")).getImage();
        textureBox = new ImageIcon(this.getClass().getResource("/gui/textureBox.png")).getImage();
        textureBoxSmall = new ImageIcon(this.getClass().getResource("/gui/textureBoxSmall.png")).getImage();
        navUp = new ImageIcon(this.getClass().getResource("/gui/navUp.png")).getImage();
        navRight = new ImageIcon(this.getClass().getResource("/gui/navRight.png")).getImage();
        navDown = new ImageIcon(this.getClass().getResource("/gui/navDown.png")).getImage();
        navLeft = new ImageIcon(this.getClass().getResource("/gui/navLeft.png")).getImage();
        //extra
        defaultTexture = new ImageIcon(this.getClass().getResource("/graphics/defaultTexture.png")).getImage();
	}
}
