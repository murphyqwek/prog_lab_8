package org.example.form.visualise;

import org.example.base.model.MusicBand;
import org.example.base.model.MusicBandWithOwner;
import org.example.controller.VisualiseController;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * VisualiseForm - описание класса.
 *
 * @version 1.0
 */

public class VisualiseForm extends JFrame {
    private VisualiseController controller;
    private VisualizationPanel visualizationPanel;

    public VisualiseForm(VisualiseController controller) {
        this.controller = controller;
        setBackground(Color.WHITE);
    }


    public void gui() {
        JFrame frame = new JFrame("Visualization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        visualizationPanel = new VisualizationPanel(controller.getMusicBands());

        frame.add(visualizationPanel);
        frame.setLocationRelativeTo(null);

        Timer timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
                visualizationPanel.stopTimer();

                controller.unsubscribe();
                
                dispose();
            }
        });

        
        timer.start();
        
        

        frame.setVisible(true);
    }
    
    private void update() {
        try {
            controller.checkForUpdate();
        } catch (Exception ex) {
            //ex.printStackTrace();
            return;
        }

        visualizationPanel.update(controller.getMusicBands());
    }

}

class VisualizationPanel extends JPanel {
    private static final int ANIMATION_DURATION = 1000;
    private static final int TIMER_DELAY = 40;

    private java.util.List<MusicBandWithOwner> elements = new ArrayList<>();
    private Map<MusicBandWithOwner, Float> alphaMap = new HashMap<>();
    private Timer animationTimer;

    public VisualizationPanel(java.util.List<MusicBandWithOwner> initialElements) {
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        update(initialElements);
    }

    public void stopTimer() {
        animationTimer.stop();
    }

    public void update(java.util.List<MusicBandWithOwner> newElements) {
        elements = new ArrayList<>(newElements);
        alphaMap.clear();

        for (MusicBandWithOwner el : elements) {
            alphaMap.put(el, 0f);
        }

        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        long startTime = System.currentTimeMillis();

        animationTimer = new Timer(TIMER_DELAY, e -> {
            float progress = (System.currentTimeMillis() - startTime) / (float) ANIMATION_DURATION;
            progress = Math.min(progress, 1f);

            for (var el : elements) {
                alphaMap.put(el, progress);
            }

            repaint();

            if (progress >= 1f) {
                animationTimer.stop();
            }
        });

        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        drawElements((Graphics2D) g);
    }

    private void drawAxes(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        g2.setColor(Color.BLACK);
        g2.drawLine(w / 2, 0, w / 2, h); // Y-axis
        g2.drawLine(0, h / 2, w, h / 2); // X-axis
    }

    private void drawElements(Graphics2D g2) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (MusicBandWithOwner el : elements) {
            var mb = el.getMusicBand();
            var owner = el.getOwner();
            int x = centerX + mb.getX();
            long y = centerY - mb.getY();

            float alpha = alphaMap.getOrDefault(el, 1f);
            Composite old = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            g2.setColor(getColorByOwner(owner));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 24));
            g2.drawString("♫", x, y);

            g2.setComposite(old);
        }
    }

    private Color getColorByOwner(String owner) {
        int hash = Math.abs(owner.hashCode());
        return new Color(Color.HSBtoRGB((hash % 360) / 360f, 0.6f, 0.9f));
    }
}
