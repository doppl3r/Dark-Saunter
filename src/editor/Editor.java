package editor;

import java.awt.*;
import mapping.TileBuffer;

public class Editor {
    private TileBuffer tileBuffer;

    public Editor(){
        tileBuffer = new TileBuffer(EditorWindow.tt.defaultTexture);
    }
    public void draw(Graphics2D g){
        tileBuffer.draw(g);
    }
    public void update(double mod){
        tileBuffer.update(mod);
    }
    //key pressed
    public void keyUpPressed(){  }
    public void keyDownPressed(){  }
    public void keyLeftPressed(){  }
    public void keyRightPressed(){  }

    //key released
    public void keyUpReleased(){  }
    public void keyDownReleased(){  }
    public void keyLeftReleased(){  }
    public void keyRightReleased(){  }

    //mouse actions
    public void down(int x, int y){
        tileBuffer.down(x, y);
    }
    public void move(int x, int y){
        tileBuffer.move(x, y);
    }
    public void up(int x, int y){
        tileBuffer.up(x, y);
    }
    public void hover(int x, int y){
        tileBuffer.hover(x, y);
    }
    public void addRow(){ tileBuffer.addRow(); }
    public void removeRow(){ tileBuffer.removeRow(); }
    public void addCol(){ tileBuffer.addCol(); }
    public void removeCol(){ tileBuffer.removeCol(); }
}
