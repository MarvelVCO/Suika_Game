package main;

import java.awt.*;
import java.util.ArrayList;

public class Fruit {
    public int size;

    public boolean isLocked;

    private boolean isDropped;

    public double x;
    public double y;
    double yVel;
    double xVel;

    private int containerLeftWall;
    private int containerRightWall;
    private int containerFloor;

    public Fruit(int size, Point mousePos) {
        this.size = 10 + size * 25;
        x = mousePos.x;
        y = 100;

        containerLeftWall = 700 + this.size / 2;
        containerRightWall = 1220 - this.size / 2;
        containerFloor = 850 - this.size / 2;
        System.out.println(containerLeftWall);

        isLocked = false;
    }

    public void lock() {
        isLocked = true;
    }

    public void drop(double angle, double launchVelocity) {
        isDropped = true;
        isLocked = false;

        yVel = Math.abs((Math.sin(Math.toRadians(angle)) * launchVelocity));

        double deltaXAccel = Math.abs(Math.cos(Math.toRadians(angle)) * launchVelocity);
        xVel = angle > 0 ? -deltaXAccel : deltaXAccel;
    }

    public void update(Point mousePos, ArrayList<Fruit> fruits) {
        if (!isDropped && !isLocked) {
            x = mousePos.x;
            if (x < containerLeftWall) {
                x = containerLeftWall;
            }
            if (x > containerRightWall) {
                x = containerRightWall;
            }
        } else if (!isLocked) {
            // vertical physics
            if (y > containerFloor) {
                y = containerFloor;
                yVel *= -0.4;
                if (yVel > -5) {
                    yVel = 0;
                }
            } else {
                yVel += y == containerFloor ? 0 : 0.75;
            }
            y += yVel;

            // Horizontal physics
            if (x > containerRightWall || x < containerLeftWall) {
                x = x > containerRightWall ? containerRightWall : containerLeftWall;
                xVel *= -0.75;
            } else {
                xVel += x == containerRightWall || x == containerLeftWall ? 0 :
                        xVel > 0 ? -0.03 : 0.03;
            }
            if (Math.abs(xVel) < 0.1) {
                xVel = 0;
            }
            x += xVel;
        }
    }
    public void calculatePhysics (ArrayList<Fruit> fruits) {
        for (Fruit fruit : fruits) {
            if (fruit != this && !fruit.isLocked) {
                double xDiff = x - fruit.x;
                double yDiff = y - fruit.y;
                double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

                if (distance <= ((double) size / 2 + (double) fruit.size / 2)) {
                    double angle = Math.atan2(y - fruit.y, x - fruit.x);
                    double totalVel = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));

                    // Calculate new velocities after collision
                    double newVelX = totalVel * Math.cos(angle);
                    double newVelY = totalVel * Math.sin(angle);

                    // Update velocities
                    xVel = newVelX;
                    yVel = newVelY;
                }
            }
        }
    }
}

