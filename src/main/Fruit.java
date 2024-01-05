package main;

import java.awt.*;

public class Fruit {
    public int size;
    public Point coordinates;
    private boolean isDropped;

    private double yAccel;
    private double xAccel;

    private int containerLeftWall = 700;
    private int containerRightWall = 1220;
    private int containerFloor = 850;

    public Fruit(int size, Point mousePos) {
        this.size = 100 + size * 7;
        coordinates = new Point(mousePos.x, 100);

        yAccel = 1;
        xAccel = 0;
    }

    public void drop() {
        isDropped = true;
    }

    public void update(Point mousePos) {
        if (!isDropped) {
            coordinates = new Point(mousePos.x, 100);
            if(coordinates.x < containerLeftWall) {
                coordinates.x = containerLeftWall;
            }
            if(coordinates.x > containerRightWall) {
                coordinates.x = containerRightWall;
            }
            if(coordinates.y > containerFloor) {
                coordinates.y = containerFloor;
            }
        }
        else {
            if(coordinates.y > containerFloor) {
                yAccel *= -0.75;
                System.out.println(yAccel);
                if(yAccel < 10 && yAccel > 0) {
                    yAccel = 0;
                }
            }
            else {
                yAccel += 0.75;
            }
            coordinates.y += yAccel;
        }
    }
}
