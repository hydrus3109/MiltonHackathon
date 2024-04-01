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
    private Color currentcolor = Color.BLACK;
    private Map<Integer, Boolean> keymap = new HashMap<>(); // Track key states
    public boolean gamestart = false;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    public int Background = 0;
    Image blank = toolkit.getImage("");
    Image image = toolkit.getImage("src/EtchHomescreen.PNG" );
    Image mountain = toolkit.getImage("src/IMG_0311.png" );
    Image beach = toolkit.getImage("src/beach.jpg" );
    Image space = toolkit.getImage("src/space.png" );
    Image kitchen = toolkit.getImage("src/kitchen.png" );
    Image aquarium = toolkit.getImage("src/aquarium.jpg" );

    public EtchASketch() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
        setFocusable(true);
        points.add(new Point(250, 250)); // Starting point
        colors.add(currentcolor); // Add the current color for the starting point
        keymap.put(KeyEvent.VK_UP, false);
        keymap.put(KeyEvent.VK_DOWN, false);
        keymap.put(KeyEvent.VK_LEFT, false);
        keymap.put(KeyEvent.VK_RIGHT, false);
        keymap.put(KeyEvent.VK_ENTER, false);
        keymap.put(KeyEvent.VK_E, false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keymap.put(e.getKeyCode(), true);
                moveDot();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                keymap.put(e.getKeyCode(), false);
            }
        });
    }

    private void moveDot() {
        Point lastPoint = points.get(points.size() - 1);
        int x = lastPoint.x;
        int y = lastPoint.y;
        if(keymap.getOrDefault(KeyEvent.VK_ENTER, false)){
            gamestart = true;
            repaint();
        } else if (keymap.getOrDefault(KeyEvent.VK_E, false)) {
            points.clear();
            colors.clear();
            points.add(new Point(250, 250)); // Reset to starting point
            colors.add(currentcolor);
            Background = 0;
            repaint();
        } else {
            if (keymap.getOrDefault(KeyEvent.VK_UP, false) && keymap.getOrDefault(KeyEvent.VK_LEFT, false)) {
                x -= 5;
                y -= 5;
            } else if (keymap.getOrDefault(KeyEvent.VK_UP, false) && keymap.getOrDefault(KeyEvent.VK_RIGHT, false)) {
                x += 5;
                y -= 5;
            } else if (keymap.getOrDefault(KeyEvent.VK_DOWN, false) && keymap.getOrDefault(KeyEvent.VK_LEFT, false)) {
                x -= 5;
                y += 5;
            } else if (keymap.getOrDefault(KeyEvent.VK_DOWN, false) && keymap.getOrDefault(KeyEvent.VK_RIGHT, false)) {
                x += 5;
                y += 5;
            } else if (keymap.getOrDefault(KeyEvent.VK_UP, false)) {
                y -= 5;
            } else if (keymap.getOrDefault(KeyEvent.VK_DOWN, false)) {
                y += 5;
            } else if (keymap.getOrDefault(KeyEvent.VK_LEFT, false)) {
                x -= 5;
            } else if (keymap.getOrDefault(KeyEvent.VK_RIGHT, false)) {
                x += 5;
            } else {
                return;
            }
            //add point and corresponding color to tracking array
            points.add(new Point(x, y));
            colors.add(currentcolor);
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if(gamestart == false) {
            g.drawImage(image, 0, 0, 500, 500, this);
        }
        if(gamestart == true && Background == 0){
            g.drawImage(blank, 0, 0, 500, 500, this);
        }
        if(gamestart == true && Background == 1){
            g.drawImage(mountain, 0, 0, 500, 500, this);
        }
        if(gamestart == true && Background == 2){
            g.drawImage(beach, 0, 0, 500, 500, this);
        }
        if(gamestart == true && Background == 3){
            g.drawImage(space, 0, 0, 500, 500, this);
        }
        if(gamestart == true && Background == 4){
            g.drawImage(kitchen, 0, 0, 500, 500, this);
        }
        if(gamestart == true && Background == 5){
            g.drawImage(aquarium, 0, 0, 500, 500, this);
        }
        for (int i = 1; i < points.size(); i++) {
            g2.setColor(colors.get(i)); // Set the color for each segment
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Draw cursor at the last point
        if (!points.isEmpty()) {
            Point lastPoint = points.get(points.size() - 1);
            int cursorDiameter = 10;
            g2.setColor(currentcolor); // Use the current color for the cursor
            g2.fillOval(lastPoint.x - cursorDiameter / 2, lastPoint.y - cursorDiameter / 2, cursorDiameter, cursorDiameter);
        }
    }

    public void setCurrentColor(Color color) {
        this.currentcolor = color;
    }
    public void changebg(int bg) {
        this.Background = bg;

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //Define Mainframe
            JFrame frame = new JFrame("Etch-A-Sketch");
            EtchASketch sketch = new EtchASketch();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(sketch, BorderLayout.CENTER);
            // Define Sidebar
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
                sketch.keymap.put(KeyEvent.VK_E, true);
                sketch.moveDot(); // Directly call moveDot() to handle erase
                sketch.keymap.put(KeyEvent.VK_E, false);
            });
            sidebar.add(eraseButton);
            JButton bgButton = new JButton("Mountains");
            bgButton.addActionListener(e -> {
                sketch.changebg(1);
                sketch.requestFocusInWindow();
            });
            sidebar.add(bgButton);
            JButton bg2Button = new JButton("Beach");
            bg2Button.addActionListener(e -> {
                sketch.changebg(2);
                sketch.requestFocusInWindow();
            });
            sidebar.add(bg2Button);

            JButton bg3Button = new JButton("Kitchen");
            bg3Button.addActionListener(e -> {
                sketch.changebg(4);
                sketch.requestFocusInWindow();
            });
            sidebar.add(bg3Button);

            JButton bg4Button = new JButton("Space");
            bg4Button.addActionListener(e -> {
                sketch.changebg(3);
                sketch.requestFocusInWindow();
            });
            sidebar.add(bg4Button);

            JButton bg5Button = new JButton("Aquarium");
            bg5Button.addActionListener(e -> {
                sketch.changebg(5);
                sketch.requestFocusInWindow();
            });
            sidebar.add(bg5Button);

            frame.getContentPane().add(sidebar, BorderLayout.EAST);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            sketch.requestFocusInWindow();
        });
    }
}
