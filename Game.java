import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Game {
  public static int width = 3;
  public static int height = 3;
  public static JPanel[][] map = new JPanel[width][height];
  public static LightsOut game = new LightsOut(width, height);

  public static void main(String[] args) {
    game.randomizeMap();
    JPanel content = new JPanel();
    MouseListener listener = new MouseListener() {
      public void mousePressed(MouseEvent e) {
        Component pressed = e.getComponent();
        int[] indices = getIndices2(map, pressed);
        int x = indices[0];
        int y = indices[1];
        ArrayList<int[]> updates = game.press(new int[] {x, y});
        updateCells(updates);

        if (game.isWon()) {
          System.out.println("You win!");
        }
      }

      public void mouseReleased(MouseEvent e) {}

      public void mouseEntered(MouseEvent e) {}

      public void mouseExited(MouseEvent e) {}

      public void mouseClicked(MouseEvent e) {}
    };
    content.setLayout(new GridLayout(height, width));

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        JPanel button = new JPanel();
        button.addMouseListener(listener);
        map[x][y] = button;
        content.add(button);

        if (game.map[x][y]) {
          button.setBackground(Color.WHITE);
        } else {
          button.setBackground(Color.BLACK);
        }
      }
    }

    JFrame window = new JFrame();
    window.setContentPane(content);
    window.setSize(300, 300);
    window.setLocationRelativeTo(null);
    window.setVisible(true);
    window.setResizable(false);
  }

  public static void updateCells(ArrayList<int[]> cells) {
    for (int i = 0; i < cells.size(); i++) {
      int[] coords = cells.get(i);
      int x = coords[0];
      int y = coords[1];
      JPanel button = map[x][y];

      if (game.map[x][y]) {
        button.setBackground(Color.WHITE);
      } else {
        button.setBackground(Color.BLACK);
      }
    }
  }

  public static int[] getIndices2(Object[][] items, Object search) {
    for (int i = 0; i < items.length; i++) {
      for (int j = 0; j < items[i].length; j++) {
        Object item = items[i][j];
        if (item.equals(search)) {
          return new int[] {i, j};
        }
      }
    }

    return null;
  }
}
