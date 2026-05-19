# 🏴‍☠️ Pirate's Quest

A 2D side-scrolling platformer built in Java where you play as a swashbuckling pirate navigating six treacherous rooms, dodging skeleton enemies, collecting a key, and opening a treasure chest to claim victory.

**Made by:** Martin, Hamza, Jonathan, and Yasir

---

## 📹 Video Explanation
https://www.loom.com/share/3ed5218efa46418daa306a2dc24893f8 <br>
https://www.loom.com/share/03a4675920184847950cc702e59c9156

---

## 🎮 Gameplay

You are a pirate on a quest for treasure. To win, you must:

1. **Navigate** through six increasingly challenging rooms
2. **Avoid** patrolling skeleton enemies that push you around
3. **Find the key** hidden in Room 5 — jump up to the platform to grab it
4. **Open the chest** in the final room to claim your reward

The game runs at ~60 FPS using a double-buffered rendering loop to keep everything smooth and flicker-free.

---

## 🕹️ Controls

| Action | Keys |
|--------|------|
| Move Left | `A` or `←` |
| Move Right | `D` or `→` |
| Jump | `Space`, `↑`, or `W` |
| Play Again (Win Screen) | `R` |

---

## 🗺️ Rooms

| Room | Description |
|------|-------------|
| **Room 0** | Tutorial-style intro room with one platform and one skeleton |
| **Room 1** | Three platforms, two skeletons patrolling the floor |
| **Room 2** | Similar layout to Room 1, with a faster skeleton |
| **Room 3** | Dock-themed room — walk across the dock platforms with two skeletons |
| **Room 4** | Two platforms; the key floats above the upper platform — grab it! |
| **Room 5** | Final showdown — two skeletons guard the locked chest on the right wall |

Each room has an **exit zone** on the right edge. Walk into it to advance to the next room.

---

## 👾 Enemies

**Skeletons** patrol a fixed horizontal range and bounce back and forth. If one walks into you, you get shoved sideways — no health system, but being pushed into a wall or off a platform can cost you time.

Each skeleton has a configurable:
- Patrol range (left and right bounds)
- Movement speed
- Animated walk cycle (5 frames, left and right facing)

---

## 🏗️ Project Structure

```
├── GameBase.java       # Core applet loop, input handling, double-buffer rendering
├── GameS26.java        # Main game class — state machine (Title / Playing / Win)
├── Room.java           # Abstract base class for all rooms
├── RoomBase.java       # Shared key constants for input
├── Room0.java          # Room implementations (0–5)
├── Room1.java
├── Room2.java
├── Room3.java
├── Room4.java
├── Room5.java
├── Pirate.java         # Player entity — movement, physics, animation, inventory
├── Skeleton.java       # Enemy entity — patrol AI, animation, player push
├── Animation.java      # Sprite animation utility (frame cycling)
└── Rect.java           # Rectangle utility — collision detection, overlap, push helpers
```

---

## ⚙️ Technical Details

- **Language:** Java (Applet-based)
- **Rendering:** Double-buffered via off-screen `Image` + `Graphics` — eliminates flicker
- **Physics:** Simple gravity (`+1` per tick), max fall speed cap, jump velocity (`-22`), one-way platform landing
- **Collision:** AABB (Axis-Aligned Bounding Box) using `Rect.overlaps()`, with directional push resolution
- **Animation:** Frame-based sprite sheets, per-direction animations (up/down/left/right), separate jump sprite
- **Game States:** `TITLE` → `PLAYING` → `WIN`, with `R` to restart from win screen
- **HUD:** Room counter, key status indicator, and context hints ("Find the key!", "Open the chest!")

---

## 🖼️ Assets Required

The game expects the following image files in the working directory:

**Backgrounds:** `room0.png` through `room5.png`

**UI:** `title.png`, `win.png`, `key.png`, `chest_locked.png`, `chest_open.png`

**Pirate sprites:** `p_dn_0.png` – `p_dn_4.png`, `p_up_0` – `p_up_4`, `p_lt_0` – `p_lt_4`, `p_rt_0` – `p_rt_4`, `p_jump_0.png`

**Skeleton sprites:** `sk_lt_0.png` – `sk_lt_4.png`, `sk_rt_0.png` – `sk_rt_4.png`

---

## 🚀 Running the Game

Since the game uses Java Applet (`GameBase extends Applet`), it is intended to be run with `appletviewer` or an IDE that supports applet execution.

1. Compile all `.java` files:
   ```bash
   javac *.java
   ```
2. Run with appletviewer using an HTML wrapper, or launch directly from your IDE with the applet configured to a `1440 × 810` window size.

> **Note:** Java Applets are deprecated in modern JDKs. The game works best with **JDK 8**.

---

## 🙌 Credits

Built by **Martin**, **Hamza**, **Jonathan**, and **Yasir** as a collaborative Java game project.

## Associated with
CMP 428 - Videogame Development<br>
CUNY Lehman College - Spring 2026
