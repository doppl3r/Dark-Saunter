package editor;
import textures.Textures;
import javax.swing.*;
import java.awt.*;

/**
 */
public class EditorWindow {
	static JFrame jf;
	static Textures tt;
	static EditorPanel panel;
	static int width;
	static int height;
    static int originalWidth;
    static int originalHeight;
	static String title;
	static String version;

    public EditorWindow(){
		//change ui for jFileChooser + other UI settings
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
		//construct frame and panel
		tt = new Textures();
		jf = new JFrame();
        panel = new EditorPanel();
        width = originalWidth = 640;
        height = originalHeight = 480;
		title = "Level Editor - Doppler Indie Games";
		version = "v0.1";
		//build window
        panel.setPreferredSize(new Dimension(width,height));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setIconImage(tt.icon);
        jf.setTitle(title+" ["+version+"]");
        jf.getContentPane().add(panel);
        jf.setResizable(true);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.requestFocusInWindow();
        jf.setVisible(true);
	}
    public static int getWindowWidth(){ return jf.getWidth(); }
    public static int getWindowHeight(){ return jf.getHeight(); }
    public static int getPanelWidth(){ return panel != null ? panel.getWidth() : 0; }
    public static int getPanelHeight(){ return panel != null ? panel.getHeight() : 0; }
    public static void exit(){ System.exit(0); }
}
