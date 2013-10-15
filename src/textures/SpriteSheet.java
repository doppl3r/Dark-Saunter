package textures;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 */
public class SpriteSheet {
	private double rate;
	private double currentFrame;
	private boolean finished;
	private boolean centered;
	private int imgWidth;
	private int imgHeight;
	private int spriteWidth;
	private int spriteHeight;
	private int hFrames;
	private int vFrames;
	private int frames;
	private int x;
	private int y;
	private Image image;
	private Rect spriteRect, destRect;

	public SpriteSheet(Image image, int hFrames, int vFrames, double rate){
		this.hFrames=hFrames;
		this.vFrames=vFrames;
		this.image=image;
		this.rate=rate;
		spriteWidth =imgWidth =image.getWidth(null) /this.hFrames;
		spriteHeight=imgHeight=image.getHeight(null)/this.vFrames;
		
		frames = (int)(hFrames*vFrames);
		spriteRect = new Rect(0,0,imgWidth,imgHeight);
		destRect = new Rect();
	}
	public boolean animate(){
		finished = false;
		if (currentFrame+rate < frames) currentFrame+=rate;
		else{
			finished = true;
			currentFrame = 0;
		}
		//adjust sprite location
		spriteRect.top = (((int)currentFrame)/hFrames)*spriteHeight;
		spriteRect.bottom = spriteRect.top + spriteHeight;
		spriteRect.left = (((int)currentFrame)%hFrames)*spriteWidth;
		spriteRect.right = spriteRect.left + spriteWidth;
		return finished;
    }
	public void animate(int frame){
        currentFrame=frame;
		//adjust sprite location
		spriteRect.top = ((frame)/hFrames)*spriteHeight;
		spriteRect.bottom = spriteRect.top + spriteHeight;
		spriteRect.left = ((frame)%hFrames)*spriteWidth;
		spriteRect.right = spriteRect.left + spriteWidth;
	}
	public void animate(int start, int end){
		end++;
		//animates between a certain frame
		if (currentFrame < start) currentFrame = start;
		if (currentFrame+rate < end-1) currentFrame+=rate;
		else{
			finished = true;
			currentFrame = start;
		}
		//adjust sprite location
		spriteRect.top = (((int)currentFrame)/hFrames)*spriteHeight;
		spriteRect.bottom = spriteRect.top + spriteHeight;
		spriteRect.left = (((int)currentFrame)%hFrames)*spriteWidth;
		spriteRect.right = spriteRect.left + spriteWidth;
	}
	public void build(double x, double y, int xSize, int ySize){
		update(x,y,xSize,ySize);
		this.x=(int)x;
		this.y=(int)y;
	}
	public void draw(Graphics2D g){
		g.drawImage(getImage(), 
				getDestRectLeft(), getDestRectTop(),
				getDestRectRight(), getDestRectBottom(), 
				getSpriteLeft(), getSpriteTop(), 
				getSpriteRight(), getSpriteBottom(), null);
	}
	public void update(double x, double y){
		//texture placement
		if (centered){
			destRect.top = (int)(y-(imgHeight/2));
			destRect.bottom = destRect.top + imgHeight;
			destRect.left = (int)(x-(imgWidth/2));
			destRect.right = destRect.left + imgWidth;
		}
		else{
			destRect.top = (int)y;
			destRect.bottom = destRect.top + imgHeight;
			destRect.left = (int)x;
			destRect.right = destRect.left + imgWidth;
		}
	}
	public void update(double x, double y, int xSize, int ySize){
		imgWidth = xSize;
		imgHeight = ySize;
		update(x,y);
	}
	public void updateSprite(int x1, int y1, int x2, int y2){
		spriteRect.top = y1;
		spriteRect.right = x2;
		spriteRect.bottom = y2;
		spriteRect.left = x1;
	}
	public void reflect(){
		int oldLeft = spriteRect.left;
		spriteRect.left = spriteRect.right;
		spriteRect.right = oldLeft;
	}
	public void flip(){
		int oldTop = spriteRect.top;
		spriteRect.top = spriteRect.bottom;
		spriteRect.bottom = oldTop;
	}
	public void resize(int imgWidth, int imgHeight){
		this.imgWidth=imgWidth;
		this.imgHeight=imgHeight;
	}
	public void resetDest(){
		destRect.top = 0;
		destRect.bottom = 0;
		destRect.left = 0;
		destRect.right = 0;
	}
	public void setImage(Image image){ this.image = image; }
	public Image getImage(){ return image; }
	public Rect getDestRect(){ return destRect; }
	public Rect getSpriteRect(){ return spriteRect; }
	public int getImageWidth(){ return imgWidth; }
	public int getImageHeight(){ return imgHeight; }
	public int getHFrames(){ return hFrames; }
	public int getVFrames(){ return vFrames; }
	public int getCurrentFrame(){ return (int)currentFrame; }
	public int getSpriteWidth(){ return spriteWidth; }
	public int getSpriteHeight(){ return spriteHeight; }
	public int getSpriteTop(){ return spriteRect.top; }
	public int getSpriteRight(){ return spriteRect.right; }
	public int getSpriteBottom(){ return spriteRect.bottom; }
	public int getSpriteLeft(){ return spriteRect.left; }
	public int getDestRectTop(){ return destRect.top; }
	public int getDestRectRight(){ return destRect.right; }
	public int getDestRectBottom(){ return destRect.bottom; }
	public int getDestRectLeft(){ return destRect.left; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	public double getRate(){ return rate; }
	public void center(){ centered = true; }
	public boolean isCentered(){ return centered; }
	public boolean isFinished(){ return finished; }
	public boolean isAnimating(){ return currentFrame > 0; }
	//basic rectangle class
	public class Rect {
		public int left;
		public int right;
		public int top;
		public int bottom;
		
		public Rect(){ 
			
		}
		public Rect(int left, int top, int right, int bottom){
			this.left=left;
			this.top=top;
			this.right=right;
			this.bottom=bottom;
		}
	}
}
