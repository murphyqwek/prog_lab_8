package org.example.form.removelower;

import org.example.controller.AddController;
import org.example.controller.RemoveLowerController;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * AddForm - описание класса.
 *
 * @version 1.0
 */

public class RemoveLowerDialog extends JDialog {
    private JTextField nameField;
    private JFormattedTextField xField;
    private JFormattedTextField yField;
    private JFormattedTextField participantsField;
    private JFormattedTextField albumsField;
    private JComboBox<String> genreBox;
    private JFormattedTextField salesField;
    private final RemoveLowerController controller;


    public RemoveLowerDialog(JFrame parent, RemoveLowerController controller) {
        super(parent, "Lab8 Starikov Arseny", true);
        this.controller = controller;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.WHITE);

        setLayout(new BorderLayout());
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(parent);

        JLabel titleLabel = new JLabel("Remove lower");
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

        addField(formPanel, gbc, "Name:", nameField);
        addField(formPanel, gbc, "X:", xField);
        addField(formPanel, gbc, "Y:", yField);
        addField(formPanel, gbc, "Number of Participants:", participantsField);
        addField(formPanel, gbc, "Albums count:", albumsField);
        addField(formPanel, gbc, "Music genre:", genreBox);
        addField(formPanel, gbc, "Sales:", salesField);

        JPanel buttonPanel = new JPanel();

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED);
        deleteButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(221, 222, 219));
        cancelButton.setFocusPainted(false);

        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBackground(Color.WHITE);

        add(buttonPanel, BorderLayout.SOUTH);


        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                saveClickHandler(evt);
            }
        });

        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
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
        int option = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить элементы?", "Подтверждение", JOptionPane.YES_NO_CANCEL_OPTION);

        if(option != JOptionPane.YES_OPTION) {
            return;
        }

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

            var response = controller.delete(name, x, y, participants, albums, musicGenre, sales);
            JOptionPane.showMessageDialog(this, response,
                    "Успешно!", JOptionPane.INFORMATION_MESSAGE);
            controller.close(this, false);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Не все данные корректны, возможно, какие-то ячейки пустые, или выходят за свои границы.",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка:" + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelClickHandler(MouseEvent evt) {
        controller.close(this, true);
    }
}
