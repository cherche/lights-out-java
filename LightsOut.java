import java.util.*;

public class LightsOut {
  public int width;
  public int height;
  public boolean[][] map;

  public String toString() {
    String string = "";

    for (int y = 0; y < width; y++) {
      for (int x = 0; x < height; x++) {
        string += (map[x][y] ? 1 : 0) + " ";
      }

      string += "\n";
    }

    return string;
  }

  public static void main(String[] args) {
    LightsOut game = new LightsOut(3, 3);
    game.randomizeMap();
    System.out.println(game.toString());

    while (!game.isWon()) {
      int x = IBIO.inputInt("x: ");
      int y = IBIO.inputInt("y: ");
      game.press(new int[] {x, y});
      System.out.println("");
      System.out.println(game.toString());
    }

    System.out.println("Congratulations! You win.");
  }

  public LightsOut(int width, int height) {
    this.width = width;
    this.height = height;
    this.map = new boolean[width][height];
  }

  public boolean isWon() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        // If any lights are on, it obviously isn't won.
        if (map[x][y]) {
          return false;
        }
      }
    }

    return true;
  }

  public void toggle(int[] coords) {
    int x = coords[0];
    int y = coords[1];
    map[x][y] = !map[x][y];
  }

  public ArrayList<int[]> press(int[] coords) {
    toggle(coords);
    ArrayList<int[]> queue = new ArrayList<int[]>(getDirectAdjacents(coords));

    for (int i = 0; i < queue.size(); i++) {
      int[] coords2 = queue.get(i);
      toggle(coords2);
    }

    return queue;
  }

  public void randomizeMap() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (Math.random() < 0.5) {
          press(new int[] {x, y});
        }
      }
    }

    if (isWon()) {
      randomizeMap();
    }
  }

  private ArrayList<int[]> getDirectAdjacents(int[] coords) {
    ArrayList<int[]> adjacents = new ArrayList<int[]>();
    int[][] diffs = {
      {-1, 0},
      {1, 0},
      {0, -1},
      {0, 1}
    };

    for (int i = 0; i < diffs.length; i++) {
      int[] diff = diffs[i];
      int[] temp = {coords[0] + diff[0], coords[1] + diff[1]};

      if (isInBoard(temp)) {
        adjacents.add(temp);
      }
    }

    return adjacents;
  }

  private boolean isInBoard(int[] coords) {
    int x = coords[0];
    int y = coords[1];

    return (0 <= x && x < width) && (0 <= y && y < height);
  }
}
