package com.example.poryadnyygordiichukproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Gun {
    private double angle, x, y;
    private static final double Speed= 25;//25 - Pistol, 50 - MG
    public static final double Width  = 6;
    public static boolean MachineGun = false;

    public Gun(double angle, double x, double y){
        this.x = x;
        this.y = y;
        this.angle = angle;
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