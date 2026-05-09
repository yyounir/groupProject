import java.awt.*;


public class Animation
{
	Image[] image;
	
	int current = 0;
	
	int duration;
	int delay;
	
	public Animation(String name, int count, int duration, String filetype)
	{
		image = new Image[count];
		
		this.duration = duration;
		
		delay = duration;
		
		for(int i = 0; i < image.length; i++)
		{
			image[i] = getImage(name + "_" + i + "." + filetype);
		}
	}
	
	
	public Image stillImage()
	{
		return image[0];
	}
	
	
	public Image nextImage()
	{
		delay--;
		
		if(delay == 0)
		{
			if( current == image.length-1)   current = 1;
			else                             current++;
			
			delay = duration;
		}
				
		return image[current];
	}
	
	
	public Image getImage(String filename)
	{
		return Toolkit.getDefaultToolkit().getImage(filename);
	}

}