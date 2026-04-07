import java.awt.*;

/**
 * Player represents the character the user controls.
 *
 * Key concepts demonstrated here:
 *   - Object state  (position, velocity, size)
 *   - Physics       (gravity, jumping, collision)
 *   - Input reading (checking flags set by GamePanel)
 *
 * Teaching challenges:
 *   1. Change SPEED or JUMP_FORCE and observe the difference.
 *   2. Add a double-jump (hint: track how many jumps have been used).
 *   3. Change the player shape/colour to customise your character.
 */
public class Player {

    // ── Tunable physics constants ────────────────────────────────────────────
    public static final float SPEED      = 4.0f;   // horizontal pixels per frame
    public static final float JUMP_FORCE = -13.0f; // negative = upward in screen coords
    public static final float GRAVITY    = 0.5f;   // acceleration downward per frame

    // ── Size ─────────────────────────────────────────────────────────────────
    public static final int W = 30;   // player width  in pixels
    public static final int H = 40;   // player height in pixels

    // ── Position & velocity ───────────────────────────────────────────────────
    private float x, y;           // top-left corner of the player rectangle
    private float velX, velY;     // velocity (pixels per frame)

    // ── State flags ───────────────────────────────────────────────────────────
    private boolean onGround = false;

    // ── Reference back to the panel so we can read key flags ─────────────────
    private GamePanel panel;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Player(float startX, float startY, GamePanel panel) {
        this.x     = startX;
        this.y     = startY;
        this.panel = panel;
    }

    // ── Update: called once per frame ─────────────────────────────────────────
    public void update(Platform[] platforms) {
        handleInput();
        applyPhysics();
        checkCollisions(platforms);
        clampToScreen();
    }

    // ── Read keyboard flags and move accordingly ──────────────────────────────
    private void handleInput() {
        if (panel.leftPressed)  velX = -SPEED;
        else if (panel.rightPressed) velX =  SPEED;
        else velX = 0;   // no key held → stop immediately (no sliding)

        // Jump only when standing on something
        if (panel.jumpPressed && onGround) {
            velY    = JUMP_FORCE;
            onGround = false;
        }
    }

    // ── Apply gravity to vertical velocity, then move ─────────────────────────
    private void applyPhysics() {
        velY += GRAVITY;   // gravity pulls the player down every frame

        x += velX;
        y += velY;
    }

    // ── Check whether the player is sitting on top of a platform ──────────────
    private void checkCollisions(Platform[] platforms) {
        onGround = false;   // assume we're in the air until proven otherwise

        for (Platform p : platforms) {
            // Horizontal overlap?
            boolean overlapX = (x + W > p.getX()) && (x < p.getX() + p.getWidth());

            // Was the player's BOTTOM above the platform's TOP last frame?
            boolean landingFromAbove = (y + H - velY) <= p.getY();

            // Is the player now inside the platform?
            boolean insideY = (y + H >= p.getY()) && (y < p.getY() + p.getHeight());

            if (overlapX && insideY && landingFromAbove) {
                // Snap feet to platform surface
                y        = p.getY() - H;
                velY     = 0;
                onGround = true;
            }
        }
    }

    // ── Keep the player inside the window horizontally ────────────────────────
    private void clampToScreen() {
        if (x < 0)                          x = 0;
        if (x + W > GamePanel.WIDTH)        x = GamePanel.WIDTH - W;

        // If the player falls off the bottom, reset to top
        if (y > GamePanel.HEIGHT) {
            y    = 0;
            velY = 0;
        }
    }

    // ── Draw the player ───────────────────────────────────────────────────────
    public void draw(Graphics g) {
        int ix = (int) x;
        int iy = (int) y;

        // Body
        g.setColor(new Color(70, 180, 255));
        g.fillRect(ix, iy, W, H);

        // Eyes — shift direction based on movement
        g.setColor(Color.WHITE);
        int eyeOffset = (velX > 0) ? 10 : (velX < 0) ? 2 : 6;
        g.fillOval(ix + eyeOffset,     iy + 8, 8, 8);
        g.fillOval(ix + eyeOffset + 2, iy + 8, 4, 4);

        // Pupil
        g.setColor(Color.BLACK);
        g.fillOval(ix + eyeOffset + 2, iy + 10, 4, 4);

        // Outline
        g.setColor(Color.WHITE);
        g.drawRect(ix, iy, W, H);
    }

    // ── Getters (useful for future collision detection with enemies, coins, etc.)
    public float getX()      { return x; }
    public float getY()      { return y; }
    public int   getWidth()  { return W; }
    public int   getHeight() { return H; }
}
