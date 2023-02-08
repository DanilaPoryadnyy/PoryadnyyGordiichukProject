package com.example.poryadnyygordiichukproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Gun {
    private double angle, x, y,xg,yg;
    private static final double Speed= 25;
    public static final double Width  = 6;
    public static boolean MachineGun = false;

    public Gun(double angle, double x, double y){
        this.x = x+8;
        this.y = y+8;
        this.angle = angle;
    }
    public Gun (double xg, double yg)
    {
        this.xg = xg + 8;
        this.yg = yg + 8;

    }

    public double GetX()
    {
        return this.x;
    }
    public double GetY()
    {
        return this.y;
    }

    public void render(GraphicsContext gc){
        gc.setFill(Color.GOLD);
        gc.fillOval(this.x,this.y,Width,Width);

        this.x += Math.cos(this.angle)*Speed;
        this.y += Math.sin(this.angle)*Speed;
    }
}