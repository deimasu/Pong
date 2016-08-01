package ru.xc0re.games.pong;

import org.jsfml.audio.Music;

import org.jsfml.graphics.*;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.IOException;

import static org.jsfml.window.Keyboard.Key.*;

public class Main {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private static boolean flowOut = false;
    private static boolean gameStarted = false;

    private static int leftScore = 0;
    private static int rightScore = 0;

    public static void main(String[] args) throws IOException {

        RenderWindow window = new RenderWindow(new VideoMode(WIDTH, HEIGHT), "Pong");
        window.setFramerateLimit(60);

        Image icon = new Image();
        icon.loadFromStream(Main.class.getClassLoader().getResourceAsStream("icon.png"));
        window.setIcon(icon);

        Music hitWallSound = new Music();
        hitWallSound.openFromStream(Main.class.getClassLoader().getResourceAsStream("plop.ogg"));
        Music hitPaddleSound = new Music();
        hitPaddleSound.openFromStream(Main.class.getClassLoader().getResourceAsStream("beep.ogg"));
        Music flowOutSound = new Music();
        flowOutSound.openFromStream(Main.class.getClassLoader().getResourceAsStream("flow_out.ogg"));

        Font font = new Font();
        font.loadFromStream(Main.class.getClassLoader().getResourceAsStream("standart.ttf"));

        Text left = new Text();
        Text right = new Text();

        left.setColor(Color.WHITE);
        right.setColor(Color.WHITE);
        left.setCharacterSize(HEIGHT / 10);
        right.setCharacterSize(HEIGHT / 10);
        left.setFont(font);
        right.setFont(font);

        left.setPosition(WIDTH / 2.9f, 15);
        right.setPosition(WIDTH / 3 + WIDTH / 5.05f, 15);

        CentralLine line = new CentralLine();

        Paddle paddle1 = new Paddle();
        Paddle paddle2 = new Paddle();

        paddle1.init(WIDTH / 64,
                HEIGHT / 2 - paddle1.getSize().y / 2);

        paddle2.init(WIDTH - WIDTH / 64 - paddle2.getSize().x,
                HEIGHT / 2 - paddle2.getSize().y / 2);

        Ball ball = new Ball();

        ball.init(WIDTH / 2 - ball.getSize().x / 2, HEIGHT / 2 - ball.getSize().y / 2);

        while (window.isOpen()) {

            if (leftScore >= 10) {
                left.setString(Integer.toString(leftScore));
            } else {
                left.setString("0" + Integer.toString(leftScore));
            }
            if (rightScore >= 10) {
                right.setString(Integer.toString(rightScore));
            } else {
                right.setString("0" + Integer.toString(rightScore));
            }

            for (Event event : window.pollEvents()) {

                if (event.type == Event.Type.CLOSED)
                    window.close();

                if (event.type == Event.Type.KEY_PRESSED) {
                    if (event.asKeyEvent().key == SPACE) {
                        gameStarted = true;
                    }
                    if (event.asKeyEvent().key == UP) {
                        paddle2.setSpeed(-7);
                    } else if (event.asKeyEvent().key == DOWN) {
                        paddle2.setSpeed(7);
                    }
                    if (event.asKeyEvent().key == W) {
                        paddle1.setSpeed(-7);
                    } else if (event.asKeyEvent().key == S) {
                        paddle1.setSpeed(7);
                    }
                }
                if (event.type == Event.Type.KEY_RELEASED) {
                    if (event.asKeyEvent().key == UP) {
                        paddle2.setSpeed(0);
                    } else if (event.asKeyEvent().key == DOWN) {
                        paddle2.setSpeed(0);
                    }
                    if (event.asKeyEvent().key == W) {
                        paddle1.setSpeed(0);
                    } else if (event.asKeyEvent().key == S) {
                        paddle1.setSpeed(0);
                    }
                }
            }

            if (gameStarted) {

                if (paddle1.getTopY() <= 6 && paddle1.getSpeed() < 0) {
                    paddle1.setSpeed(0);
                }
                if (paddle1.getBottomY() >= HEIGHT - 6 && paddle1.getSpeed() > 0) {
                    paddle1.setSpeed(0);
                }
                if (paddle2.getTopY() <= 6 && paddle2.getSpeed() < 0) {
                    paddle2.setSpeed(0);
                }
                if (paddle2.getBottomY() >= HEIGHT - 6 && paddle2.getSpeed() > 0) {
                    paddle2.setSpeed(0);
                }

                // checks that ball hit the wall and reverses its y speed if this happened

                if (ball.getTopY() <= 0 && ball.getSpeedY() < 0
                        || ball.getBottomY() >= HEIGHT && ball.getSpeedY() > 0) {
                    ball.setSpeedY(ball.getSpeedY() * -1);
                    hitWallSound.play();
                }


                // checks that left player missed with paddle

                if (ball.getLeftX() <= paddle1.getRightX()
                        && (ball.getBottomY() < paddle1.getTopY()
                        || ball.getTopY() > paddle1.getBottomY())
                        && !flowOut) {
                    flowOut = true;
                    rightScore++;
                    ball.setSpeedX(-Ball.DEFAULT_X_SPEED);
                }


                // checks that right player missed with paddle

                if (ball.getRightX() >= paddle2.getLeftX()
                        && (ball.getBottomY() < paddle2.getTopY()
                        || ball.getTopY() > paddle2.getBottomY())
                        && !flowOut){
                    flowOut = true;
                    leftScore++;
                    ball.setSpeedX(Ball.DEFAULT_X_SPEED);
                    flowOutSound.play();
                }

                // if ball hits left paddle reverse x speed and increase it

                if (ball.getLeftX() <= paddle1.getRightX()
                        && ball.getBottomY() > paddle1.getTopY()
                        && ball.getTopY() < paddle1.getBottomY()
                        && !flowOut) {
                    ball.setSpeedX(ball.getSpeedX() * -1 * 1.1f);
                    float y1 = ball.getBottomY() - ball.getSize().y * 0.5f;
                    float y2 = paddle1.getBottomY() - paddle1.getSize().y * 0.5f;
                    ball.setSpeedY(0.2f * (y1 - y2));
                    if (ball.getSpeedY() > 0 && y1 < y2 || ball.getSpeedY() < 0 && y1 > y2)
                        ball.setSpeedY(ball.getSpeedY() * -1);
                    hitPaddleSound.play();
                }

                // if ball hits right paddle reverse x speed and increase it

                if (ball.getRightX() >= paddle2.getLeftX()
                        && ball.getBottomY() > paddle2.getTopY()
                        && ball.getTopY() < paddle2.getBottomY()
                        && !flowOut) {
                    ball.setSpeedX(ball.getSpeedX() * -1 * 1.1f);
                    float y1 = ball.getBottomY() - ball.getSize().y * 0.5f;
                    float y2 = paddle2.getBottomY() - paddle2.getSize().y * 0.5f;
                    ball.setSpeedY(0.2f * (y1 - y2));
                    if (ball.getSpeedY() > 0 && y1 < y2 || ball.getSpeedY() < 0 && y1 > y2)
                        ball.setSpeedY(ball.getSpeedY() * -1);
                    hitPaddleSound.play();
                }

                if (ball.getRightX() < 0 || ball.getLeftX() > WIDTH) {
                    gameStarted = false;
                    flowOut = false;

                    paddle1.init(WIDTH / 64,
                            HEIGHT / 2 - paddle1.getSize().y / 2);

                    paddle2.init(WIDTH - WIDTH / 64 - paddle2.getSize().x,
                            HEIGHT / 2 - paddle2.getSize().y / 2);

                    ball.init(WIDTH / 2 - ball.getSize().x / 2, HEIGHT / 2 - ball.getSize().y / 2);

                    flowOutSound.play();

                }


                ball.move();
                paddle1.move();
                paddle2.move();
            }
            window.clear(Color.BLACK);
            window.draw(paddle1.getShape());
            window.draw(paddle2.getShape());
            window.draw(ball.getShape());
            for (RectangleShape rect : line.getLine())
                window.draw(rect);
            window.draw(left);
            window.draw(right);
            window.display();

        }
    }
}
