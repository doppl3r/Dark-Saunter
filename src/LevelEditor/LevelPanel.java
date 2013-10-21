package LevelEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class LevelPanel extends JPanel implements KeyListener,
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
    BufferedImage buffered;
    public Editor editor;
    public LevelGUI gui;
	private Timer t;

	public LevelPanel(){
        panelState = 1; //start at editor
        pixelsPerSecond = 100; //very important for computer speed vs. graphic speed
        font = new Font ("Arial", Font.BOLD, 18);
        editor = new Editor();
        gui = new LevelGUI();

        //start music
        //AudioHandler.THEME.clip.loop(-1);

		//set listeners and thread
		addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
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
        g.setFont(font);
        super.paintComponent(g);
		setBackground(new Color(43,43,43));
        if (LevelWindow.isStretched()) g.drawImage(buffered, 0,0, LevelWindow.getWidth(), LevelWindow.getHeight(),this);
        else{ //for exact proportion
            double ratio =  ((double) LevelWindow.getOriginalWidth()/(double) LevelWindow.getOriginalHeight());
            g.drawImage(buffered,
                /*x1*/ (LevelWindow.getWidth()/2)-((int)(LevelWindow.getHeight()*ratio)/2),
                /*y1*/ 0,
                /*x2*/ (int)(LevelWindow.getHeight()*ratio),
                /*y2*/ LevelWindow.getHeight(), this);
        }
        LevelWindow.updateSize(); //Window.jf
        doubleBuffer();
	}
    public void draw(Graphics2D g){
        g.drawString("fps: "+fps,4,16);
        //draw components
        if (!paused){
            switch(panelState){
                case(1): editor.draw(g); break;
            }
        }
        gui.draw(g);
        updateFPS(); //updatesfps after drawn completely
    }
    public void doubleBuffer(){
        buffered = new BufferedImage(LevelWindow.getOriginalWidth(), LevelWindow.getOriginalHeight(),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffered.createGraphics();
        draw(g);
    }
	public void update(double mod){
		//update the components
        if (!paused){
            switch(panelState){
                case(1): editor.update(mod); break;
            }
        }
        gui.update();
	}
	//key bindings
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        switch(panelState){
            case(0): break;
            case(1): //editor keybindings
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { editor.keyUpPressed(); }
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) { editor.keyRightPressed(); }
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { editor.keyDownPressed(); }
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) { editor.keyLeftPressed(); }
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
        editor.down(x,y);
        gui.down(x,y);
    }
    public void mouseDragged(MouseEvent e) { //move
        int x = e.getX();
        int y = e.getY();
        editor.move(x,y);
        gui.move(x,y);
    }
    public void mouseReleased(MouseEvent e) { //up
        int x = e.getX();
        int y = e.getY();
        editor.up(x,y);
        gui.up(x,y);
    }
    public void mouseMoved(MouseEvent e) {
        //update cursor
        int x = e.getX();
        int y = e.getY();
        editor.hover(x,y);
        gui.hover(x,y);
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