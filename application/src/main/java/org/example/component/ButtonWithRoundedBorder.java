package org.example.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * ButtonWithRoundedBorder - описание класса.
 *
 * @version 1.0
 */

public class ButtonWithRoundedBorder extends JButton {
    private final Color borderColor;

    public ButtonWithRoundedBorder(String text, Color borderColor) {
        super(text);
        this.borderColor = borderColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(borderColor);
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
        g2d.setColor(getForeground());
        super.paintComponent(g2d);
        g2d.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}