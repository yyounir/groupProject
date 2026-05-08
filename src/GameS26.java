import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class GameS26 extends GameBase {		
	
	Rect r = new Rect(800, 100, 20, 300);
	
	public void inGameLoop()
	{
		// Code that executes 60 times per second
		
		// Move User Controlled Objects
		int speed = 5;
		
		if(pressing[SHFT]) {
			speed = 10;
		}
		
		// if(pressing[UP] || pressing[_W]) y.moveUP(speed);
		// if(pressing[DN] || pressing[_S]) y.moveDN(speed);
		// if(pressing[LT] || pressing[_A]) y.moveLT(speed);
		// if(pressing[RT] || pressing[_D]) y.moveRT(speed);
		
		// Move Computer Controlled Objects

		
		// Handle Collisions
		
		
		
		// Update the Screen

	}
	
	public void paint(Graphics g) {	
		g.setColor(Color.BLACK);
		// y.draw(g);
		
		// if(y.overlaps(r))	g.setColor(Color.RED);
		
		r.draw(g);
	}	
}