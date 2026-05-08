import java.awt.Toolkit;
import java.awt.*;

public class Animation {
	Image[] image = new Image[7];
	String direction = "dn";
	int current = 0;
	int duration = 10;
	int delay;
	String name;
	
	public Animation(String name, int duration, String direction, String filetype) {
		this.name = name;
		this.direction = direction;
		this.duration = duration;
		delay = duration;
		
		for(int i =0; i< image.length; i++) {
			image[i] = Toolkit.getDefaultToolkit().getImage(name + "-" + this.direction + i + "." + filetype);
		}
	}
	
	public Image stillImage() {
		return image[0];  
	}
	
	public Image nextImage() {
		
		delay--;
		if(name.equals("y") || (name.equals("v"))) {
			if(delay == 0) { 
				if(direction.equals("dn") || direction.equals("up")) { 
					if(current == 6) 	current = 0;
					else 				current++;
				}
				else {
					if(current == 4) 	current = 0;
					else 				current++;
				}
				delay = duration;
			}
		}
		
		
		return image[current]; // return current image
	}
}
