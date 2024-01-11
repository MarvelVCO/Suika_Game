package main;

import java.awt.*;
import java.util.ArrayList;

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
        this.size = 10 + size * 25;
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

    public void update(Point mousePos, ArrayList<Fruit> fruits) {
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
        }

        for(Fruit fruit : fruits) {
            if(fruit != this && fruit != fruits.get(fruits.size() - 1) && fruit != fruits.get(fruits.size() - 2)) {
                double xDiff = x - fruit.x;
                double yDiff = y - fruit.y;

                double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

                boolean collision = distance < ((double) size / 2 + (double) fruit.size / 2);

                if (collision) {
                    double deltaY = (this.y - fruit.y);
                    double deltaX = (fruit.x - this.x);
                    double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
                    double angle = (result < 0) ? (360d + result) : result;
                    if(angle > 90 && angle <= 180) {
                        angle = -(angle - 90);
                    }
                    else if(angle > 180 && angle <= 270) {
                        // to be continued
                    }
                    else if(angle > 270 && angle <= 360) {
                        // to be continued
                    }

                    yAccel = Math.abs((Math.sin(Math.toRadians(angle)) * yAccel));

                    xAccel = Math.abs(Math.cos(Math.toRadians(angle)) * xAccel);
                }
            }
        }
    }
}
