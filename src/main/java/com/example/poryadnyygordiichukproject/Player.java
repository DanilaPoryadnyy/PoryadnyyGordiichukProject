package com.example.poryadnyygordiichukproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Player {
    private static final double Width = 20;
    private double x, y;
    public static double Ammo = 5;
    public static List<Pistol> bullets = new ArrayList<>();
    private boolean shooting = false, damage = false;
    public static int hp = 1;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double GetX() {
        return this.x;
    }

    public double GetY() {
        return this.y;
    }

    public void takeDamage(int dmg) {
        if (damage) return;
        //this.hp -= dmg;
        damage = true;
        HelloApplication.shedule(150, () -> damage = false);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(this.x, this.y, Width, Width);
        for (int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).render(gc);
        }
    }

    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void shoot(double x, double y) {
        if(Ammo == 0)
        {
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    System.out.println(Ammo + System.currentTimeMillis());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
        if (Ammo != 0)
        {
            Ammo--;
            double angle = Math.atan2(y - this.y, x - this.x);
            Pistol b = new Pistol(angle, this.x, this.y);
            this.bullets.add(b);
        }
    }
}