import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LibrarySearch extends JFrame {
    private final JTextField query = new JTextField(20);
    private final JComboBox<String> category = new JComboBox<>(new String[]{"All","Java","Science","History"});
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> results = new JList<>(model);
    private final JLabel status = new JLabel("Ready");
    private final java.util.List<String> books = Arrays.asList(
        "Java Programming - Java","Effective Java - Java","Physics Fundamentals - Science",
        "World History - History","Chemistry Basics - Science","Data Structures - Java"
    );
    public LibrarySearch() {
        super("Campus Library Search");
        setLayout(new BorderLayout(8,8));
        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.add(new JLabel("Search:")); search.add(query); search.add(category);
        JButton searchButton = new JButton("Search"); search.add(searchButton);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prev = new JButton("Prev"), next = new JButton("Next"), reserve = new JButton("Reserve");
        actions.add(prev); actions.add(next); actions.add(reserve);
        add(search, BorderLayout.NORTH);
        add(new JScrollPane(results), BorderLayout.CENTER);
        JPanel south = new JPanel(new BorderLayout()); south.add(actions, BorderLayout.NORTH); south.add(status, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);
        searchButton.addActionListener(e -> searchBooks());
        query.addActionListener(e -> searchBooks());
        reserve.addActionListener(e -> status.setText(results.getSelectedValue()==null ? "Select a book first." : "Reserved: "+results.getSelectedValue()));
        prev.addActionListener(e -> status.setText("Previous page (demo)."));
        next.addActionListener(e -> status.setText("Next page (demo)."));
        searchBooks();
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(650,420); setLocationRelativeTo(null); setVisible(true);
    }
    private void searchBooks() {
        model.clear(); String q=query.getText().toLowerCase(); String c=(String)category.getSelectedItem();
        for(String b:books) if(b.toLowerCase().contains(q) && (c.equals("All") || b.endsWith("- "+c))) model.addElement(b);
        status.setText(model.size()+" result(s) found.");
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(LibrarySearch::new); }
}
