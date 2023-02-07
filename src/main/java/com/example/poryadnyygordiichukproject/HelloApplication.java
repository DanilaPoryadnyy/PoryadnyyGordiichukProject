package com.example.poryadnyygordiichukproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.*;

import java.io.IOException;

import static com.example.poryadnyygordiichukproject.Player.hp;

public class HelloApplication extends Application {
    public static final int Height = 720;
    public static final int Width = 1440;
    public static final double Speed = 7;
    private static int Kills = 0;
    public static Player player;
    public static Map<KeyCode, Boolean> keys = new HashMap<>();
    public static List<Enemy> enemies = new ArrayList<>();
    StackPane pane = new StackPane();
    Canvas canvas = new Canvas(Width, Height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0 / 40), e -> update(gc)));


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
                    double x = rand.nextDouble() * Width;
                    double y = rand.nextDouble() * Height;
                    this.enemies.add(new Enemy(this.player, x, y));
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
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, Width, Height);
        if(hp == 0)
        {
            loop.stop();
        }
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.render(gc);
            for (int j = 0; j < Player.bullets.size(); j++) {
                if (e.collided(Player.bullets.get(j).GetX(), Player.bullets.get(j).GetY(), Enemy.Width, Pistol.Width)) {
                    Player.bullets.remove(j);
                    enemies.remove(i);
                    i++;
                    Kills++;
                    break;
                }
            }
        }


        this.player.render(gc);

        this.player.render(gc);

        if (this.keys.getOrDefault(KeyCode.W, false) && player.GetY() > 1) {
            this.player.move(0, -Speed);
        }
        if (this.keys.getOrDefault(KeyCode.S, false) && player.GetY() < Height - 25) {
            this.player.move(0, Speed);
        }
        if (this.keys.getOrDefault(KeyCode.D, false) && player.GetX() < Width - 20) {
            this.player.move(Speed, 0);
        }
        if (this.keys.getOrDefault(KeyCode.A, false) && player.GetX() > 1) {
            this.player.move(-Speed, 0);
        }

        //Kills Count
        gc.setFill(Color.ORANGE);
        gc.fillText("KIlls: " + String.valueOf(Kills), Height - 680, Width - 1420, 30);
    }
}
