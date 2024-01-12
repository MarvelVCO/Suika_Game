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

        yVel = Math.abs((Math.sin(Math.toRadians(angle)) * launchVelocity));

        double deltaXAccel = Math.abs(Math.cos(Math.toRadians(angle)) * launchVelocity);
        xVel = angle > 0 ? -deltaXAccel : deltaXAccel;
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
                yVel *= -0.4;
                if(yVel > -5) {
                    yVel = 0;
                }
            }
            else {
                yVel += y == containerFloor ? 0 : 0.75;
            }
            y += yVel;

            // Horizontal physics
            if(x > containerRightWall || x < containerLeftWall) {
                x = x > containerRightWall ? containerRightWall : containerLeftWall;
                xVel *= -0.75;
            }
            else {
                xVel += x == containerRightWall || x == containerLeftWall ? 0 :
                        xVel > 0 ? -0.03 : 0.03;
            }
            if(Math.abs(xVel) < 0.1) {
                xVel = 0;
            }
            x += xVel;
        }

        for(Fruit fruit : fruits) {
            if(fruit != this && fruit != fruits.get(fruits.size() - 1) && fruit != fruits.get(fruits.size() - 2)) {
                double xDiff = x - fruit.x;
                double yDiff = y - fruit.y;

                double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

                boolean collision = distance <= ((double) size / 2 + (double) fruit.size / 2);

                if (collision) {
                    double deltaY = (this.y - fruit.y);
                    double deltaX = (fruit.x - this.x);
                    double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
                    double angle = (result < 0) ? (360d + result) : result;
                    if(angle > 90 && angle <= 180) {
                        angle = 90 - (angle - 90);
                        xVel *= -1;
                    }
                    else if(angle > 180 && angle <= 270) {
                        angle = -(angle - 180);
                        xVel *= -1;
                    }
                    else if(angle > 270 && angle <= 360) {
                        angle = -(90 - (angle - 270));
                    }

                    double totalVel = Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2));
                    double absXVel = Math.abs(Math.cos(Math.toRadians(angle)) * totalVel);
                    double absYVel = Math.abs(Math.sin(Math.toRadians(angle)) * totalVel);
                    System.out.println(angle);
                    System.out.println(totalVel);
                    System.out.println("Abs x: " + absXVel + " Abs y: " + absYVel);
                    System.out.println();
                    if(angle >= 0 && xVel <= 0) {
                        xVel = -absXVel;
                        yVel = absYVel;
                    }

                    if(angle <= 0 && xVel >= 0) {
                        xVel = absXVel;
                        yVel = -absYVel;
                    }

                    if(angle <= 0 && xVel <= 0) {
                        xVel = -absXVel;
                        yVel = -absYVel;
                    }
                }
            }
        }
    }
}
