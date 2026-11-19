import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Converter extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD","EUR"});
    private final JLabel result = new JLabel("Choose a style and convert.");
    private static double rate(String currency) { return currency.equals("USD") ? 0.012 : 0.011; }

    public Converter() {
        super("Currency Converter - Four Listener Styles");
        setLayout(new FlowLayout());
        add(new JLabel("Amount (INR):")); add(amount); add(target);
        JButton frameListener = new JButton("Frame listener");
        JButton inner = new JButton("Named inner");
        JButton anonymous = new JButton("Anonymous");
        JButton lambda = new JButton("Lambda");
        add(frameListener); add(inner); add(anonymous); add(lambda); add(result);

        // (a) Frame itself handles ActionListener
        frameListener.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { convert(); }
        });
        // (b) Named inner class
        inner.addActionListener(new NamedHandler());
        // (c) Anonymous inner class
        anonymous.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { convert(); }
        });
        // (d) Lambda listener; same lambda handles button and Enter key
        ActionListener shared = e -> convert();
        lambda.addActionListener(shared);
        amount.addActionListener(shared);

        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(620,180); setLocationRelativeTo(null); setVisible(true);
    }
    private class NamedHandler implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) { convert(); }
    }
    private void convert() {
        try {
            double inr = Double.parseDouble(amount.getText().trim());
            if (inr < 0) throw new NumberFormatException();
            String currency = (String)target.getSelectedItem();
            String message = String.format("%.2f INR = %.2f %s", inr, inr*rate(currency), currency);
            result.setText(message);
            JOptionPane.showMessageDialog(this, message, "Conversion", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(Converter::new); }
}
