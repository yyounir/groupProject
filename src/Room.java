import java.awt.*;

public abstract class Room extends RoomBase 
{

    public static Room   current;
    public static Room[] room = new Room[6];

    // Per-room data
    protected Image background;
    public    int   nextRoomIndex;   // -1 = no exit (final room)
    public    int   entryX;
    public    int   entryY;

    // Right-side exit zone
    // player overlaps this to advance
    public Rect exitZone;
    
    // Using protected as Room0 - Room5 are subclasses and unlike public and private,
    // protected makes it so that any subclass that extends Room can use these declarations

    protected Rect[] platforms;
    protected Rect[] walls;
    protected Skeleton[] skeletons;

    // Key
    protected boolean hasKey     = false;
    protected Rect    keyItem;   // collision / display rect
    protected Image   keyImg;

    // Chest
    protected boolean hasChest   = false;
    protected Rect    chest;
    protected Image   chestLockedImg;
    protected Image   chestOpenImg;

    // Jump latch 
    // must release jump key before jumping again
    private boolean jumpLatch = false;

    // Room constructor
    protected Room(String bgFile, int nextRoom, int entryX, int entryY) 
    {
        this.background    = Toolkit.getDefaultToolkit().getImage(bgFile);
        this.nextRoomIndex = nextRoom;
        this.entryX        = entryX;
        this.entryY        = entryY;

        keyImg        = Toolkit.getDefaultToolkit().getImage("key.png");
        chestLockedImg= Toolkit.getDefaultToolkit().getImage("chest_locked.png");
        chestOpenImg  = Toolkit.getDefaultToolkit().getImage("chest_open.png");
    }

    // input handling
    public void handleInput(boolean[] pressing, Pirate player) 
    {
        int speed = 5;
        if (pressing[SHFT]) speed = 9;

        if (pressing[LT] || pressing[_A]) player.moveLT(speed);
        if (pressing[RT] || pressing[_D]) player.moveRT(speed);

        // Jump latch statements
        boolean jumpKey = pressing[SPACE] || pressing[UP] || pressing[_W];
        
        if (jumpKey && !jumpLatch && player.onGround) 
        {
            player.jump();
            jumpLatch = true;
        }
        if (!jumpKey) 
        {
            jumpLatch = false;
        }
    }

    
    // Abstract collision
    // Implemented by each room subclass
    public abstract void handleCollisions(Pirate player);

    
    // Shared collision helpers

    // Snap player onto a hard floor at floorY (teleporting effect)
    protected void handleFloor(Pirate player, int floorY) {
        player.landOn(floorY);
    }

    // One-way platform: player can jump through from below and lands on top when falling.
    protected void handlePlatform(Pirate player, Rect plat) {
        player.landOnPlatform(plat);
    }

    // pushes player out horizontally
    protected void handleWall(Pirate player, Rect wall) {
        player.pushOutOfWall(wall);
    }

    // Update all skeletons and push the player if overlapping
    protected void handleSkeletons(Pirate player) 
    {
        if (skeletons == null) return;
        
        for (Skeleton sk : skeletons) 
        {
            sk.update();
            sk.pushPlayer(player);
        }
    }

    // Check if player walks over the key and picks it up.
    protected void handleKeyPickup(Pirate player) 
    {
        if (!hasKey || keyItem == null || player.hasKey) return;
        
        if (player.body.overlaps(keyItem)) {
            player.hasKey = true;
        }
    }

    // Check if player opens the chest
    protected void handleChest(Pirate player) 
    {
        if (!hasChest || chest == null || player.chestOpened) return;
        
        if (player.body.overlaps(chest) && player.hasKey) {
            player.chestOpened = true;
        }
    }

    // draw method
    public void draw(Graphics g, Pirate player) 
    {
        // drawing background fills entire 1440 x 810 screen of our Applet
        g.drawImage(background, 0, 0, 1440, 810, null);

        // this will draw the key on the screen until it is picked up 
        if (hasKey && keyItem != null && !player.hasKey) {
            g.drawImage(keyImg, keyItem.x, keyItem.y, keyItem.w, keyItem.h, null);
        }

        // draw the chest
        if (hasChest && chest != null) {
            Image ci = player.chestOpened ? chestOpenImg : chestLockedImg;
            g.drawImage(ci, chest.x, chest.y, chest.w, chest.h, null);
        }

        // drawing enemy skeletons
        if (skeletons != null) {
            for (Skeleton sk : skeletons) sk.draw(g);
        }

        // draws the pirate on top
        player.draw(g);
    }
}
