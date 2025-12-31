import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class car extends JPanel implements ActionListener, KeyListener {

    private int carX = 220;          
    private int obstacleY = -100;    
    private int obstacleX = (int)(Math.random() * 400);
    private Timer timer;
    private boolean gameOver = false;

    private int score = 0;      // NEW: Score variable

    public car() {
        timer = new Timer(15, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Track background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 500, 700);

        // Road lines
        g.setColor(Color.WHITE);
        for (int i = 0; i < 700; i += 50)
            g.fillRect(245, i, 10, 30);

        // Player car
        g.setColor(Color.YELLOW);
        g.fillRect(carX, 580, 60, 100);

        // Obstacle car
        g.setColor(Color.RED);
        g.fillRect(obstacleX, obstacleY, 60, 100);

        // Score display
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 40);

        // Game Over text
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER!", 120, 300);

            // Display final score
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Final Score: " + score, 150, 350);

            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            obstacleY += 10; // Move obstacle

            if (obstacleY > 700) {
                // Reset obstacle
                obstacleY = -100;
                obstacleX = (int)(Math.random() * 400);

                score++; // NEW: increase score when obstacle resets
            }

            // Collision detection
            if (new Rectangle(carX, 580, 60, 100).intersects(
                new Rectangle(obstacleX, obstacleY, 60, 100))) {
                gameOver = true;
            }

            repaint();
        }
    }

    // Controls
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && carX > 20) {
                carX -= 20;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && carX < 420) {
                carX += 20;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Race Game");
        CarRaceGame game = new CarRaceGame();

        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

