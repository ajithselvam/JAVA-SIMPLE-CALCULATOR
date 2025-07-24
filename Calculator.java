import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    private JTextField displayField;
    private String currentInput = "";
    private double firstOperand = 0;
    private String operator = "";
    private boolean newCalculationStarted = false;

    public Calculator() {
        setTitle("Simple Java Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        setLayout(new BorderLayout());

        // Display Field
        displayField = new JTextField("0");
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        add(displayField, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // Rows, Cols, HGap, VGap

        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) { // Numbers
            if (newCalculationStarted) {
                currentInput = "";
                newCalculationStarted = false;
            }
            currentInput += command;
            displayField.setText(currentInput);
        } else if (command.equals(".")) { // Decimal point
            if (!currentInput.contains(".")) {
                currentInput += ".";
                displayField.setText(currentInput);
            }
        } else if (command.matches("[+\\-*/]")) { // Operators
            if (!currentInput.isEmpty()) {
                firstOperand = Double.parseDouble(currentInput);
                operator = command;
                currentInput = "";
                newCalculationStarted = true; // Clear display for next input
            }
        } else if (command.equals("=")) { // Equals
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondOperand = Double.parseDouble(currentInput);
                double result = 0;
                try {
                    switch (operator) {
                        case "+":
                            result = firstOperand + secondOperand;
                            break;
                        case "-":
                            result = firstOperand - secondOperand;
                            break;
                        case "*":
                            result = firstOperand * secondOperand;
                            break;
                        case "/":
                            if (secondOperand != 0) {
                                result = firstOperand / secondOperand;
                            } else {
                                displayField.setText("Error: Div by 0");
                                return;
                            }
                            break;
                    }
                    displayField.setText(String.valueOf(result));
                    currentInput = String.valueOf(result); // Allow further operations on result
                    operator = "";
                    newCalculationStarted = true;
                } catch (NumberFormatException ex) {
                    displayField.setText("Error");
                }
            }
        } else if (command.equals("C")) { // Clear
            currentInput = "";
            firstOperand = 0;
            operator = "";
            displayField.setText("0");
            newCalculationStarted = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }
}