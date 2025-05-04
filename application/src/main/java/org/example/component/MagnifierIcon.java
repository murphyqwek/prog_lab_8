package org.example.component;

import javax.swing.*;
import java.awt.*;

/**
 * MagnifierIcon - описание класса.
 *
 * @version 1.0
 */

public class MagnifierIcon implements Icon {
    private int size;
    private Color color;

    public MagnifierIcon(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.fillOval(x + 2, y + 2, size - 4, size - 4);

        g2d.drawLine(x + size - 2, y + size - 2, x + size + 2, y + size + 2);

        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return size + 4;
    }

    @Override
    public int getIconHeight() {
        return size + 4;
    }
}
