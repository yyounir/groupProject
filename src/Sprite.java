import java.awt.Graphics;

public class Sprite {
	String name;
	
	int x;
	int y;
	
	int w;
	int h;
	
	int direction;
	boolean moving = false;
	
	// Constant values that are used to index the animation array to select the correct animation for the direction the soldier is moving
	static final int UP = 0;
	static final int DN = 1;
	static final int LT = 2;
	static final int RT = 3;
	
	Animation[] animation = new Animation[4];
	
	boolean selected = false;
	
	String[] pose;
	
	public Sprite(String name, int x, int y, int w, int h, int direction, String[] pose)
	{
		this.name = name;
		this.pose = pose;
		
		for(int i = 0; i < animation.length; i++) {
			animation[i] = new Animation(name, 7, pose[i], "png");
		}
		
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
		
		this.direction = direction;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected() {
		selected = true;
	}
	
	public void clearSelected() {
		selected = false;
	}
	
	public boolean overlaps(Rect r)
	{
		return (x <= r.x + r.w) &&
			   (y <= r.y + r.h) &&
			   
			   (r.x <= x + w)   &&
			   (r.y <= y + h);	
	}
	
	public boolean contains(int mx, int my)
	{
		return (mx > x)   && 
			   (mx < x+w) && 
			   (my > y)   && 
			   (my < y+h);
	}
	
	public void moveUP(int dy) {
		y -= dy;
		direction = UP;
		moving = true;
	}
	
	public void moveDN(int dy) {
		y += dy;
		direction = DN;
		moving = true;
	}
	
	public void moveLT(int dx) {
		x -= dx;
		direction = LT;
		moving = true;
	}
	
	public void moveRT(int dx) {
		x += dx;
		direction = RT;
		moving = true;
	}	
		
	public void draw(Graphics g) {
		if(moving) {
			g.drawImage(animation[direction].nextImage(), x, y, w, h, null);
	    }
		else {
			g.drawImage(animation[direction].stillImage(), x, y, w, h, null);
		}
		
		new Rect(x, y, w, h).draw(g);
		
		moving = false;
	}
}
