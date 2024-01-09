package main;

import java.awt.*;

public class Fruit {
    public int size;
    public Point coordinates;
    public boolean isLocked;

    private boolean isDropped;

    double yAccel;
    double xAccel;

    private int containerLeftWall = 700;
    private int containerRightWall = 1220;
    private int containerFloor = 850;

    public Fruit(int size, Point mousePos) {
        this.size = 100 + size * 7;
        coordinates = new Point(mousePos.x, 100);

        isLocked = false;
    }

    public void lock() {
        isLocked = true;
    }

    public void drop(double angle, double launchVelocity) {
        isDropped = true;
        isLocked = false;

        yAccel = Math.abs((Math.toDegrees(Math.sin(angle)) * launchVelocity));
        xAccel = (Math.toDegrees(Math.cos(angle)) * launchVelocity);
        System.out.println(angle);
    }

    public void update(Point mousePos) {
        if (!isDropped && !isLocked) {
            coordinates = new Point(mousePos.x - size / 2, 100);
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
        else if (!isLocked) {
            if(coordinates.y > containerFloor) {
                yAccel *= -0.6;
                if(yAccel > -10) {
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
