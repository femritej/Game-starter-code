# Java 2D Platformer — Teaching Template

A minimal but fully working 2D platformer built with plain Java (no libraries).
Designed to be readable, hackable, and extended by high school students.

## Requirements
- Java JDK 11 or newer
- VS Code with the **Extension Pack for Java** installed

## Running the Game
1. Open the `java2dgame` folder in VS Code.
2. Press **F5** (or Run → Start Debugging) → choose "Launch Game".
3. Use **Arrow keys** or **WASD** to move, **Space / Up / W** to jump.

## Project Structure
```
src/
  Main.java       ← Entry point — creates the window
  GamePanel.java  ← Game loop, keyboard input, drawing
  Player.java     ← Player physics and rendering
  Platform.java   ← Static platforms the player stands on
```

## How the Game Loop Works
Every 1/60th of a second the Swing Timer fires:
1. `update()` — move the player, apply gravity, check collisions
2. `repaint()` — redraw everything to the screen

This is the same pattern used in Unity (Update()), Godot (_process()), and pygame.

---

## Student Challenges (easiest → hardest)

### Beginner
- [ ] Change the player colour or size (`Player.java` → `draw()`)
- [ ] Add a new platform to the level (`GamePanel.java` → `initGame()`)
- [ ] Change gravity or jump force (constants at top of `Player.java`)

### Intermediate
- [ ] Add a `Coin` class — draw a yellow circle, collect it for +10 points
- [ ] Add a lives system — reset the player when they fall off the screen
- [ ] Make the score display as "Time: X seconds" instead of raw frames

### Advanced
- [ ] Create a `MovingPlatform` subclass that shifts left/right each frame
- [ ] Add a simple `Enemy` that patrols a platform; touching it costs a life
- [ ] Add a second level that loads when the player reaches the right edge
- [ ] Implement a `Camera` class so the world can be bigger than the screen

---

## Key Java Concepts Covered
| Concept | Where to find it |
|---|---|
| Classes & objects | `Player`, `Platform`, `GamePanel` |
| Constructors | All three classes |
| Instance variables | `x`, `y`, `velX`, `velY` in `Player` |
| Constants (`static final`) | `SPEED`, `GRAVITY`, `WIDTH`, etc. |
| Arrays | `Platform[] platforms` in `GamePanel` |
| For-each loops | Collision loop in `Player.checkCollisions()` |
| Booleans & flags | `onGround`, `leftPressed`, etc. |
| Inheritance | `GamePanel extends JPanel` |
| Interfaces | `implements ActionListener, KeyListener` |
| Method overriding | `paintComponent()`, `keyPressed()` |
