package com.example.poryadnyygordiichukproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static com.example.poryadnyygordiichukproject.Main.rotatedImage;
import static com.example.poryadnyygordiichukproject.Main.rotatedImage1;


public class Enemy {
    private double x,y;
    public static Player player1;
    public static final double Width = 25;
    private static double Speed = 7;

    public Enemy(Player p, double x,double y)
    {
        this.player1 = p;
        this.x = x;
        this.y = y;
    }

    public double getX1() {
        return x;
    }

    public double getY1() {
        return y;
    }

    private boolean checkCollision()
    {
        for (int i = 0; i< Main.enemies.size(); i++)
        {
            Enemy e = Main.enemies.get(i);
            if (e != this)
            {
                if(e.collided(this.x, this.y, Width, Width))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collided(double x, double y, double EnemyWidth, double BulletOfPistolWidth)
    {
        return Math.sqrt(Math.pow(this.x+EnemyWidth/2-x-BulletOfPistolWidth/2,2)+Math.pow(this.y+EnemyWidth/2-y-BulletOfPistolWidth/2,2)) <= EnemyWidth+BulletOfPistolWidth;
    }

    public void render(GraphicsContext gc)
    {

        gc.drawImage(rotatedImage1,this.x,this.y,70,40);
        double distance = Math.sqrt(Math.pow(this.x - this.player1.GetX(),2)+Math.pow(this.y - this.player1.GetY(), 2));

        if(distance <= 30)
        {
            //Урон
            this.player1.takeDamage(1);

        }
        else
        {
            //Расстояние между врагом и персонажем
            double angle = Math.atan2(this.player1.GetY() -this.y, this.player1.GetX()-this.x);
            this.x += Math.cos(angle)*Speed;
            if (checkCollision())
            {
                this.x -= Math.cos(angle)*Speed;
            }
            this.y += Math.sin(angle)*Speed;
            if (checkCollision())
            {
                this.y -= Math.sin(angle)*Speed;
            }
        }
    }
}