import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GamePanel is the heart of the game.
 * It handles:
 *   - The GAME LOOP  (update logic + repaint on a timer)
 *   - KEYBOARD INPUT (pressing/releasing keys)
 *   - DRAWING        (background, platforms, player, HUD)
 *
 * Teaching note: This pattern — update() then repaint() on a fixed timer —
 * is used in almost every 2-D game engine. Learn it here and you'll
 * recognise it in Unity, Godot, and beyond.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {

    // ── Screen constants ────────────────────────────────────────────────────
    public static final int WIDTH  = 800;
    public static final int HEIGHT = 500;
    public static final int FPS    = 60;           // frames per second
    public static final int DELAY  = 1000 / FPS;  // milliseconds per frame

    // ── Game objects ────────────────────────────────────────────────────────
    private Player   player;
    private Platform[] platforms;

    // ── Game state ──────────────────────────────────────────────────────────
    private int score = 0;
    private Timer gameTimer;   // Swing Timer fires every DELAY ms

    // ── Input flags (true while key is held down) ───────────────────────────
    boolean leftPressed  = false;
    boolean rightPressed = false;
    boolean jumpPressed  = false;

    // ── Constructor ─────────────────────────────────────────────────────────
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(30, 30, 60));   // dark-blue sky
        setFocusable(true);
        addKeyListener(this);

        initGame();

        // Start the game loop
        gameTimer = new Timer(DELAY, this);
        gameTimer.start();
    }

    // ── Set up / reset all game objects ─────────────────────────────────────
    private void initGame() {
        // Player starts near the bottom-left
        player = new Player(100, 350, this);

        // Build the level — feel free to add more platforms!
        // new Platform(x, y, width, height)
        platforms = new Platform[] {
            new Platform(0,   460, 800, 40),   // ground (full width)
            new Platform(150, 360, 120, 20),   // low platform
            new Platform(350, 280, 150, 20),   // mid platform
            new Platform(580, 200, 120, 20),   // high platform
            new Platform(50,  240, 100, 20),   // left ledge
            new Platform(680, 340, 80,  20),   // right step
        };

        score = 0;
    }

    // ── Called by the Timer every DELAY ms (this IS the game loop) ──────────
    @Override
    public void actionPerformed(ActionEvent e) {
        update();   // 1. Move everything
        repaint();  // 2. Draw the new state
    }

    // ── Game logic update ────────────────────────────────────────────────────
    private void update() {
        player.update(platforms);

        // Simple scoring: gain 1 point per frame the player is alive
        score++;
    }

    // ── Painting (called automatically by repaint()) ─────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);                // clear screen

        // Draw platforms
        for (Platform p : platforms) {
            p.draw(g);
        }

        // Draw player
        player.draw(g);

        // Draw HUD (Heads-Up Display)
        drawHUD(g);
    }

    // ── HUD: score and controls reminder ─────────────────────────────────────
    private void drawHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 25);

        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.drawString("Arrow keys / WASD to move  |  Space or Up to jump", 10, HEIGHT - 10);
    }

    // ── KeyListener methods ───────────────────────────────────────────────────
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) leftPressed  = true;
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) rightPressed = true;
        if (key == KeyEvent.VK_UP    || key == KeyEvent.VK_W
                                     || key == KeyEvent.VK_SPACE) jumpPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT  || key == KeyEvent.VK_A) leftPressed  = false;
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) rightPressed = false;
        if (key == KeyEvent.VK_UP    || key == KeyEvent.VK_W
                                     || key == KeyEvent.VK_SPACE) jumpPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) { /* not needed */ }
}
