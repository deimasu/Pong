package ru.xc0re.games.pong;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;

public class CentralLine {

    private ArrayList<RectangleShape> line = new ArrayList<RectangleShape>();

    public CentralLine() {
        for (int i = 0; i < 15; i++) {
            line.add(new RectangleShape(new Vector2f(3, Main.HEIGHT / 18)));
            int y;
            if (i == 0)
                y = 6;
            else
                y = i * 6 + i * Main.HEIGHT / 18 + 6;
            line.get(i).setPosition(Main.WIDTH / 2 - line.get(0).getSize().x / 2, y);
        }
    }

    public ArrayList<RectangleShape> getLine() {
        return line;
    }
}
