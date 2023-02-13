package com.example.poryadnyygordiichukproject;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.atan2;


public class Player extends Main {
    private static final double Width = 20;
    private double x, y;
    public static double Ammo = 5;
    public static List<Gun> bullets = new ArrayList<>();
    private boolean shooting = false, damage = false;
    public static double hp = 111;

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
        this.hp -= dmg;
        damage = true;
        Main.shedule(150, () -> damage = false);
    }

    public void render(GraphicsContext gc) {

        if(hp>1)
        {
            gc.drawImage(rotatedImage,this.x,this.y,70,40);
        }

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
            reloadAmmo();
        }
        else
        {
            if (!Main.ItReload)
            {
                Ammo--;
                double angle = atan2(y - this.y, x - this.x);
                Gun b = new Gun(angle, this.x, this.y);
                this.bullets.add(b);
            }
        }
    }
    public static void reloadAmmo()
    {
        Main.Cooldown(2000);
    }
}