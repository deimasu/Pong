package ru.xc0re.games.pong;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Paddle {

    private RectangleShape shape = new RectangleShape(new Vector2f(7, 60));

    private int speed;

    public void init(float x, float y) {
        shape.setPosition(x, y);
        shape.setFillColor(Color.WHITE);
    }

    public Vector2f getSize() {
        return shape.getSize();
    }

    public RectangleShape getShape() {
        return shape;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move() {
        this.shape.move(0, speed);
    }
}
