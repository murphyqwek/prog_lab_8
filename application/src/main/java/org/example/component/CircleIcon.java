package org.example.component;

import javax.swing.*;
import java.awt.*;

/**
 * CircleIcon - описание класса.
 *
 * @version 1.0
 */

public class CircleIcon implements Icon {
    private int diameter;
    private Color color;

    public CircleIcon(int diameter, Color color) {
        this.diameter = diameter;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(x, y, diameter, diameter);
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return diameter;
    }

    @Override
    public int getIconHeight() {
        return diameter;
    }
}
