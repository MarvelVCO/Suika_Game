package main;

import java.awt.*;

public class Fruit {
    public int size;

    public boolean isLocked;

    private boolean isDropped;

    public double x;
    public double y;
    double yAccel;
    double xAccel;

    private int containerLeftWall = 700;
    private int containerRightWall = 1220;
    private int containerFloor = 850;

    public Fruit(int size, Point mousePos) {
        this.size = 100 + size * 7;
        x = mousePos.x;
        y = 100;

        isLocked = false;
    }

    public void lock() {
        isLocked = true;
    }

    public void drop(double angle, double launchVelocity) {
        isDropped = true;
        isLocked = false;

        yAccel = Math.abs((Math.sin(Math.toRadians(angle)) * launchVelocity));

        double deltaXAccel = Math.abs(Math.cos(Math.toRadians(angle)) * launchVelocity);
        xAccel = angle > 0 ? -deltaXAccel : deltaXAccel;
    }

    public void update(Point mousePos) {
        if (!isDropped && !isLocked) {
            x = mousePos.x - (double) size / 2;
            if(x < containerLeftWall) {
                x = containerLeftWall;
            }
            if(x > containerRightWall) {
                x = containerRightWall;
            }
            if(y > containerFloor) {
                y = containerFloor;
            }
        }
        else if (!isLocked) {
            // vertical physics
            if(y > containerFloor) {
                y = containerFloor;
                yAccel *= -0.4;
                if(yAccel > -5) {
                    yAccel = 0;
                }
            }
            else {
                yAccel += y == containerFloor ? 0 : 0.75;
            }
            y += yAccel;

            // Horizontal physics
            if(x > containerRightWall || x < containerLeftWall) {
                x = x > containerRightWall ? containerRightWall : containerLeftWall;
                xAccel *= -0.75;
            }
            else {
                xAccel += x == containerRightWall || x == containerLeftWall ? 0 :
                        xAccel > 0 ? -0.03 : 0.03;
            }
            if(Math.abs(xAccel) < 0.1) {
                xAccel = 0;
            }
            x += xAccel;
            if(xAccel != 0) {
                System.out.println("xAccel: " + xAccel);
                System.out.println("Coordinates.x" + x);
            }
        }
    }
}
