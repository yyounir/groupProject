import java.awt.*;

public class Rect
{
	int x;
	int y;
	
	int w;
	int h;
	
	int vx;
	int vy;
	
	
	boolean selected = false;
	
	int direction;
	
	// Constant values that are used to index the animation array to select the 
	// correct animation for the direction the soldier is moving
	// Update: this will be for our pirate player
	static final int UP = 0;
	static final int DN = 1;
	static final int LT = 2;
	static final int RT = 3;
	
    // Moving diagonally
	static final int UL = 4;
	static final int DL = 5;
	static final int UR = 6;
	static final int DR = 7;
	
	public Rect(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected()
	{
		selected = true;
	}
	
	public void clearSelected()
	{
		selected = false;
	}
	
	public void toggle()
	{
		selected = ! selected;
	}
	
	public void pushLeft(Rect r)
	{
		int penetration = r.x + r.w - x;
		
		if(penetration < 10)
			
			r.x -= penetration + 1;
	}
	
	public void pushRight(Rect r)
	{
		int penetration = x + w - r.x;
		
		if(penetration < 10)
			
			r.x += penetration + 1;
	}
	
	public void pushUp(Rect r)
	{
		int penetration = r.y + r.h - y ;
		
		if(penetration < 10)
			
			r.y -= penetration + 1;
	}
	
	public void pushDown(Rect r)
	{
		int penetration = y + h - r.y;
		
		if(penetration < 10)
			
			r.y += penetration + 1;
	}
	
	public void pushes(Rect r) {
		pushDown(r);
		pushUp(r);
		pushRight(r);
		pushLeft(r);
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
	
	public void moveBy(int dx, int dy)
	{
		x += dx;
		y += dy;
	}
	
	
	public void draw(Graphics g)
	{
		g.drawRect(x, y, w, h);
	}
	
}
