import java.awt.*;

public class GameS26 extends GameBase 
{

    static final int TITLE   = 0;
    static final int PLAYING = 1;
    static final int WIN     = 2;

    int gameState = TITLE;

    // One-time initialization flag
    // This is so that setup() is the first 'tick' of the game loop
    boolean initialized = false;

    // create pirate player object instance 
    Pirate player;

    // putting up screen images
    Image titleImg;
    Image winImg;

    // setting fonts as writing text without it it changes sizes 
    // which is not what we want
    Font hudFont;
    Font titleFont;
    Font subtitleFont;
    Font winFont;

    // Title screen animated blink timer
    // When user runs game they will see PRESS SPACE TO BEGIN blinking in and out effect
    int blinkTick = 0;
    boolean blinkOn = true;

    // win screen tick for the text 'Press  R  to play again'
    int winTick = 0;

    
    //  first tick of inGameLoop and delayed to first frame

    void setup() 
    {
        player = new Pirate();
        
        titleImg = Toolkit.getDefaultToolkit().getImage("title.png");
        winImg   = Toolkit.getDefaultToolkit().getImage("win.png");

        hudFont      = new Font("Monospaced", Font.BOLD, 22);
        titleFont    = new Font("Serif",      Font.BOLD, 84);
        subtitleFont = new Font("Monospaced", Font.BOLD, 28);
        winFont      = new Font("Serif",      Font.BOLD, 96);

        // Create all rooms and register them
        Room.room[0] = new Room0();
        Room.room[1] = new Room1();
        Room.room[2] = new Room2();
        Room.room[3] = new Room3();
        Room.room[4] = new Room4();
        Room.room[5] = new Room5();

        Room.current  = Room.room[0];
        player.body.x = Room.current.entryX;
        player.body.y = Room.current.entryY;
    }
    
    // Game Loop called 60 frames per second via GameBase thread
    public void inGameLoop() 
    {
        if (!initialized) 
        {
            setup();
            initialized = true;
        }

        switch (gameState) 
        {

            case TITLE:
                blinkTick++;
                if (blinkTick >= 35) 
                { 
                	blinkTick = 0; 
                	blinkOn = !blinkOn; 
                }
                if (pressing[SPACE]) 
                {
                    gameState = PLAYING;
                }
                break;

            case PLAYING:
                Room.current.handleInput(pressing, player);
                player.update();
                Room.current.handleCollisions(player);
                checkRoomTransition();
                checkWinCondition();
                break;

            case WIN:
                winTick++;
                if (pressing[_R]) 
                {
                    restartGame();
                }
                break;
        }
    }

    
    // Room Transition

    void checkRoomTransition() 
    {
        if (Room.current.exitZone == null) 				  return;
        if (!player.body.overlaps(Room.current.exitZone)) return;

        int next = Room.current.nextRoomIndex;
        if (next < 0 || next >= Room.room.length || Room.room[next] == null) return;

        Room.current  = Room.room[next];
        player.body.x = Room.current.entryX;
        player.body.y = Room.current.entryY;
        player.vy     = 0;
        player.onGround = false;
    }

    // Win checks 
    void checkWinCondition() 
    {
        if (Room.current == Room.room[5] && player.chestOpened)    gameState = WIN;
    }

    
    void restartGame() 
    {
        player = new Pirate();

        Room.room[0] = new Room0();
        Room.room[1] = new Room1();
        Room.room[2] = new Room2();
        Room.room[3] = new Room3();
        Room.room[4] = new Room4();
        Room.room[5] = new Room5();

        // where the player will start
        Room.current = Room.room[0];
        
        player.body.x = Room.current.entryX;
        player.body.y = Room.current.entryY;
        player.vy = 0;
        player.onGround = false;
        winTick = 0;
        blinkTick = 0;
        blinkOn = true;
        gameState = PLAYING;
    }

    // paint method gets called by GameBase after double-buffer flip
    // as drawing right on the main screen would cause flickering issues
    public void paint(Graphics g) 
    {
        if (!initialized) 
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1440, 810);
            return;
        }

        switch (gameState) 
        {
            case TITLE:   
            	paintTitle(g);   
            	break;
            	
            case PLAYING: 
            	paintGame(g);    
            	break;
            	
            case WIN:     
            	paintWin(g);     
            	break;
        }
    }

    // Title screen
    void paintTitle(Graphics g) 
    {
        g.drawImage(titleImg, 0, 0, 1440, 810, null);

        // main game title
        g.setFont(titleFont);
        String title = "PIRATE'S QUEST";
        FontMetrics fm = g.getFontMetrics();
        int tw = fm.stringWidth(title);

        // Shadows of title
        // gives a better piraty feel to the game
        g.setColor(new Color(40, 20, 0));
        g.drawString(title, (1440 - tw) / 2 + 5, 220 + 5);
        
        // Gold text
        g.setColor(new Color(220, 180, 50));
        g.drawString(title, (1440 - tw) / 2, 220);

        g.setFont(subtitleFont);
        String sub = "Find the key. Open the chest. Win.";
        int sw = g.getFontMetrics().stringWidth(sub);
        g.setColor(new Color(180, 160, 110));
        g.drawString(sub, (1440 - sw) / 2, 270);

        // where we use the blinking effect on title screen
        if (blinkOn) 
        {
            g.setFont(subtitleFont);
            String prompt = ">>  PRESS SPACE TO BEGIN  <<";
            int pw = g.getFontMetrics().stringWidth(prompt);
            g.setColor(new Color(240, 210, 80));
            g.drawString(prompt, (1440 - pw) / 2, 726);
        }

        // Showing the user the controls right on title screen
        g.setFont(new Font("Monospaced", Font.PLAIN, 18));
        g.setColor(new Color(160, 148, 105));
        String ctrl = "A/D or ←/→  to move   |   SPACE / ↑ / W  to jump   |   SHIFT  to run";
        int cw = g.getFontMetrics().stringWidth(ctrl);
        g.drawString(ctrl, (1440 - cw) / 2, 750);
    }

    // Drawing the first room of game along with player when game is started
    void paintGame(Graphics g) 
    {
        Room.current.draw(g, player);
        drawHUD(g);
    }

    // Drawing game HUD overlay
    void drawHUD(Graphics g) 
    {
        // Semi-transparent dark pill in top-left
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRoundRect(10, 10, 370, 52, 14, 14);

        // Room indicator
        int roomNum = currentRoomNumber();
        g.setFont(hudFont);
        g.setColor(new Color(255, 200, 50));
        g.drawString("ROOM " + roomNum + " / 6", 24, 44);

        // Divider
        g.setColor(new Color(150, 120, 50));
        g.drawLine(168, 18, 168, 56);

        // Key indicator for when player grabs key
        if (player.hasKey) 
        {
            g.setColor(new Color(255, 215, 40));
            g.drawString("  KEY: GOT IT", 178, 44);
        } 
        else 
        {
            g.setColor(new Color(160, 148, 110));
            g.drawString("  KEY: ?", 178, 44);
        }

        // Context hint in top-right
        String hint = getContextHint();
        
        if (hint != null) 
        {
            g.setFont(new Font("Monospaced", Font.PLAIN, 17));
            FontMetrics fm = g.getFontMetrics();
            int hw = fm.stringWidth(hint) + 20;
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRoundRect(1440 - hw - 10, 10, hw, 38, 10, 10);
            g.setColor(new Color(200, 185, 130));
            g.drawString(hint, 1440 - hw - 1, 34);
        }
    }

    int currentRoomNumber() 
    {
        for (int i = 0; i < Room.room.length; i++) 
        {
            if (Room.room[i] == Room.current) return i + 1;
        }
        return 1;
    }

    String getContextHint() {
        if (Room.current == Room.room[4] && !player.hasKey)  return "Find the key!";
        if (Room.current == Room.room[5] && !player.hasKey)  return "You need the key!";
        if (Room.current == Room.room[5] &&  player.hasKey && !player.chestOpened)
            return "Open the chest!";
        return null;
    }

    // Draws the win screen
    void paintWin(Graphics g) 
    {
        g.drawImage(winImg, 0, 0, 1440, 810, null);

        // Big winner text with shadow
        g.setFont(winFont);
        String win = "YOU WIN!";
        FontMetrics fm = g.getFontMetrics();
        int ww = fm.stringWidth(win);

        g.setColor(new Color(80, 40, 0));
        g.drawString(win, (1440 - ww) / 2 + 6, 368 + 6);
        
        // Gradient-like effect
        
        g.setColor(new Color(255, 195, 40));
        g.drawString(win, (1440 - ww) / 2, 368);
        g.setColor(new Color(255, 240, 130));
        g.drawString(win, (1440 - ww) / 2 - 2, 362);

        // Subtitle
        g.setFont(subtitleFont);
        String sub = "The treasure is yours, Captain.";
        int sw = g.getFontMetrics().stringWidth(sub);
        g.setColor(new Color(230, 200, 120));
        g.drawString(sub, (1440 - sw) / 2, 425);

        // restart prompt which also has blinking effect
        if ((winTick / 35) % 2 == 0) 
        {
            g.setFont(subtitleFont);
            String rp = "Press  R  to play again";
            int rw = g.getFontMetrics().stringWidth(rp);
            g.setColor(new Color(200, 175, 80));
            g.drawString(rp, (1440 - rw) / 2, 618);
        }
        
    }

    
    
    
    
    
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
