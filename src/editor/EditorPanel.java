package editor;
import textures.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditorPanel extends JPanel implements KeyListener, MouseWheelListener,
        MouseListener, MouseMotionListener, Runnable {
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
        texture = new SpriteSheet(EditorWindow.tt.defaultTexture, 4, 4, 0.0);

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
        //draw components
        if (!paused){
            switch(panelState){
                case(0): break;
                case(1): editor.draw(g,texture); break;
            }
        }
        gui.draw(g);
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
                else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { editor.keyUpPressed(); }
                else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { editor.keyRightPressed(); }
                else if (key == KeyEvent.VK_DOWN) { editor.keyDownPressed(); }
                else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { editor.keyLeftPressed(); }
                else if (key == KeyEvent.VK_CONTROL) { editor.setControlKey(true); }
                else if (key == KeyEvent.VK_SHIFT){ editor.setShiftKey(true); }
                else if (key == KeyEvent.VK_SPACE) { editor.spaceBarPressed(); } //Drag Tool
                else if (key == KeyEvent.VK_S) { editor.sKey(); }
                else if (key == KeyEvent.VK_O) { editor.oKey(); }
                else if (key == KeyEvent.VK_E) { editor.zoomIn(true); }
                else if (key == KeyEvent.VK_Q) { editor.zoomOut(true); }
                else if (key == KeyEvent.VK_Z) { editor.zKey(); }
                else if (key == KeyEvent.VK_Y) { editor.yKey(); }
                else if (key == KeyEvent.VK_F) { editor.setCurrentTool(1); }  //Fill  Tool
                else if (key == KeyEvent.VK_V) { editor.setCurrentTool(2); }  //Erase Tool
                else if (key == KeyEvent.VK_B) { editor.setCurrentTool(3); }  //Draw Tool
                else if (key == KeyEvent.VK_1) { EditorWindow.browser.openMap(); }
                else if (key == KeyEvent.VK_2) { EditorWindow.browser.saveMap(); }
                else if (key == KeyEvent.VK_3) { editor.addRow(); }
                else if (key == KeyEvent.VK_4) { editor.removeRow(); }
                else if (key == KeyEvent.VK_5) { editor.addCol();}
                else if (key == KeyEvent.VK_6) { editor.removeCol(); }
                else if (key == KeyEvent.VK_7) { EditorWindow.browser.newMap(); }
                else if (key == KeyEvent.VK_8) { EditorWindow.browser.deleteMap(); }
                else if (key == KeyEvent.VK_9) { EditorWindow.browser.changeArrayProperties(); }
                else if (key == KeyEvent.VK_0) { EditorWindow.browser.changeTextureProperties(); }
            break;
        }
	}
	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch(panelState){
            case(0): if (key != 0) {}
            case(1): //editor keybindings
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { editor.keyUpReleased(); }
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { editor.keyRightReleased(); }
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { editor.keyDownReleased(); }
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { editor.keyLeftReleased(); }
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
}
