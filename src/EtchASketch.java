import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtchASketch extends JPanel {
    private List<Point> points = new ArrayList<>();
    private List<Color> colors = new ArrayList<>(); // Track colors for each point
    private Color currentColor = Color.BLACK; // Start with black color
    private Map<Integer, Boolean> keyMap = new HashMap<>(); // Track key states

    public EtchASketch() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
        setFocusable(true);
        points.add(new Point(250, 250)); // Starting point
        colors.add(currentColor); // Add the current color for the starting point

        // Initialize key states for movement and erase
        keyMap.put(KeyEvent.VK_UP, false);
        keyMap.put(KeyEvent.VK_DOWN, false);
        keyMap.put(KeyEvent.VK_LEFT, false);
        keyMap.put(KeyEvent.VK_RIGHT, false);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyMap.put(e.getKeyCode(), true);
                moveDot();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyMap.put(e.getKeyCode(), false);
            }
        });
    }

    private void moveDot() {
        if (keyMap.getOrDefault(KeyEvent.VK_E, false)) { // Erase functionality
            points.clear();
            colors.clear();
            points.add(new Point(250, 250)); // Reset to starting point
            colors.add(currentColor); // Add the current color for the new starting point
        } else {
            Point lastPoint = points.get(points.size() - 1);
            int x = lastPoint.x;
            int y = lastPoint.y;

            if (keyMap.getOrDefault(KeyEvent.VK_UP, false) && keyMap.getOrDefault(KeyEvent.VK_LEFT, false)) {
                x -= 5;
                y -= 5;
            } else if (keyMap.getOrDefault(KeyEvent.VK_UP, false) && keyMap.getOrDefault(KeyEvent.VK_RIGHT, false)) {
                x += 5;
                y -= 5;
            } else if (keyMap.getOrDefault(KeyEvent.VK_DOWN, false) && keyMap.getOrDefault(KeyEvent.VK_LEFT, false)) {
                x -= 5;
                y += 5;
            } else if (keyMap.getOrDefault(KeyEvent.VK_DOWN, false) && keyMap.getOrDefault(KeyEvent.VK_RIGHT, false)) {
                x += 5;
                y += 5;
            } else if (keyMap.getOrDefault(KeyEvent.VK_UP, false)) {
                y -= 5;
            } else if (keyMap.getOrDefault(KeyEvent.VK_DOWN, false)) {
                y += 5;
            } else if (keyMap.getOrDefault(KeyEvent.VK_LEFT, false)) {
                x -= 5;
            } else if (keyMap.getOrDefault(KeyEvent.VK_RIGHT, false)) {
                x += 5;
            } else {
                return; // No movement key pressed
            }

            points.add(new Point(x, y));
            colors.add(currentColor); // Add the current color for the new point
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 1; i < points.size(); i++) {
            g.setColor(colors.get(i)); // Set the color for each segment
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Draw cursor at the last point
        if (!points.isEmpty()) {
            Point lastPoint = points.get(points.size() - 1);
            int cursorDiameter = 10;
            g.setColor(currentColor); // Use the current color for the cursor
            g.fillOval(lastPoint.x - cursorDiameter / 2, lastPoint.y - cursorDiameter / 2, cursorDiameter, cursorDiameter);
        }
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Etch-A-Sketch");
            EtchASketch sketch = new EtchASketch();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(sketch, BorderLayout.CENTER);

            // Sidebar for color selection and erase functionality
            JPanel sidebar = new JPanel();
            sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

            // Color buttons
            JButton blackButton = new JButton("Black");
            blackButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.BLACK);
                sketch.requestFocusInWindow();
            });
            sidebar.add(blackButton);

            JButton redButton = new JButton("Red");
            redButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.RED);
                sketch.requestFocusInWindow();
            });
            sidebar.add(redButton);

            JButton greenButton = new JButton("Green");
            greenButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.GREEN);
                sketch.requestFocusInWindow();
            });
            sidebar.add(greenButton);

            JButton blueButton = new JButton("Blue");
            blueButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.BLUE);
                sketch.requestFocusInWindow();
            });
            sidebar.add(blueButton);

            JButton orangeButton = new JButton("Orange");
            orangeButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.ORANGE);
                sketch.requestFocusInWindow();
            });
            sidebar.add(orangeButton);

            JButton yellowButton = new JButton("Yellow");
            yellowButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.YELLOW);
                sketch.requestFocusInWindow();
            });
            sidebar.add(yellowButton);


            JButton purpleButton = new JButton("Magenta");
            purpleButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.magenta);
                sketch.requestFocusInWindow();
            });
            sidebar.add(purpleButton);

            JButton pinkButton = new JButton("Pink");
            pinkButton.addActionListener(e -> {
                sketch.setCurrentColor(Color.PINK);
                sketch.requestFocusInWindow();
            });
            sidebar.add(pinkButton);
            // Erase button
            JButton eraseButton = new JButton("Erase");
            eraseButton.addActionListener(e -> {
                sketch.keyMap.put(KeyEvent.VK_E, true);
                sketch.moveDot(); // Directly call moveDot() to handle erase
                sketch.keyMap.put(KeyEvent.VK_E, false); // Reset erase key state
                sketch.requestFocusInWindow();
            });
            sidebar.add(eraseButton);

            frame.getContentPane().add(sidebar, BorderLayout.EAST);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            sketch.requestFocusInWindow();
        });
    }
}
