import java.awt.*;

public class Rect
{
	int x;
	int y;
	
	int w;
	int h;
	
	boolean selected = false;
	
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
	
