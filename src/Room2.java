import java.awt.*;

public class Room2 extends Room 
{

    private static final int FLOOR_Y = 680;

    private Rect platform1, platform2, platform3;

    public Room2() 
    {
        super("room2.png", 3, 100, 596);

        platform1 = new Rect(200, 570, 220, 20);
        platform2 = new Rect(580, 490, 220, 20);
        platform3 = new Rect(970, 570, 220, 20);
        exitZone  = new Rect(1380, 580, 60, 100);

        skeletons = new Skeleton[] {
            new Skeleton(320, 596,  80,  900, 2),
            new Skeleton(900, 596, 650, 1320, 3)
        };
    }

    @Override
    public void handleCollisions(Pirate player) 
    {
        handleFloor(player, FLOOR_Y);
        handlePlatform(player, platform1);
        handlePlatform(player, platform2);
        handlePlatform(player, platform3);
        handleSkeletons(player);
        player.clampToScreen(1440);
    }
    
}
