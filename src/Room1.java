import java.awt.*;

public class Room1 extends Room 
{

    private static final int FLOOR_Y = 680;

    private Rect platform1, platform2, platform3;

    public Room1() 
    {
        super("room1.png", 2, 100, 596);

        platform1 = new Rect(180,  545, 200, 20);
        platform2 = new Rect(620,  490, 200, 20);
        platform3 = new Rect(1020, 545, 200, 20);
        exitZone  = new Rect(1380, 580,  60, 100);

        skeletons = new Skeleton[] {
            new Skeleton(280, 596,  80,  750, 2),
            new Skeleton(850, 596, 620, 1300, 2)
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
