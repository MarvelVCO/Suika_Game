package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private final MouseHandler mH = new MouseHandler();
    private ArrayList<Fruit> fruits = new ArrayList<>();
    int FPS = 60;
    public GamePanel() {
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseListener(mH);
        this.setFocusable(true);
        this.fruits.add(new Fruit(1, mH.mousePos));
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                if(mH.mouseClicked) {
                    fruits.get(fruits.size() - 1).drop();
                    fruits.add(new Fruit(1, mH.mousePos));
                    mH.mouseClicked = false;
                }
                for(Fruit fruit : fruits) {
                    fruit.update(mH.mousePos);
                }

                repaint();
                delta--;
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D fruitGraphics = (Graphics2D) g;
        fruitGraphics.setColor(Color.white);
        for(Fruit fruit : fruits) {
            g.drawOval(fruit.coordinates.x, fruit.coordinates.y, fruit.size, fruit.size);
        }

    }
}