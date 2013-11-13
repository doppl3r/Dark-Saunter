package editor;
import textures.SpriteSheet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditorPanel extends JPanel implements KeyListener, MouseWheelListener,
        MouseListener, MouseMotionListener, Runnable {
    private BufferedImage background;
	private static final long serialVersionUID = 1L;
    private boolean paused; //pause option
    private int panelState; //displays menus individually
    private double now;
    private double then;
    private double mod;
    private double delta;
    private double pixelsPerSecond;
    private double mLastTime;
    private int frameSamplesCollected = 0;
    private int frameSampleTime = 0;
    private int fps = 0;
    private Font font;
    public Editor editor;
    public EditorGUI gui;
    public SpriteSheet texture;
	private Timer t;

	public EditorPanel(){
        panelState = 1; //start at editor
        pixelsPerSecond = 100; //very important for computer speed vs. graphic speed
        font = new Font ("Arial", Font.BOLD, 12);
        gui = new EditorGUI();
        editor = new Editor();
        texture = new SpriteSheet(EditorWindow.tt.defaultTexture, 8, 8, 0.0);
        renderBackground();

		//set listeners and thread
		addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
		setFocusable(true);
		run();
	}
	public void run(){
		t = new Timer(1000/60, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
                updateMod(true);
                //update components
				update(mod);
				repaint();
                //final update of mod value
                updateMod(false);
			}
		});
		t.start();
	}
	public void paintComponent(Graphics g1){ //alt for paint
        //convert to Graphics2D
        Graphics2D g = (Graphics2D)g1;
        super.paintComponent(g);
		setBackground(new Color(43,43,43));
        //draw background
        g.drawImage(background,0,0,
            EditorWindow.getPanelWidth(),
            EditorWindow.getPanelHeight(),null);
        //draw components
        if (!paused){
            switch(panelState){
                case(0): break;
                case(1): editor.draw(g,texture); break;
            }
        }
        gui.draw(g,texture);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("fps: "+fps,4,EditorWindow.getPanelHeight()-32);
        updateFPS(); //updatesfps after drawn completely
	}
	public void update(double mod){
		//update the components
        if (!paused){
            switch(panelState){
                case(1): editor.update(mod); break;
            }
        }
        gui.update();
        //EditorWindow.fixJFrame();
	}
	//key bindings
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        switch(panelState){
            case(0): break;
            case(1): //editor keybindings
                if (key == KeyEvent.VK_ESCAPE) { EditorWindow.browser.exit(); }
                else if (key == KeyEvent.VK_W) { changeCurrentID(-1,0); }
                else if (key == KeyEvent.VK_D) { changeCurrentID(0,1); }
                else if (key == KeyEvent.VK_S) { editor.sKey(); }
                else if (key == KeyEvent.VK_A) { changeCurrentID(0,-1);}
                else if (key == KeyEvent.VK_CONTROL) { editor.setControlKey(true); }
                else if (key == KeyEvent.VK_SHIFT){ editor.setShiftKey(true); }
                else if (key == KeyEvent.VK_SPACE) { editor.spaceBarPressed(); } //Drag Tool
                else if (key == KeyEvent.VK_UP) { editor.keyUpPressed(); }
                else if (key == KeyEvent.VK_RIGHT) { editor.keyRightPressed();}
                else if (key == KeyEvent.VK_DOWN) { editor.keyDownPressed(); }
                else if (key == KeyEvent.VK_LEFT) { editor.keyLeftPressed(); }
                else if (key == KeyEvent.VK_O) { editor.oKey(); }
                else if (key == KeyEvent.VK_E) { editor.zoomIn(true); }
                else if (key == KeyEvent.VK_Q) { editor.zoomOut(true); }
                else if (key == KeyEvent.VK_Z) { editor.zKey(); }
                else if (key == KeyEvent.VK_Y) { editor.yKey(); }
                else if (key == KeyEvent.VK_F) { editor.setCurrentTool(1); }  //Fill  Tool
                else if (key == KeyEvent.VK_V) { editor.setCurrentTool(2); }  //Erase Tool
                else if (key == KeyEvent.VK_B) { editor.setCurrentTool(3); }  //Draw Tool
                else if (key == KeyEvent.VK_1) { EditorWindow.browser.newMap(); }
                else if (key == KeyEvent.VK_2) { EditorWindow.browser.deleteMap(); }
                else if (key == KeyEvent.VK_3) { EditorWindow.browser.changeArrayProperties(false); }
                else if (key == KeyEvent.VK_4) { editor.addCol();}
                else if (key == KeyEvent.VK_5) { editor.removeCol(); }
                else if (key == KeyEvent.VK_6) { editor.addRow(); }
                else if (key == KeyEvent.VK_7) { editor.removeRow(); }
                else if (key == KeyEvent.VK_8) { EditorWindow.browser.openMap(); }
                else if (key == KeyEvent.VK_9) { EditorWindow.browser.saveMap(); }
                else if (key == KeyEvent.VK_0) { EditorWindow.browser.changeTextureProperties(); }
                else if (key == KeyEvent.VK_F11) editor.renderMap(texture);
                else if (key == KeyEvent.VK_F12) captureScreen();
            break;
        }
	}
	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch(panelState){
            case(0): if (key != 0) {}
            case(1): //editor keybindings
                if (key == KeyEvent.VK_UP) { editor.keyUpReleased(); }
                if (key == KeyEvent.VK_RIGHT) { editor.keyRightReleased(); }
                if (key == KeyEvent.VK_DOWN) { editor.keyDownReleased(); }
                if (key == KeyEvent.VK_LEFT) { editor.keyLeftReleased(); }
                if (key == KeyEvent.VK_CONTROL) { editor.setControlKey(false); }
                if (key == KeyEvent.VK_SHIFT){ editor.setShiftKey(false); }
                if (key == KeyEvent.VK_SPACE) { editor.spaceBarReleased(); }
                if (key == KeyEvent.VK_G) { editor.toggleGrid(); }
            break;
        }
    }
	public void keyTyped(KeyEvent arg0) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { //down
        int x = e.getX();
        int y = e.getY();
        int buttonID = e.getButton();
        if (!editor.isActive()) gui.down(x,y);
        if (!gui.isActive()) editor.down(x,y,buttonID);
    }
    public void mouseDragged(MouseEvent e) { //move
        int x = e.getX();
        int y = e.getY();
        int buttonID = e.getButton();
        if (!editor.isActive()) gui.move(x,y);
        if (!gui.isActive()) editor.move(x,y,buttonID);
    }
    public void mouseReleased(MouseEvent e) { //up
        int x = e.getX();
        int y = e.getY();
        int buttonID = e.getButton();
        gui.up(x,y);
        if (!gui.isActive()) editor.up(x,y,buttonID);
    }
    public void mouseMoved(MouseEvent e) {
        //update cursor
        int x = e.getX();
        int y = e.getY();
        gui.hover(x,y);
        if (!gui.isActive()) editor.hover(x,y);
    }
    public void mouseWheelMoved(MouseWheelEvent e) {
        String message;
        int notches = e.getWheelRotation();
        if (notches >= 0) editor.zoomOut(false);
        else editor.zoomIn(false);
    }
    //update FPS
    public void updateFPS() {
        long tempNow = System.currentTimeMillis();
        if (mLastTime != 0) {
            int time = (int) (tempNow - mLastTime);
            frameSampleTime += time;
            frameSamplesCollected++;
            if (frameSamplesCollected == 10) {
                if (frameSampleTime != 0)
                    fps = (10000 / frameSampleTime);
                frameSampleTime = 0;
                frameSamplesCollected = 0;
            }
        }
        mLastTime = tempNow;
    }
    public void updateMod(boolean start){
        if (start){
            //first update module value
            now = System.currentTimeMillis();
            delta = (now - then)/1000;
            mod = delta*pixelsPerSecond;
        } else then = now;
    }
    public void setGlobalID(int id){
        editor.setTileID(id);
        gui.textureBox.setTileID(id);
    }
    public void changeCurrentID(int newRow, int newCol){
        int rows = texture.getHFrames();
        int cols = texture.getVFrames();
        int tempID = editor.getTileID()-1;
        int row = (tempID/cols)+newRow;
        int col = (tempID%cols)+newCol;
        //parameters
        if (row < 0) row = rows-1;
        if (col < 0) col = cols-1;
        if (row > rows-1) row = 0;
        if (col > cols-1) col = 0;
        tempID = (col) + (row*cols) + 1;
        setGlobalID(tempID);
    }
    public void captureScreen(){
        BufferedImage img = new BufferedImage(EditorWindow.getPanelWidth(),
            EditorWindow.getPanelHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.createGraphics();
        paintComponent(g);
        g.dispose();
        try {
            File outputfile = new File("screenshot.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {}
    }
    public void renderBackground(){
        int color = 43;
        int width  = EditorWindow.getPanelWidth();
        int height = EditorWindow.getPanelHeight();

        background = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics g = background.createGraphics();
        //draw stuff normally
        g.setColor(new Color(color,color,color));
        g.fillRect(0,0,width,height);
        for (int i = 0; i < 20000; i++){
            int size = 50;
            int rand = (int)(Math.random()*2);
            int randX = (int)(Math.random()*(width+size)) -size;
            int randY = (int)(Math.random()*(height+size))-size;
            int maxDelta = size;
            double delta = 0;
            //draw lines
            for (int j = 0; j < maxDelta; j++){
                if (j < maxDelta/2) delta+=0.35;
                else delta-=0.35;
                //draw lines
                g.setColor(new Color(color+(int)(delta),color+(int)(delta),color+(int)(delta)));
                if (rand == 0) g.fillRect(randX+j,randY,1,1);
                else g.fillRect(randX,randY+j,1,1);
            }
        }
        //dispose heap
        g.dispose();
    }
}
