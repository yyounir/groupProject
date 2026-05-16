import java.awt.*;

public class Skeleton 
{

    public Rect body;

    private int leftBound;
    private int rightBound;
    private int speed;
    private int dir;  // Rect.LT or Rect.RT

    // Animation
    private static final int FRAMES       = 5;
    private static final int FRAME_DELAY  = 6;  // ticks per frame
    private int animFrame = 0;
    private int animTick  = 0;

    private Image[] framesLt = new Image[FRAMES];
    private Image[] framesRt = new Image[FRAMES];

    // Skeleton constructor
    public Skeleton(int x, int y, int leftBound, int rightBound, int speed) 
    {
        body = new Rect(x, y, 48, 84);
        
        this.leftBound  = leftBound;
        this.rightBound = rightBound;
        this.speed      = speed;
        this.dir        = Rect.RT;

        for (int i = 0; i < FRAMES; i++) 
        {
            framesLt[i] = Toolkit.getDefaultToolkit().getImage("sk_lt_" + i + ".png");
            framesRt[i] = Toolkit.getDefaultToolkit().getImage("sk_rt_" + i + ".png");
        }
    }

    //speed defaults to 2
    public Skeleton(int x, int y, int leftBound, int rightBound) 
    {
        this(x, y, leftBound, rightBound, 2);
    }

    
    // updating per frame
    public void update() 
    {
        // moving
        if (dir == Rect.RT) {
            body.x += speed;
            if (body.x + body.w >= rightBound)  dir = Rect.LT;
        } else {
            body.x -= speed;
            if (body.x <= leftBound)  dir = Rect.RT;
        }

        // Advance animation
        animTick++;
        if (animTick >= FRAME_DELAY) {
            animTick  = 0;
            animFrame = (animFrame + 1) % FRAMES;
        }
    }

    // Push the player away if the skeleton overlaps them
    public void pushPlayer(Pirate player) 
    {
        if (!body.overlaps(player.body)) return;
        
        int skelCX   = body.x + body.w / 2;
        
        int playerCX = player.body.x + player.body.w / 2;
        
        if (playerCX < skelCX) {
            player.body.x -= 6;
        } else {
            player.body.x += 6;
        }
    }

    
    public void draw(Graphics g) 
    {
        Image[] frames = (dir == Rect.RT) ? framesRt : framesLt;
        
        Image frame = frames[animFrame];
        
        if (frame != null) 
        {
            g.drawImage(frame, body.x, body.y, body.w, body.h, null);
        }
    }
    
}
