import java.awt.*;

public class Pirate 
{

    // for collision detection
    public Rect body;

    
    // physics
    public int vy = 0;
    public boolean onGround = false;

    static final int GRAVITY   =  1;
    static final int JUMP_SPEED= -22;
    static final int MAX_FALL  =  18;

    
    // inventory of player
    public boolean hasKey       = false;
    public boolean chestOpened  = false;

    
    // animation
    public Animation[] anim = new Animation[4]; 
    
    public Animation jumpAnim;

    public int direction = Rect.DN;
    public boolean moving = false;
    
    
    
    public Pirate() 
    {
        body = new Rect(100, 596, 48, 84);

        anim[Rect.DN] = new Animation("p_dn", 5, 5, "png");
        anim[Rect.UP] = new Animation("p_up", 5, 5, "png");
        anim[Rect.LT] = new Animation("p_lt", 5, 5, "png");
        anim[Rect.RT] = new Animation("p_rt", 5, 5, "png");
        
        jumpAnim = new Animation("p_jump", 1, 10, "png");
    }

    // movements called by Room.handleInput
    public void moveLT(int speed) 
    {
        body.x -= speed;
        direction = Rect.LT;
        moving = true;
    }

    public void moveRT(int speed) 
    {
        body.x += speed;
        direction = Rect.RT;
        moving = true;
    }

    public void jump() 
    {
        if (onGround)
        {
            vy = JUMP_SPEED;
            onGround = false;
        }
    }

    
    // updating per frame called before collision handling
    public void update() 
    {
        moving = false;
        onGround = false;   // will be set true again by landOn / platform

        vy += GRAVITY;
        if (vy > MAX_FALL) vy = MAX_FALL;
        body.y += vy;
    }

    // Helpers for landing called by Room.handleCollisions

    // Snap onto a hard floor at floorY (like a teleporting effect)
    public void landOn(int floorY) 
    {
        if (body.y + body.h >= floorY) 
        {
            body.y = floorY - body.h;
            vy = 0;
            onGround = true;
        }
    }

    // only land from above when falling
    // prevBottom = position of feet before this frame's vy was applied
    public void landOnPlatform(Rect plat) 
    {
    	if (!body.overlaps(plat)) return;
    	
        int prevBottom = (body.y + body.h) - vy;
        
        if (prevBottom <= plat.y + 6 && vy >= 0) 
        {
            body.y   = plat.y - body.h;
            vy       = 0;
            onGround = true;
        }
    }

    // Pushes player out of a solid wall horizontally
    public void pushOutOfWall(Rect wall) 
    {
        if (!body.overlaps(wall)) return;
        
        int overlapL = (body.x + body.w) - wall.x;
        int overlapR = (wall.x + wall.w) - body.x;
        
        if (overlapL < overlapR) {
            body.x = wall.x - body.w;
        } else {
            body.x = wall.x + wall.w;
        }
    }

    // Keeps the player inside horizontal screen bounds
    public void clampToScreen(int screenW) 
    {
        if (body.x < 0)                body.x = 0;
        if (body.x + body.w > screenW) body.x = screenW - body.w;
    }

    
    public void draw(Graphics g) 
    {
        Image frame;
        
        if (!onGround) {
            frame = jumpAnim.stillImage();
        } 
        else if (moving) {
            frame = anim[direction].nextImage();
        } 
        else {
            frame = anim[direction].stillImage();
        }
        if (frame != null) {
            g.drawImage(frame, body.x, body.y, body.w, body.h, null);
        }
    }
    
}
