package com.example.poryadnyygordiichukproject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Enemy {
    private double x,y;
    private Player player;
    public static final double Width = 25;
    private static double Speed = 7;

    public Enemy(Player p, double x,double y)
    {
        this.player = p;
        this.x = x;
        this.y = y;
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

    Image enemyimg = new Image("https://imageup.ru/img99/4212179/enem.png");
    public void render(GraphicsContext gc)
    {

        gc.drawImage(enemyimg,this.x,this.y,70,43);
        double distance = Math.sqrt(Math.pow(this.x - this.player.GetX(),2)+Math.pow(this.y - this.player.GetY(), 2));

        if(distance <= 30)
        {
            //Урон
            this.player.takeDamage(1);

        }
        else
        {
            //Расстояние между врагом и персонажем
            double angle = Math.atan2(this.player.GetY() -this.y, this.player.GetX()-this.x);
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