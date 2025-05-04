package org.example.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * PlaneWithRoundedBorder - описание класса.
 *
 * @version 1.0
 */

public class PlaneWithRoundedBorder extends JPanel {
    private Color borderColor;

    public PlaneWithRoundedBorder(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));

        g2d.setColor(borderColor);
        g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
        g2d.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}