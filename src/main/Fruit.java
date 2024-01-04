package main;

import java.awt.*;

public class Fruit {
    public int size;
    public Point coordinates;
    private boolean isDropped;

    public Fruit(int size, Point mousePos) {
        this.size = 100 + size * 10;
        coordinates = new Point(mousePos.x, 100);
    }

    public void drop() {
        isDropped = true;
    }

    public void update(Point mousePos) {
        if (!isDropped) {
            coordinates = new Point(mousePos.x, 100);
        }
        else {
            coordinates.y += 10;
        }
    }
}
