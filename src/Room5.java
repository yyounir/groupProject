import java.awt.*;

public class Room5 extends Room 
{

    private static final int FLOOR_Y = 680;

    private Rect platform1, platform2;

    public Room5() 
    {
        super("room5.png", -1, 100, 596);   // nextRoom = -1; no exit (should not exit in final room)

        platform1 = new Rect(350, 555, 220, 20);
        platform2 = new Rect(720, 470, 220, 20);
        exitZone  = null;   // explicitly no exit

        // Chest on the floor against the right wall
        hasChest = true;
        chest    = new Rect(1210, 616, 120, 64);

        skeletons = new Skeleton[] {
            new Skeleton(350, 596,  80, 1180, 2),
            new Skeleton(850, 596, 200, 1180, 3)
        };
    }

    @Override
    public void handleCollisions(Pirate player) 
    {
        handleFloor(player, FLOOR_Y);
        handlePlatform(player, platform1);
        handlePlatform(player, platform2);
        handleSkeletons(player);
        handleChest(player);
        player.clampToScreen(1440);
    }
    
}
