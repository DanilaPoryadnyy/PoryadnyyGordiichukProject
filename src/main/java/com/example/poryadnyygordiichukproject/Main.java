package com.example.poryadnyygordiichukproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.EventHandler;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.*;

import java.io.IOException;
import java.util.List;

import static com.example.poryadnyygordiichukproject.Player.*;

public class Main extends Application {
    public static final int Height = 768;
    public static final int Width = 1440;
    public static final double Speed = 7;
    public static boolean ItReload = false;
    private static int Kills = 0;
    public static Player player;
    public static Map<KeyCode, Boolean> keys = new HashMap<>();
    public static List<Enemy> enemies = new ArrayList<>();
    StackPane pane = new StackPane();
    Canvas canvas = new Canvas(Width, Height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0 / 40), e -> update(gc)));

    Image dead = new Image("https://imageup.ru/img174/4211934/deadjckt.png");
    Image img = new Image("https://imageup.ru/img184/4212043/newjacket.png");
    Image enemyimg = new Image("https://imageup.ru/img99/4212179/enem.png");
    Image floor = new Image("https://imageup.ru/img204/4213013/floort.jpg");
    Image ammoImg = new Image("https://imageup.ru/img91/4213055/newammo.png");
    Image deadimg = new Image("https://imageup.ru/img249/4213318/deadenem.png");
    public static void main(String[] args) {

        launch();
    }

    public static void shedule(long time, Runnable r) {
        new Thread(() -> {
            try {
                Thread.sleep(time);
                r.run();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    public static void Cooldown(long time) {
        if(ItReload) {
            return;
        }
        ItReload = true;
        new Thread(() -> {
            try {
                Thread.sleep(time);
                if (Gun.MachineGun)
                {
                    player.Ammo = 30;
                }
                else
                {
                    Player.Ammo = 5;
                }
                ItReload = false;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException
    {
        stage.setTitle("First warrior shooter");

        canvas.setFocusTraversable(true);

        pane.getChildren().add(canvas);

        this.player = new Player(400, 300);

        loop.setCycleCount(Animation.INDEFINITE);

        loop.play();

        spawnEnemies();

        canvas.setOnKeyPressed(e -> this.keys.put(e.getCode(), true));
        canvas.setOnKeyReleased(e -> this.keys.put(e.getCode(), false));
        canvas.setOnMouseClicked(e -> this.player.shoot(e.getX(), e.getY()));


        Scene scene = new Scene(pane, Width, Height);
        stage.setScene(scene);
        stage.show();
    }
    private void spawnEnemies() {
        Thread spawner = new Thread(() -> {
            try {
                Random rand = new Random();
                while (true) {
                    double x = Player.player.GetX() + rand.nextDouble(-500,Width);
                    double y = Player.player.GetY() + rand.nextDouble(-500,Height);;
                    this.enemies.add(new Enemy(this.player ,x, y));
                    Thread.sleep(2000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        spawner.setDaemon(true);
        spawner.start();
    }

    private void update(GraphicsContext gc) {
        gc.clearRect(0, 0, Width, Height);
        gc.drawImage(floor,0,0, Width, Height);

        Point p = MouseInfo.getPointerInfo().getLocation();
        rotImg(p.x,p.y);

        rotImgEnem(Player.player.GetX(),Player.player.GetY());

        if(Ammo == 0)
        {
            reloadAmmo();
        }
        if(hp == 0)
        {
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER! \nYour score: " + Kills,  700, 400,1000);
            gc.drawImage(dead, Player.player.GetX(),Player.player.GetY(),80,80);
            loop.pause();
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.render(gc);
            for (int j = 0; j < Player.bullets.size(); j++) {
                if (e.collided(Player.bullets.get(j).GetX(), Player.bullets.get(j).GetY(), Enemy.Width, Gun.Width)) {
                    Player.bullets.remove(j);
                    enemies.remove(i);
                    gc.drawImage(deadimg,40,40,40,40);
                    i++;
                    Kills++;
                    break;
                }
            }
        }

        this.player.render(gc);

        if (this.keys.getOrDefault(KeyCode.W, false) && player.GetY() > 75) {
            this.player.move(0, -Speed);
        }
        if (this.keys.getOrDefault(KeyCode.S, false) && player.GetY() < Height - 100) {
            this.player.move(0, Speed);
        }
        if (this.keys.getOrDefault(KeyCode.D, false) && player.GetX() < Width - 20) {
            this.player.move(Speed, 0);
        }
        if (this.keys.getOrDefault(KeyCode.A, false) && player.GetX() > 1) {
            this.player.move(-Speed, 0);
        }
        if (this.keys.getOrDefault(KeyCode.R, false)) {
            reloadAmmo();
        }

        //Kills Count
        gc.setFill(Color.ORANGE);
        gc.fillText("KIlls: " + String.valueOf(Kills), Width - 1330,Height - 720, 40);
        //Ammo reloading message
        if(ItReload)
        {
            gc.setFill(Color.RED);
            gc.fillText("RELOADING!!!",  Width - 1240, Height - 185,80);
        }
        //Ammo GUI
        switch (Ammo)
        {
            case 1:
                gc.drawImage(ammoImg, 30, 620, 60,60);
                break;
            case 2:
                gc.drawImage(ammoImg, 30, 620, 60,60);
                gc.drawImage(ammoImg, 40, 620, 60,60);
                break;
            case 3:
                gc.drawImage(ammoImg, 30, 620, 60,60);
                gc.drawImage(ammoImg, 40, 620, 60,60);
                gc.drawImage(ammoImg, 50, 620, 60,60);
                break;
            case 4:
                gc.drawImage(ammoImg, 30, 620, 60,60);
                gc.drawImage(ammoImg, 40, 620, 60,60);
                gc.drawImage(ammoImg, 50, 620, 60,60);
                gc.drawImage(ammoImg, 60, 620, 60,60);
                break;
            case 5:
                gc.drawImage(ammoImg, 40, 620, 60,60);
                gc.drawImage(ammoImg, 30, 620, 60,60);
                gc.drawImage(ammoImg, 50, 620, 60,60);
                gc.drawImage(ammoImg, 60, 620, 60,60);
                gc.drawImage(ammoImg, 70, 620, 60,60);
                break;
        }
        //HP bar
        gc.setFill(Color.RED);
        gc.fillRect(50, Height-200, 100*(hp/100),30);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(50,Height-200,100,30);

    }
    ImageView iv = new ImageView(img);
    static Image rotatedImage;
    SnapshotParameters params;

    public void rotImg(double x, double y)
    {
        double xDistance = x - Player.player.GetX();
        double yDistance = y - Player.player.GetY();
        double angleToTurn = Math.toDegrees(Math.atan2(yDistance, xDistance));

        iv.setRotate((float)angleToTurn);
        params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        rotatedImage = iv.snapshot(params, null);

    }
    ImageView iv1 = new ImageView(enemyimg);
    static Image rotatedImage1;
    SnapshotParameters params1;
    public void rotImgEnem(double x, double y)
    {
        double xDistance = x - Enemy.player1.GetX();
        double yDistance = y - Enemy.player1.GetY();
        double angleToTurn = Math.toDegrees(Math.atan2(yDistance, xDistance));

        iv1.setRotate((float)angleToTurn);
        params1 = new SnapshotParameters();
        params1.setFill(Color.TRANSPARENT);
        rotatedImage1 = iv1.snapshot(params1, null);
    }
}