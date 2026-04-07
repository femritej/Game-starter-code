import java.awt.*;

/**
 * Platform is a static rectangle the player can stand on.
 *
 * Teaching note: This is a great first class for students to extend.
 * Challenge ideas:
 *   1. Add a colour parameter so each platform can look different.
 *   2. Make a MovingPlatform subclass that shifts left/right over time.
 *   3. Add a "breakable" flag that removes the platform after one landing.
 */
public class Platform {

    private int x, y, width, height;

    // ── Constructor ───────────────────────────────────────────────────────────
    public Platform(int x, int y, int width, int height) {
        this.x      = x;
        this.y      = y;
        this.width  = width;
        this.height = height;
    }

    // ── Draw ──────────────────────────────────────────────────────────────────
    public void draw(Graphics g) {
        // Platform body
        g.setColor(new Color(80, 200, 120));   // green
        g.fillRect(x, y, width, height);

        // Top highlight strip (gives a slight 3-D feel)
        g.setColor(new Color(120, 230, 150));
        g.fillRect(x, y, width, 4);

        // Outline
        g.setColor(new Color(40, 120, 60));
        g.drawRect(x, y, width, height);
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public int getX()      { return x; }
    public int getY()      { return y; }
    public int getWidth()  { return width; }
    public int getHeight() { return height; }
}
