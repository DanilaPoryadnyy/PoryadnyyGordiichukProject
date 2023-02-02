package com.example.poryadnyygordiichukproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
    private double angle, x, y;
    private static final double Speed= 3;

    public Bullet(double angle, double x, double y){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    public void render(GraphicsContext gc){
        gc.setFill(Color.BLUE);
        gc.fillOval(this.x,this.y,3,3);

        this.x += Math.cos(this.angle)*Speed;
        this.y += Math.sin(this.angle)*Speed;

    }
}
