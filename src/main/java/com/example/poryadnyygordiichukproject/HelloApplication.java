package com.example.poryadnyygordiichukproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.*;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final int Height = 1080;
    private static final int Width = 1920;
    public static final double Speed = 10;
    private Player player;
    private Map<KeyCode, Boolean> keys = new HashMap<>();

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        stage.setTitle("First warrior shooter");

        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(Width, Height);
        canvas.setFocusTraversable(true);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        pane.getChildren().add(canvas);

        this.player = new Player(400,300);

        Timeline loop = new Timeline(new KeyFrame(Duration.millis(1000.0/40), e -> update(gc)));
        loop.setCycleCount(Animation.INDEFINITE);

        loop.play();

        canvas.setOnKeyPressed(e -> this.keys.put(e.getCode(),true));
        canvas.setOnKeyReleased(e -> this.keys.put(e.getCode(),false));
        canvas.setOnMousePressed(e -> this.player.shoot(e.getX(),e.getY()));
        canvas.setOnMouseDragged(e -> this.player.shoot(e.getX(),e.getY()));


        Scene scene = new Scene(pane, Width,Height);
        stage.setScene(scene);
        stage.show();
    }
    private void update(GraphicsContext gc)
    {
        gc.clearRect(0,0,Width, Height);
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0, Width, Height);

        this.player.render(gc);

        if(this.keys.getOrDefault(KeyCode.W,false))
        {
            this.player.move(0,-Speed);
        }
        if(this.keys.getOrDefault(KeyCode.S,false))
        {
            this.player.move(0,Speed);
        }
        if(this.keys.getOrDefault(KeyCode.D,false))
        {
            this.player.move(Speed,0);
        }
        if(this.keys.getOrDefault(KeyCode.A,false))
        {
            this.player.move(-Speed,0);
        }
    }
}