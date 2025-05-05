package org.example.form.add;

import org.example.controller.AddController;
import org.example.localization.Localization;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.example.base.model.MusicGenre.BRIT_POP;

/**
 * AddForm - описание класса.
 *
 * @version 1.0
 */

public class AddDialog extends JDialog {
    private JTextField nameField;
    private JFormattedTextField xField;
    private JFormattedTextField yField;
    private JFormattedTextField participantsField;
    private JFormattedTextField albumsField;
    private JComboBox<String> genreBox;
    private JFormattedTextField salesField;
    private JCheckBox addIfMaxCheck;
    private final AddController controller;


    public AddDialog(JFrame parent, AddController controller) {
        super(parent, "Lab8 Starikov Arseny", true);
        this.controller = controller;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.WHITE);

        setLayout(new BorderLayout());
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(parent);

        JLabel titleLabel = new JLabel(Localization.get("adding_title"));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 0)));
        add(titlePanel, BorderLayout.NORTH);

        // Основная форма
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        NumberFormatter intFormatter = new NumberFormatter(intFormat) {
            @Override
            public Object stringToValue(String text) throws ParseException {
                Number number = (Number) super.stringToValue(text);
                if (number == null) return null;

                long value = number.longValue();
                if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE)
                    throw new ParseException("Число вне диапазона Integer", 0);

                return (int) value;
            }
        };
        intFormatter.setValueClass(Integer.class);
        intFormatter.setAllowsInvalid(false);
        intFormatter.setMinimum(Integer.MIN_VALUE);
        intFormatter.setMaximum(Integer.MAX_VALUE);

        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter longFormatter = new NumberFormatter(longFormat);
        longFormatter.setValueClass(Long.class);
        longFormatter.setAllowsInvalid(false);
        longFormatter.setMinimum(Long.MIN_VALUE);
        longFormatter.setMaximum(Long.MAX_VALUE);

        NumberFormat doubleFormat = NumberFormat.getNumberInstance(Locale.US);
        NumberFormatter doubleFormatter = new NumberFormatter(doubleFormat) {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text != null && text.endsWith(".")) {
                    return text;
                }

                Number number = (Number) super.stringToValue(text);
                if (number == null) return null;

                double value = number.doubleValue();
                if (value < Double.MIN_VALUE || value > Double.MAX_VALUE)
                    throw new ParseException("Число вне допустимого диапазона Double", 0);

                return value; // ← Возвращаем именно double, не BigDecimal
            }
        };
        doubleFormatter.setValueClass(Double.class);
        doubleFormatter.setAllowsInvalid(true);
        doubleFormatter.setMinimum(Double.MIN_VALUE);
        doubleFormatter.setMaximum(Double.MAX_VALUE);


        nameField = new JTextField();
        xField = new JFormattedTextField(intFormatter);
        yField = new JFormattedTextField(longFormat);
        participantsField = new JFormattedTextField(longFormat);
        albumsField = new JFormattedTextField(longFormat);
        genreBox = new JComboBox<>(new String[]{"PSYCHEDELIC_ROCK", "JAZZ", "BLUES", "POST_PUNK", "BRIT_POP"});
        salesField = new JFormattedTextField(doubleFormatter);
        addIfMaxCheck = new JCheckBox();
        addIfMaxCheck.setBackground(Color.WHITE);

        addField(formPanel, gbc, "Name:", nameField);
        addField(formPanel, gbc, "X:", xField);
        addField(formPanel, gbc, "Y:", yField);
        addField(formPanel, gbc, "Number of Participants:", participantsField);
        addField(formPanel, gbc, "Albums count:", albumsField);
        addField(formPanel, gbc, "Music genre:", genreBox);
        addField(formPanel, gbc, "Sales:", salesField);
        addField(formPanel, gbc, Localization.get("add_if_max_title"), addIfMaxCheck);

        JPanel buttonPanel = new JPanel();

        JButton saveButton = new JButton(Localization.get("save_button_text"));
        saveButton.setBackground(Color.GREEN);
        saveButton.setFocusPainted(false);

        JButton cancelButton = new JButton(Localization.get("cancel_button_text"));
        cancelButton.setBackground(new Color(221, 222, 219));
        cancelButton.setFocusPainted(false);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBackground(Color.WHITE);

        add(buttonPanel, BorderLayout.SOUTH);


        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveClickHandler(evt);
            }
        });

        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelClickHandler(evt);
            }
        });

    }

    public void gui() {
        setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent input) {
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        JLabel label = new JLabel(labelText);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        input.setPreferredSize(new Dimension(100, 25));
        input.setMaximumSize(new Dimension(100, 25));
        input.setMinimumSize(new Dimension(100, 25));
        panel.add(input, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
    }

    private void saveClickHandler(MouseEvent evt) {
        try {
            String name = nameField.getText();

            xField.commitEdit();
            Integer x = (Integer) xField.getValue();

            yField.commitEdit();
            Long y = (Long) yField.getValue();

            participantsField.commitEdit();
            Long participants = (Long) participantsField.getValue();

            albumsField.commitEdit();
            long albums = (Long) albumsField.getValue();

            String musicGenre = (String) genreBox.getSelectedItem();

            salesField.commitEdit();
            double sales = (Double) salesField.getValue();
            boolean addIfMax = addIfMaxCheck.isSelected();

            var response = controller.save(name, x, y, participants, albums, musicGenre, sales, addIfMax);
            JOptionPane.showMessageDialog(this, response,
                    Localization.get("success_title"), JOptionPane.INFORMATION_MESSAGE);
            controller.close(this, false);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, Localization.get("invalid_fields"),
                    Localization.get("error_title"), JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, Localization.get("error_message_box") + " "+ e.getMessage(),
                    Localization.get("error_title"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelClickHandler(MouseEvent evt) {
        int option = JOptionPane.showConfirmDialog(this, Localization.get("confirmation_request_for_exit"), Localization.get("confirmation_title"), JOptionPane.YES_NO_CANCEL_OPTION);

        if(option != JOptionPane.YES_OPTION) {
            return;
        }

        controller.close(this, true);
    }
}
