import java.awt.*;
import java.awt.event.*;

public class FeeWindow extends Frame {
    private final TextField regNo = new TextField(18);
    private final TextField amount = new TextField(18);
    private final Label status = new Label("Enter details and click Pay.");
    private final FeeService service = new FeeService();

    public FeeWindow() {
        super("Student Fee Payment");
        setLayout(new BorderLayout(8, 8));
        Panel form = new Panel(new GridLayout(3, 2, 8, 8));
        form.add(new Label("Registration No:")); form.add(regNo);
        form.add(new Label("Amount (INR):")); form.add(amount);
        Button pay = new Button("Pay");
        Button clear = new Button("Clear");
        form.add(pay); form.add(clear);
        add(form, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        pay.addActionListener(e -> status.setText(service.pay(regNo.getText(), amount.getText())));
        clear.addActionListener(e -> { regNo.setText(""); amount.setText(""); status.setText("Cleared."); });
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(420, 180);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) { EventQueue.invokeLater(FeeWindow::new); }
}
