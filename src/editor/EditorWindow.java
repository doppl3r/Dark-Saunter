package editor;
import textures.Textures;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class EditorWindow {
	static JFrame jf;
	static Textures tt;
	static EditorPanel panel;
    static int originalWidth;
    static int originalHeight;
	static String title;
	static String version;
    static FileBrowser browser;

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
        originalWidth = 640;
        originalHeight = 480;
        panel = new EditorPanel();
		title = "Level Editor - Doppler Indie Games";
        browser = new FileBrowser(System.getProperty("user.dir"));
		version = "v0.1";
		//build window
        panel.setPreferredSize(new Dimension(originalWidth,originalHeight));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setIconImage(tt.icon);
        jf.setTitle(title+" ["+version+"]");
        jf.getContentPane().add(panel);
        jf.setResizable(true);
        jf.pack();
        jf.setMinimumSize(new Dimension(jf.getWidth(),jf.getHeight()));
        jf.setLocationRelativeTo(null);
        jf.requestFocusInWindow();
        jf.setVisible(true);
        setCursor(3);
        exit();

	}
    public static int getWindowWidth(){ return jf.getWidth(); }
    public static int getWindowHeight(){ return jf.getHeight(); }
    public static int getPanelWidth(){ return panel != null ? panel.getWidth() : originalWidth; }
    public static int getPanelHeight(){ return panel != null ? panel.getHeight() : originalHeight; }
    public static int getOriginalWidth(){ return originalWidth; }
    public static int getOriginalHeight(){ return originalHeight; }
    public static void exit(){
        jf.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (!browser.isSaved()){
                    if (JOptionPane.showConfirmDialog(jf,
                            "Would you like to save first?", "",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        browser.quickSave(false);
                        System.exit(0);
                    }
                }
                else System.exit(0);
            }
        });
        jf.addComponentListener(new ComponentListener() {
            public void componentHidden(ComponentEvent e) { }
            public void componentMoved(ComponentEvent e) { }
            public void componentShown(ComponentEvent e) { }
            public void componentResized(ComponentEvent e) { panel.gui.positionGUI(); }
        });
    }

    public static void setCursor(int type){
        switch(type){
            case(0): jf.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); break;
            case(1): jf.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)); break;
            case(2): jf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); break;
            case(3): jf.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); break;
        }
    }
}
