package com.example.poryadnyygordiichukproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final double Width = 25;
    private double x,y;
    private List<Bullet> bullets = new ArrayList<>();
    public Player(double x, double y){
        this.x = x; this.y = y;
    }
    public void render(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillOval(this.x, this.y, Width, Width);
        for (int i = 0; i < this.bullets.size(); i++)
        {
            this.bullets.get(i).render(gc);
        }
    }
    public void move(double x, double y){
        this.x += x;
        this.y += y;
    }
    public void shoot(double x, double y)
    {
        double angle = Math.atan2(y-this.y, x-this.x);
        Bullet b = new Bullet(angle, this.x, this.y);
        this.bullets.add(b);
    }
}
