import java.awt.*;

public class Room4 extends Room 
{
	
    private static final int FLOOR_Y = 680;

    private Rect platform1, platform2;

    public Room4() 
    {
        super("room4.png", 5, 100, 596);

        platform1 = new Rect(320, 555, 240, 20);
        platform2 = new Rect(750, 460, 200, 20);
        exitZone  = new Rect(1380, 580,  60, 100);

        // Key sits 36px above platform 2 top surface
        hasKey  = true;
        keyItem = new Rect(790, 424, 60, 24);

        skeletons = new Skeleton[] {
            new Skeleton(430, 596,  80, 1320, 2),
            new Skeleton(900, 596, 500, 1320, 3)
        };
    }

    @Override
    public void handleCollisions(Pirate player) 
    {
        handleFloor(player, FLOOR_Y);
        handlePlatform(player, platform1);
        handlePlatform(player, platform2);
        handleSkeletons(player);
        handleKeyPickup(player);
        player.clampToScreen(1440);
    }
    
}
