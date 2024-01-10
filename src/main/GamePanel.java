package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private final MouseHandler mH = new MouseHandler();
    private int mouseX1;
    private int mouseY1;
    private int mouseX2;
    private int mouseY2;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    int FPS = 60;
    public GamePanel() {
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseListener(mH);
        this.addMouseMotionListener(mH);
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

            if(mH.mouseClicked) {
                if(!fruits.get(fruits.size() - 1).isLocked) {
                    mouseX1 = mH.mousePos.x;
                    mouseY1 = 100;

                    fruits.get(fruits.size() - 1).lock();
                }
                else if(mH.mousePos.y > 100){
                    mouseX2 = mH.mousePos.x;
                    mouseY2 = mH.mousePos.y;

                    double deltaY = (mouseY1 - mouseY2);
                    double deltaX = (mouseX2 - mouseX1);
                    double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
                    double angle = (result < 0) ? (360d + result) : result;

                    double launchVel = Math.abs(Math.sqrt(Math.pow(mouseX2 - mouseX1, 2) + Math.pow(mouseY2 - mouseY1, 2)) / 100);

                    fruits.get(fruits.size() - 1).drop((angle - 180) > 90 ? angle - 360 : angle - 180, launchVel);
                    fruits.add(new Fruit(1, mH.mousePos));
                }
                mH.mouseClicked = false;
            }

            if(delta >= 1) {
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
            g.drawOval((int) fruit.x, (int) fruit.y, fruit.size, fruit.size);
        }

        fruitGraphics.dispose();
    }
}