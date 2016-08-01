package ru.xc0re.games.pong;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class Ball {

    public static final float DEFAULT_X_SPEED = 4f;

    private RectangleShape shape = new RectangleShape(new Vector2f(8, 8));
    private float speedX;
    private float speedY;

    public Ball() {
        Random random = new Random();

        if (random.nextInt(2) == 0) {
            speedX = -DEFAULT_X_SPEED;
        }
        else
            speedX = DEFAULT_X_SPEED;
    }

    public Vector2f getSize() {
        return shape.getSize();
    }

    public float getTopY() {
        return shape.getPosition().y;
    }

    public float getBottomY() {
        return shape.getPosition().y + shape.getSize().y;
    }

    public float getLeftX() {
        return shape.getPosition().x;
    }

    public float getRightX() {
        return shape.getPosition().x + shape.getSize().x;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void init(float x, float y) {
        shape.setPosition(x, y);
        shape.setFillColor(Color.WHITE);

        Random random = new Random();

        speedY = random.nextFloat() * 100 % 6 - 3;
    }

    public RectangleShape getShape() {
        return shape;
    }

    public void move() {
        shape.move(speedX, speedY);
    }
}
