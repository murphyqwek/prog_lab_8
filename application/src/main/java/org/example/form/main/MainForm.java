package org.example.form.main;

import org.example.base.model.MusicBand;
import org.example.component.CircleIcon;
import org.example.component.MagnifierIcon;
import org.example.component.PlaneWithRoundedBorder;
import org.example.component.RoundButtonUI;
import org.example.controller.MainController;
import org.example.localization.Localization;
import org.example.network.NetworkClient;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.Locale;

/**
 * MainForm - описание класса.
 *
 * @version 1.0
 */

public class MainForm extends JFrame {
    private final MainController controller;
    private DefaultTableModel model;
    private JTable table;
    private final String[] musicBandFields = {"id", "name", "x", "y", "creation date", "albums count", "number of participants", "genre", "sales"};
    private JTextField searchField;
    private JComboBox<String> comboBox;

    private JComboBox<String> languageBox;

    private JLabel leftTitle;
    private JButton clearCollectionButton;
    private JButton executeScriptButton;
    private JButton sumOfAlbumsCountButton;
    private JButton updateTableButton;
    private JButton infoButton;
    private JButton removeLowerButton;
    private JButton visualiseButton;
    private JLabel rightTitle;
    private JLabel filterLabel;
    private JButton editButton;


    Locale[] locales = {
            new Locale("en"),
            new Locale("ru"),
            new Locale("el"),
            new Locale("sr"),
            new Locale("es", "HN")
    };

    public MainForm(MainController controller) {
        this.controller = controller;
        controller.setFilteringFieldName("id");
    }

    public void gui() {
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

        leftTitle = new JLabel(Localization.get("additional_commands_title"));
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
        clearCollectionButton = createSidebarButton(Localization.get("clear_collection_button_text"));
        clearCollectionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearCollection();
            }
        });

        leftCommandsPanel.add(clearCollectionButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Execute Script
        executeScriptButton = createSidebarButton(Localization.get("execute_script_button_text"));
        leftCommandsPanel.add(executeScriptButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));
        executeScriptButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                executeScirptClickHandler(evt);
            }
        });

        //Sum of albums count
        sumOfAlbumsCountButton = createSidebarButton(Localization.get("sum_albums_button_text"));
        sumOfAlbumsCountButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sumOfAlbumsCount();
            }
        });

        leftCommandsPanel.add(sumOfAlbumsCountButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Update table
        updateTableButton = createSidebarButton(Localization.get("update_button_text"));
        updateTableButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateTable(false);
            }
        });

        leftCommandsPanel.add(updateTableButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));


        //Info
        infoButton = createSidebarButton(Localization.get("info_button_text"));
        infoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                info();
            }
        });
        leftCommandsPanel.add(infoButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Remove lower
        removeLowerButton = createSidebarButton(Localization.get("remove_lower_button_text"));
        removeLowerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeLowerClickHandler(evt);
            }
        });
        leftCommandsPanel.add(removeLowerButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));

        //Visualise
        visualiseButton = createSidebarButton(Localization.get("visualise_button_text"));
        leftCommandsPanel.add(visualiseButton);
        leftCommandsPanel.add(Box.createHorizontalGlue());
        leftCommandsPanel.add(Box.createVerticalStrut(5));


        leftPanel.add(leftCommandsPanel, gbc_leftCommands);

        String[] languageNames = {"English", "Русский", "Ελληνικά", "Српски", "Español (HN)"};

        languageBox = new JComboBox<>(languageNames);
        languageBox.setPreferredSize(new Dimension(150, 25));
        languageBox.setMinimumSize(new Dimension(150, 25));
        languageBox.setMaximumSize(new Dimension(150, 25));
        languageBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        languageBox.setSelectedItem(Localization.getLocaleString());
        languageBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        languageBox.addActionListener(e -> {
            selectLangChangedHandler();
        });

        leftCommandsPanel.add(Box.createVerticalGlue());

        leftCommandsPanel.add(languageBox);



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
        rightTitle = new JLabel(Localization.get("music_band_list_title")  + " " + controller.getUsername());
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

        filterLabel = new JLabel(Localization.get("filter_by_title"));
        filterLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        filterLabel.setForeground(Color.BLACK);
        filterPanel.setAlignmentX(LEFT_ALIGNMENT);

        // Выпадающий список
        comboBox = new JComboBox<>(musicBandFields);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeFilterFieldHandler(e);
            }
        });

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
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filterContainsName();
            }
        });

        searchField = new JTextField();
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

        editButton = new JButton(Localization.get("edit_button_text"));
        editButton.setForeground(Color.BLACK);
        editButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        editButton.setBorder(BorderFactory.createEmptyBorder());
        editButton.setBackground(new Color(224, 224, 224));
        editButton.setFocusPainted(false);
        editButton.setMargin(new Insets(0, 0, 0, 0));
        editButton.setPreferredSize(new Dimension(80, 50));
        editButton.setMinimumSize(new Dimension(80, 30));
        editButton.setMaximumSize(new Dimension(80, 30));
        editButton.setAlignmentX(RIGHT_ALIGNMENT);
        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateClickHandler(evt);
            }
        });


        var plusButton = new JButton("+");
        plusButton.setForeground(Color.BLACK);
        plusButton.setBackground(new Color(40, 250, 33));
        plusButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        plusButton.setBorder(BorderFactory.createEmptyBorder());
        plusButton.setFocusPainted(false);
        plusButton.setPreferredSize(new Dimension(30, 30));
        plusButton.setMinimumSize(new Dimension(30, 30));
        plusButton.setMaximumSize(new Dimension(30, 30));
        plusButton.setAlignmentX(RIGHT_ALIGNMENT);
        plusButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addClickHandler(evt);
            }
        });

        var minusButton = new JButton("-");
        minusButton.setBackground(new Color(255, 70, 45));
        minusButton.setForeground(Color.BLACK);
        minusButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        minusButton.setBorder(BorderFactory.createEmptyBorder());
        minusButton.setFocusPainted(false);
        minusButton.setPreferredSize(new Dimension(30, 30));
        minusButton.setMinimumSize(new Dimension(30, 30));
        minusButton.setMaximumSize(new Dimension(30, 30));
        minusButton.setAlignmentX(RIGHT_ALIGNMENT);
        minusButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteClickHandler(evt);
            }
        });

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

        model = new DefaultTableModel(musicBandFields, 5);

        table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);

        updateTable(true);


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

    private void updateTable(boolean withoutUserNotification) {
        try {
            controller.updateLocalStorage();
        } catch (Exception e) {
            if(!withoutUserNotification) {
                messageBoxWithError(e.getMessage());
            }
        }

        displayMusicBands(controller.getMusicBandsToDisplay());

        if(!withoutUserNotification) {
            JOptionPane.showMessageDialog(this, Localization.get("update_table_success"),
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayMusicBands(Object[][] musicBands) {
        DefaultTableModel newModel = new DefaultTableModel(musicBandFields, 0);

        for (Object[] row : musicBands) {
            newModel.addRow(row);
        }

        table.setModel(newModel);
    }

    private void info() {
        try {
            var info = controller.getInfo();
            JOptionPane.showMessageDialog(this, info,
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            messageBoxWithError(e.getMessage());
        }
    }

    private void sumOfAlbumsCount() {
        try {
            var sumOfAlbums = controller.getSumOfAlbumsCount();
            JOptionPane.showMessageDialog(this, Localization.get("sum_of_albums_text") + " " + sumOfAlbums,
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            messageBoxWithError(e.getMessage());
        }
    }

    private void clearCollection() {
        try {
            var response = controller.clearCollection();
            JOptionPane.showMessageDialog(this, response,
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
            updateTable(true);
        } catch (Exception e) {
            messageBoxWithError(e.getMessage());
        }
    }

    private void messageBoxWithError(String errorMessage) {
        JOptionPane.showMessageDialog(this, Localization.get("error_title") + ": " + errorMessage,
                Localization.get("error_title"), JOptionPane.ERROR_MESSAGE);
    }

    private void filterContainsName() {
        String filter = searchField.getText();

        try {
            controller.uploadMusicBandWithFilterName(filter);
            displayMusicBands(controller.getMusicBandsToDisplay());
        } catch (Exception ex) {
            messageBoxWithError(ex.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, Localization.get("elements_found_text"),
                Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void changeFilterFieldHandler(ActionEvent e) {
        String selected = (String) comboBox.getSelectedItem();
        controller.setFilteringFieldName(selected);

        var sortedMusicBands = controller.getMusicBands();

        displayMusicBands(controller.getMusicBandsToDisplay(sortedMusicBands));
    }

    private void addClickHandler(MouseEvent evt) {
        var isCanceled = controller.openAddingDiaglog(this);

        if(!isCanceled) {
            updateTable(true);
        }
    }

    private void deleteClickHandler(MouseEvent evt) {
        int option = JOptionPane.showConfirmDialog(this, Localization.get("confirmation_request_for_delete"), Localization.get("confirmation_title"), JOptionPane.YES_NO_CANCEL_OPTION);

        if(option != JOptionPane.YES_OPTION) {
            return;
        }

        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            messageBoxWithError(Localization.get("choose_row_to_delete"));
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        try {
            var response = controller.deleteMusicBand(id);
            JOptionPane.showMessageDialog(this, response,
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            messageBoxWithError(e.getMessage());
        }

        updateTable(true);
    }

    private void removeLowerClickHandler(MouseEvent evt) {
        var isCanceled = controller.openRemoveLowerDiaglog(this);

        if(!isCanceled) {
            updateTable(true);
        }
    }

    private void updateClickHandler(MouseEvent evt) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            messageBoxWithError(Localization.get("choose_row_to_update"));
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        var isCanceled = controller.openUpdateDialog(this, id);

        if(!isCanceled) {
            updateTable(true);
        }

        updateTable(true);
    }

    private void executeScirptClickHandler(MouseEvent evt) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle(Localization.get("choose_file"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();
        try {
            controller.executeScript(selectedFile.getAbsolutePath());
            JOptionPane.showMessageDialog(this, Localization.get("script_executed_success"),
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
            updateTable(true);
        } catch (Exception e) {
            messageBoxWithError(e.getMessage());
        }
    }

    private void updateText() {
        leftTitle.setText(Localization.get("additional_commands_title"));
        clearCollectionButton.setText(Localization.get("clear_collection_button"));
        executeScriptButton.setText(Localization.get("execute_script_button"));
        sumOfAlbumsCountButton.setText(Localization.get("sum_of_albums_count_button"));
        updateTableButton.setText(Localization.get("update_button"));
        infoButton.setText(Localization.get("info_button"));
        removeLowerButton.setText(Localization.get("remove_lower_button"));
        visualiseButton.setText(Localization.get("visualise_button"));
        rightTitle.setText(Localization.get("music_band_list_title")  + " " + controller.getUsername());
        filterLabel.setText(Localization.get("filter_by_title"));
        editButton.setText(Localization.get("edit_button_text"));
    }

    private void selectLangChangedHandler() {
        Locale selected = locales[languageBox.getSelectedIndex()];
        Localization.setLocale(selected);
        updateText();
    }
}