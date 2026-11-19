import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EventRegistrationApp extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/csa09_events";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";
    private final JTextField studentId = new JTextField(10);
    private final JTextField studentName = new JTextField(12);
    private final JComboBox<String> event = new JComboBox<>(new String[]{"Cultural Fest","Tech Symposium","Sports Meet"});
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Student ID","Name","Event"},0);
    private final JTable table = new JTable(model);
    private Connection connection;

    public EventRegistrationApp() {
        super("Campus Event Registration");
        try { connection = DriverManager.getConnection(URL, USER, PASSWORD); }
        catch (SQLException ex) { JOptionPane.showMessageDialog(this,
            "MySQL connection failed. Check README setup.\\n"+ex.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE); }
        setJMenuBar(createMenu());
        JPanel form = new JPanel(new FlowLayout());
        form.add(new JLabel("Student ID:")); form.add(studentId);
        form.add(new JLabel("Name:")); form.add(studentName);
        form.add(event);
        JButton register = new JButton("Register");
        JButton refresh = new JButton("Refresh");
        form.add(register); form.add(refresh);
        add(form, BorderLayout.NORTH); add(new JScrollPane(table), BorderLayout.CENTER);
        register.addActionListener(e -> registerStudent());
        refresh.addActionListener(e -> loadRows());
        loadRows();
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(850,450); setLocationRelativeTo(null); setVisible(true);
    }
    private JMenuBar createMenu() {
        JMenuBar bar = new JMenuBar(); JMenu file = new JMenu("File");
        JMenuItem refresh = new JMenuItem("Refresh"), exit = new JMenuItem("Exit");
        refresh.addActionListener(e -> loadRows()); exit.addActionListener(e -> dispose());
        file.add(refresh); file.add(exit); bar.add(file); return bar;
    }
    private void registerStudent() {
        String id=studentId.getText().trim(), name=studentName.getText().trim(), ev=(String)event.getSelectedItem();
        if(id.isEmpty() || name.isEmpty()) { JOptionPane.showMessageDialog(this,"Enter student ID and name."); return; }
        if(connection==null) { JOptionPane.showMessageDialog(this,"Database is not connected. Follow README setup."); return; }
        String sql="INSERT INTO registrations(student_id, student_name, event_name) VALUES(?,?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setString(1,id); ps.setString(2,name); ps.setString(3,ev); ps.executeUpdate();
            JOptionPane.showMessageDialog(this,"Registration successful."); loadRows();
        } catch(SQLException ex) {
            if(ex.getErrorCode()==1062) JOptionPane.showMessageDialog(this,"Duplicate registration: this student is already registered for this event.");
            else JOptionPane.showMessageDialog(this,"Registration failed: "+ex.getMessage());
        }
    }
    private void loadRows() {
        model.setRowCount(0);
        if(connection==null) return;
        try(Statement st=connection.createStatement(); ResultSet rs=st.executeQuery(
            "SELECT id, student_id, student_name, event_name FROM registrations ORDER BY id DESC LIMIT 100")) {
            while(rs.next()) model.addRow(new Object[]{rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)});
        } catch(SQLException ex) { JOptionPane.showMessageDialog(this,"Could not load registrations: "+ex.getMessage()); }
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(EventRegistrationApp::new); }
}
