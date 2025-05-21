import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThemeSwitchCalculator extends JFrame implements ActionListener {
    JTextField inputField;
    JPanel buttonPanel;
    JButton[] buttons = new JButton[16];
    JButton themeToggle;

    double num1, num2, result;
    char operator;

    boolean isDark = true;

    // Theme colors
    Color darkBg = new Color(30, 30, 30);
    Color darkBtn = new Color(50, 50, 50);
    Color darkTxt = new Color(255, 255, 255);

    Color lightBg = new Color(245, 245, 245);
    Color lightBtn = new Color(225, 225, 225);
    Color lightTxt = new Color(0, 0, 0);

    Font font = new Font("Consolas", Font.BOLD, 22);

    public ThemeSwitchCalculator() {
        setTitle("Switchable Theme Calculator");
        setSize(400, 530);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Field
        inputField = new JTextField();
        inputField.setFont(font);
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputField.setEditable(false);
        add(inputField, BorderLayout.NORTH);

        // Theme Toggle Button
        themeToggle = new JButton("Switch Theme");
        themeToggle.setFont(new Font("Arial", Font.PLAIN, 14));
        themeToggle.addActionListener(e -> toggleTheme());
        add(themeToggle, BorderLayout.SOUTH);

        // Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));

        String[] btnLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", "C", "=", "+"
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(btnLabels[i]);
            buttons[i].setFont(font);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        add(buttonPanel, BorderLayout.CENTER);
        applyTheme(); // Apply default theme
        setVisible(true);
    }

    public void applyTheme() {
        Color bg = isDark ? darkBg : lightBg;
        Color btn = isDark ? darkBtn : lightBtn;
        Color txt = isDark ? darkTxt : lightTxt;

        inputField.setBackground(bg);
        inputField.setForeground(txt);
        buttonPanel.setBackground(bg);
        getContentPane().setBackground(bg);
        themeToggle.setBackground(btn);
        themeToggle.setForeground(txt);

        for (JButton b : buttons) {
            b.setBackground(btn);
            b.setForeground(txt);
            b.setBorder(BorderFactory.createLineBorder(isDark ? Color.DARK_GRAY : Color.LIGHT_GRAY));
        }
    }

    public void toggleTheme() {
        isDark = !isDark;
        applyTheme();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (Character.isDigit(command.charAt(0))) {
            inputField.setText(inputField.getText() + command);
        } else if (command.equals("C")) {
            inputField.setText("");
        } else if (command.equals("=")) {
            try {
                num2 = Double.parseDouble(inputField.getText());
                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/': 
                        if (num2 == 0) throw new ArithmeticException("Divide by zero");
                        result = num1 / num2; 
                        break;
                }
                inputField.setText(String.valueOf(result));
            } catch (Exception ex) {
                inputField.setText("Error");
            }
        } else {
            try {
                num1 = Double.parseDouble(inputField.getText());
                operator = command.charAt(0);
                inputField.setText("");
            } catch (Exception ex) {
                inputField.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        new ThemeSwitchCalculator();
    }
}
