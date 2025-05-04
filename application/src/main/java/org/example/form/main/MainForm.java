package org.example.form.main;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * MainForm - описание класса.
 *
 * @version 1.0
 */

public class MainForm extends JFrame {
    public MainForm() {
        setTitle("Lab8 Starikov Arseny");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setBackground(Color.WHITE);

        //Левая панель
        JPanel panel = new JPanel(new GridBagLayout());
        //Настройки расположения на гриде
        GridBagConstraints gbc_l = new GridBagConstraints();
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        gbc_l.gridx = 0;
        gbc_l.gridy = 0;
        gbc_l.weightx = 0.05;
        gbc_l.weighty = 1.0;
        gbc_l.fill = GridBagConstraints.BOTH;

        //Настройки границ
        leftPanel.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY), new EmptyBorder(5, 5, 5, 5)));

        //Добавляем панель для заголовка
        JPanel leftTitlePanel = new JPanel();
        GridBagConstraints gbc_leftTitlePanel = new GridBagConstraints();
        leftTitlePanel.setBackground(Color.WHITE);
        gbc_leftTitlePanel.fill = GridBagConstraints.BOTH;
        gbc_leftTitlePanel.gridx = 0;
        gbc_leftTitlePanel.gridy = 0;
        gbc_leftTitlePanel.weightx = 1;
        gbc_leftTitlePanel.weighty = 0.05;
        gbc_leftTitlePanel.insets = new Insets(5, 0, 0, 0);

        JLabel leftTitle = new JLabel("Additional commands");
        leftTitle.setHorizontalAlignment(SwingConstants.CENTER);
        leftTitle.setVerticalAlignment(SwingConstants.CENTER);
        leftTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
        leftTitlePanel.add(leftTitle);


        leftPanel.add(leftTitlePanel, gbc_leftTitlePanel);

        //Добавляем панель для заголовка
        JPanel leftCommandsPanel = new JPanel();
        leftCommandsPanel.setLayout(new BoxLayout(leftCommandsPanel, BoxLayout.Y_AXIS));
        GridBagConstraints gbc_leftCommands = new GridBagConstraints();
        leftCommandsPanel.setBackground(Color.WHITE);
        gbc_leftCommands.fill = GridBagConstraints.BOTH;
        gbc_leftCommands.gridx = 0;
        gbc_leftCommands.gridy = 1;
        gbc_leftCommands.weightx = 1;
        gbc_leftCommands.weighty = 0.95;
        gbc_leftCommands.insets = new Insets(0, 5, 0, 5);


        //Clear Collection
        var clearCollectionButton = createSidebarButton("Clear Collection");
        leftCommandsPanel.add(clearCollectionButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Execute Script
        var executeScriptButton = createSidebarButton("Execute Script");
        leftCommandsPanel.add(executeScriptButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Sum of albums count
        var sumOfAlbumsCountButton = createSidebarButton("Sum of albums count");
        leftCommandsPanel.add(sumOfAlbumsCountButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Update table
        var updateTableButton = createSidebarButton("Update table");
        leftCommandsPanel.add(updateTableButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));


        //Info
        var infoButton = createSidebarButton("Info");
        leftCommandsPanel.add(infoButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Remove lower
        var removeLowerButton = createSidebarButton("Remove lower");
        leftCommandsPanel.add(removeLowerButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Visualise
        var visualiseButton = createSidebarButton("Visualise");
        leftCommandsPanel.add(visualiseButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));



        leftPanel.add(leftCommandsPanel, gbc_leftCommands);


        panel.add(leftPanel, gbc_l);

        GridBagConstraints gbc_r = new GridBagConstraints();
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        gbc_r.gridx = 1;
        gbc_r.gridy = 0;
        gbc_r.weightx = 0.95;
        gbc_r.weighty = 1.0;
        gbc_r.fill = GridBagConstraints.BOTH;
        gbc_r.insets = new Insets(15, 30, 20, 30);

        //Панель заголовка и панели таблицы
        GridBagConstraints headPanelConstraints = new GridBagConstraints();
        headPanelConstraints.gridx = 0;
        headPanelConstraints.gridy = 0;
        headPanelConstraints.weightx = 1;
        headPanelConstraints.weighty = 0.05;
        headPanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel headPanel = new JPanel(new GridBagLayout());
        headPanel.setBackground(Color.WHITE);

        //Заголовок
        GridBagConstraints rightTitlePanelConstraints = new GridBagConstraints();
        rightTitlePanelConstraints.gridx = 0;
        rightTitlePanelConstraints.gridy = 0;
        rightTitlePanelConstraints.weightx = 1;
        rightTitlePanelConstraints.weighty = 0.5;
        rightTitlePanelConstraints.fill = GridBagConstraints.BOTH;

        JPanel rightTitlePanel = new JPanel();
        rightTitlePanel.setBackground(Color.WHITE);
        rightTitlePanel.setLayout(new BoxLayout(rightTitlePanel, BoxLayout.Y_AXIS));
        JLabel rightTitle = new JLabel("Music band list");
        rightTitle.setHorizontalAlignment(SwingConstants.LEFT);

        rightTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        rightTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
        rightTitlePanel.add(rightTitle);

        headPanel.add(rightTitlePanel, rightTitlePanelConstraints);

        //Управление таблицей
        GridBagConstraints tableControlPanelConstraints = new GridBagConstraints();
        tableControlPanelConstraints.gridx = 0;
        tableControlPanelConstraints.gridy = 1;
        tableControlPanelConstraints.weightx = 1;
        tableControlPanelConstraints.weighty = 0.5;
        tableControlPanelConstraints.fill = GridBagConstraints.BOTH;



        JPanel tableControlPanel = new JPanel(new GridBagLayout());
        tableControlPanel.setBackground(Color.WHITE);

        //Элементы управления
        //Сортировка
        GridBagConstraints controlConstraints1 = new GridBagConstraints();
        controlConstraints1.gridx = 0;
        controlConstraints1.gridy = 0;
        controlConstraints1.weightx = 0.20;
        controlConstraints1.weighty = 1;
        controlConstraints1.insets = new Insets(2, 0 , 0, 0);
        controlConstraints1.fill = GridBagConstraints.NONE;
        controlConstraints1.anchor = GridBagConstraints.WEST;

        JPanel filterPanel = new PlaneWithRoundedBorder(new Color(200, 200, 200));;
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(2, 3, 2, 3));

        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterLabel.setForeground(Color.BLACK);
        filterPanel.setAlignmentX(LEFT_ALIGNMENT);

        // Выпадающий список
        String[] options = {"id", "name", "x", "y", "creation date", "albums count", "number of participants", "genre", "sales"}; // Пример вариантов
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboBox.setBackground(new Color(230, 230, 230));
        comboBox.setForeground(Color.BLACK);
        comboBox.setPreferredSize(new Dimension(70, 25));
        comboBox.setMaximumSize(new Dimension(70, 25));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        filterPanel.add(filterLabel);
        filterPanel.add(Box.createHorizontalStrut(5));
        filterPanel.add(comboBox);
        filterPanel.add(Box.createHorizontalStrut(5));

        filterPanel.setBackground(Color.WHITE);

        tableControlPanel.add(filterPanel, controlConstraints1);

        // Поиск
        GridBagConstraints controlConstraints2 = new GridBagConstraints();
        controlConstraints2.gridx = 1;
        controlConstraints2.gridy = 0;
        controlConstraints2.weightx = 0.60;
        controlConstraints2.weighty = 1;
        controlConstraints2.insets = new Insets(2, 2 , 0, 0);
        controlConstraints2.fill = GridBagConstraints.HORIZONTAL;
        controlConstraints2.anchor = GridBagConstraints.CENTER;

        JPanel searchPanel = new PlaneWithRoundedBorder(new Color(200, 200, 200));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 3));

        JButton searchButton = new JButton();
        searchButton.setIcon(new MagnifierIcon(16, new Color(150, 150, 150))); // Иконка лупы
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder());
        searchButton.setFocusPainted(false);
        searchButton.setMargin(new Insets(0, 0, 0, 0));
        searchButton.setAlignmentY(CENTER_ALIGNMENT);

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createEmptyBorder());
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setPreferredSize(new Dimension(0, 25));
        searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        searchField.setAlignmentY(CENTER_ALIGNMENT);

        searchPanel.add(searchButton);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalGlue());

        tableControlPanel.add(searchPanel, controlConstraints2);

        // Кнопки
        GridBagConstraints controlConstraints3= new GridBagConstraints();
        controlConstraints3.gridx = 2;
        controlConstraints3.gridy = 0;
        controlConstraints3.weightx = 0.20;
        controlConstraints3.weighty = 1;
        controlConstraints3.fill = GridBagConstraints.NONE;
        controlConstraints3.anchor = GridBagConstraints.EAST;

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(RIGHT_ALIGNMENT);
        buttonsPanel.setBackground(Color.WHITE);

        var editButton = new JButton("edit");
        editButton.setForeground(Color.BLACK);
        editButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        editButton.setBorder(BorderFactory.createEmptyBorder());
        editButton.setBackground(new Color(200, 200, 200));
        editButton.setFocusPainted(false);
        editButton.setMargin(new Insets(0, 0, 0, 0));
        editButton.setPreferredSize(new Dimension(80, 50));
        editButton.setMinimumSize(new Dimension(80, 30));
        editButton.setMaximumSize(new Dimension(80, 30));
        editButton.setAlignmentX(RIGHT_ALIGNMENT);

        var plusButton = new JButton("+");
        plusButton.setForeground(Color.BLACK);
        plusButton.setBackground(new Color(35, 237, 35));
        plusButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        plusButton.setBorder(BorderFactory.createEmptyBorder());
        plusButton.setFocusPainted(false);
        plusButton.setPreferredSize(new Dimension(30, 30));
        plusButton.setMinimumSize(new Dimension(30, 30));
        plusButton.setMaximumSize(new Dimension(30, 30));
        plusButton.setAlignmentX(RIGHT_ALIGNMENT);

        var minusButton = new JButton("-");
        minusButton.setBackground(new Color(255, 46, 46));
        minusButton.setForeground(Color.BLACK);
        minusButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        minusButton.setBorder(BorderFactory.createEmptyBorder());
        minusButton.setFocusPainted(false);
        minusButton.setPreferredSize(new Dimension(30, 30));
        minusButton.setMinimumSize(new Dimension(30, 30));
        minusButton.setMaximumSize(new Dimension(30, 30));
        minusButton.setAlignmentX(RIGHT_ALIGNMENT);

        buttonsPanel.add(editButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(plusButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(minusButton);
        buttonsPanel.add(Box.createHorizontalGlue());

        tableControlPanel.add(buttonsPanel, controlConstraints3);


        headPanel.add(tableControlPanel, tableControlPanelConstraints);

        //Таблица
        GridBagConstraints tablePanelConstraints = new GridBagConstraints();
        tablePanelConstraints.gridx = 0;
        tablePanelConstraints.gridy = 1;
        tablePanelConstraints.weightx = 1;
        tablePanelConstraints.weighty = 0.95;
        tablePanelConstraints.fill = GridBagConstraints.BOTH;

        // Создаем модель таблицы
        String[] columnNames = {"id", "name", "x", "y", "creation date", "albums count", "number of participants", "genre", "sales"};
        Object[][] data = {
                {1, "Alice", 25},
                {2, "Bob", 30},
                {3, "Charlie", 35}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Создаем таблицу
        JTable table = new JTable(model);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25); // Высота строк

        // Добавляем таблицу в JScrollPane для прокрутки
        JScrollPane tablePanel = new JScrollPane(table);
        tablePanel.setBorder(BorderFactory.createEmptyBorder());

        rightPanel.add(headPanel, headPanelConstraints);

        rightPanel.add(tablePanel, tablePanelConstraints);

        panel.setBackground(Color.WHITE);
        panel.add(rightPanel, gbc_r);

        add(panel);
        setVisible(true);
    }


    private JButton createSidebarButton(String title) {
        JButton sidebarButton = new JButton(title);
        sidebarButton.setBackground(new Color(247, 247, 247));
        sidebarButton.setForeground(Color.BLACK);
        sidebarButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sidebarButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(247, 247, 247), 1, true),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
        sidebarButton.setFocusPainted(false);
        sidebarButton.setPreferredSize(new Dimension(0, 70));
        sidebarButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        sidebarButton.setIcon(new CircleIcon(20, new Color(217, 217, 217)));

        sidebarButton.setHorizontalAlignment(SwingConstants.LEFT);
        sidebarButton.setIconTextGap(10);

        sidebarButton.setUI(new RoundButtonUI());

        return sidebarButton;
    }
}

class CircleIcon implements Icon {
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

class RoundButtonUI extends BasicButtonUI {

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b);
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, 0, size.width, size.height, 20, 20);
    }
}

class MagnifierIcon implements Icon {
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

class PlaneWithRoundedBorder extends JPanel {
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

class ButtonWithRoundedBorder extends JButton {
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
