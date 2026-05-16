import java.awt.*;

public class Room3 extends Room 
{

    private static final int FLOOR_Y = 680;

    private Rect dock1, dock2;

    public Room3() 
    {
        super("room3.png", 4, 100, 486);

        dock1    = new Rect(120,  570, 760, 20);
        dock2    = new Rect(1000, 585, 360, 20);
        exitZone = new Rect(1380, 450, 60, 230);   // tall zone covers all dock heights

        skeletons = new Skeleton[] {
            new Skeleton(280, 486, 122, 840, 2),
            new Skeleton(1100, 501, 1002, 1318, 2)
        };
    }

    @Override
    public void handleCollisions(Pirate player) 
    {
        handleFloor(player, FLOOR_Y);
        handlePlatform(player, dock1);
        handlePlatform(player, dock2);
        handleSkeletons(player);
        player.clampToScreen(1440);
    }
    
}
