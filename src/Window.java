import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.*;


import textures.Textures;

/**
 */
public class Window {
	static JFrame jf;
	static Textures tt;
	static Panel panel;
	static int width;
	static int height;
    static int originalWidth;
    static int originalHeight;
	static String title;
	static String version;
    static boolean fullScreen;
    static boolean stretched;
    static Toolkit tk = Toolkit.getDefaultToolkit();
	
	public Window(){
		//change ui for jFileChooser + other UI settings
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		catch (ClassNotFoundException e) { e.printStackTrace(); } 
		catch (InstantiationException e) { e.printStackTrace(); } 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
		//construct frame and panel
		tt = new Textures();
		jf = new JFrame();
        width = originalWidth = 640;
        height = originalHeight = 480;
        //stretched = true;
		panel = new Panel();
		title = "Dark Saunter - Doppler Indie Games";
		version = "v0.1";
		//build window
		jf.setIconImage(tt.icon_hd);
		jf.setTitle(title+" ["+version+"]");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().add(panel);
		jf.setResizable(false);
		jf.pack();
		jf.add(panel);
		jf.setLocationRelativeTo(null);
		jf.requestFocusInWindow();
        toggleFullScreen();
	}
    public static int getWidth(){ return width; }
    public static int getHeight(){ return height; }
    public static int getOriginalWidth(){ return originalWidth; }
    public static int getOriginalHeight(){ return originalHeight; }
    public static boolean isStretched(){ return stretched; }
    public static void toggleStretched(){ stretched = !stretched; }
    public static void toggleFullScreen(){
        if (fullScreen){
            jf.dispose();
            jf.setUndecorated(false);
            jf.setVisible(true);
            width = originalWidth;
            height = originalHeight;
            fullScreen = false;
            refresh();
            jf.setLocationRelativeTo(null);
        }
        else{
            jf.dispose();
            jf.setUndecorated(true);
            jf.setVisible(true);
            originalWidth = width;
            originalHeight = height;
            width = (int)tk.getScreenSize().getWidth();
            height = (int)tk.getScreenSize().getHeight();
            fullScreen = true;
            refresh();
            jf.setLocation(new Point(0, 0));
        }
    }
    public static void resize(int w, int h){ width=w; height=h; }
    public static void updateSize(){ width = jf.getWidth(); height = jf.getHeight(); }
    public static void refresh(){
        jf.setPreferredSize(new Dimension(width, height));
        jf.pack();
    }
}
