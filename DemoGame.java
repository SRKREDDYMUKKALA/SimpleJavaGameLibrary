import java.awt.Color;
import java.awt.event.KeyEvent;

class DemoGame
{
    private SJGL game = new SJGL("Side Scrolling Shooter", 1024, 768);
    
    // Main Method
    public static void main(String[] args)
    {
        SJGL game = new SJGL("Side Scrolling Shooter", 1024, 768);
        game.setBackgroundColor(Color.BLACK);
        game.setFPS(30);
        game.setGravity(0);
        
        // Create Background Sprites
        Sprite backgroundSprite = new Sprite(0, 0, "background_1024_768.jpg");
        backgroundSprite.setHasPhysics(false);
        game.addBackgroundSprite(backgroundSprite);
        
        // Create Sprites
        Sprite player = new Sprite(0, 400, "player.png");
        player.setBorderAction(Sprite.BorderAction.STOP);
        game.addSprite(player);
        game.setCameraTarget(512, 384);
        
        Sprite missile = new Sprite(12000, 400, "missile.png");
        missile.setDX(300);
        game.addSprite(missile);
        
        for(int i=0;i<30;i++)
        {
            double x = (Math.random() * 5000) + 1024;
            double y = (Math.random() * 768);
            
            Sprite enemy = new Sprite(x, y, "enemy.png");
            enemy.setDX(-10 + (Math.random() * -200));
            // enemy.setBoundingBox(true);
            
            game.addSprite(enemy);
        }
        
        // Create sounds
        Sound explosion = new Sound("explosion.wav");
        Sound bgm = new Sound("bgm.wav");
        
        // Create labels
        Label scoreLabel = new Label("Score: 0", 500, 25);
        scoreLabel.setColor(Color.GREEN);
        game.addLabel(scoreLabel);
        
        int score = 0;
        
        // Main Game Loop
        while(true)
        {               
            bgm.play();
             
            // Deal with keypresses
            if(game.isKeyPressed(KeyEvent.VK_UP))
            {
                player.setDY(-100);
                player.setDX(0);
            }
             
            if(game.isKeyPressed(KeyEvent.VK_DOWN))
            {
                player.setDY(100);
                player.setDX(0);
            }
             
            if(game.isKeyPressed(KeyEvent.VK_SPACE))
            {
                if(missile.getX() > game.getCanvasWidth())
                {
                    missile.setY(player.getY()+20);
                    missile.setX(player.getX()+100);
                }
            }
             
            // Mouse clicked
            if(game.getIsMousePressed())
            {
                // System.out.println("MOUSE DOWN: " + game.getMouseX() + " " + game.getMouseY());
            }
            
            // Mouse over
            if(game.isMouseOverSprite(player))
            {
                player.setBoundingBox(true);
            }
            else
            {
                player.setBoundingBox(false);
            }
                
             
            // Check for collisions
            for(Sprite sprite: game.getSprites())
            {
                if(missile.isCollision(sprite))
                {
                    game.getCamera().shake(5);
                    
                    explosion.play();
                    game.removeSprite(sprite);
                        
                    missile.setX(12000);
                        
                    score += 1;
                    scoreLabel.setText("Score: " + score);
                }
                  
                // Move missiles back if off screen
                if(sprite.getX() <= -sprite.getWidth())
                {
                    double x = (Math.random() * 1000) + 1024;
                    double y = (Math.random() * 768);
                    sprite.setX(x);
                    sprite.setY(y);
                }
            }
        }
        

    }
}
