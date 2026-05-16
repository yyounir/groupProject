import java.awt.*;

public class Room0 extends Room 
{

    private static final int FLOOR_Y = 680;

    private Rect platform1;

    public Room0() 
    {
        super("room0.png", 1, 100, 596);

        platform1 = new Rect(450, 560, 240, 20);
        exitZone  = new Rect(1380, 580, 60, 100);

        skeletons = new Skeleton[] {
            new Skeleton(620, 596, 200, 1300, 2)
        };
    }

    @Override
    public void handleCollisions(Pirate player) 
    {
        handleFloor(player, FLOOR_Y);
        handlePlatform(player, platform1);
        handleSkeletons(player);
        player.clampToScreen(1440);
    }
    
}
