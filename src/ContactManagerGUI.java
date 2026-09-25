import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ContactManagerGUI extends JFrame {

    private JTextField nameField;
    private JTextField phoneField;

    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel countLabel;
    private JLabel statusLabel;

    private ContactManager manager;

    public ContactManagerGUI() {

        manager = new ContactManager();

        setTitle("Contact Manager Pro");
        setSize(1000, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        createTopPanel();
        createTable();
        createStatusBar();

        refreshTable();

        setVisible(true);
    }

    private void createTopPanel() {

        JPanel panel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        panel.add(new JLabel("Name:"));

        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Phone:"));

        phoneField = new JTextField();
        panel.add(phoneField);

        JButton addButton =
                new JButton("Add");

        JButton searchButton =
                new JButton("Search");

        JButton deleteButton =
                new JButton("Delete Selected");

        JButton sortButton =
                new JButton("Sort");

        addButton.setBackground(
                new Color(34, 197, 94)
        );

        searchButton.setBackground(
                new Color(59, 130, 246)
        );

        deleteButton.setBackground(
                new Color(239, 68, 68)
        );

        sortButton.setBackground(
                new Color(139, 92, 246)
        );

        addButton.setForeground(Color.WHITE);
        searchButton.setForeground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);
        sortButton.setForeground(Color.WHITE);

        panel.add(addButton);
        panel.add(searchButton);

        panel.add(deleteButton);
        panel.add(sortButton);

        add(panel, BorderLayout.NORTH);

        addButton.addActionListener(
                e -> addContact()
        );

        searchButton.addActionListener(
                e -> searchContact()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedContact()
        );

        sortButton.addActionListener(
                e -> sortContacts()
        );
    }

    private void createTable() {

        tableModel = new DefaultTableModel(
                new String[]{
                        "Name",
                        "Phone"
                },
                0
        );

        table = new JTable(tableModel);

        table.setRowHeight(30);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void createStatusBar() {

        countLabel =
                new JLabel("Total Contacts: 0");

        statusLabel =
                new JLabel("Ready");

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.add(
                countLabel,
                BorderLayout.WEST
        );

        panel.add(
                statusLabel,
                BorderLayout.EAST
        );

        add(panel, BorderLayout.SOUTH);
    }

    private void addContact() {

        String name =
                nameField.getText().trim();

        String phone =
                phoneField.getText().trim();

        if (name.isEmpty() ||
                phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        manager.addContact(
                new Contact(
                        name,
                        phone
                )
        );

        refreshTable();

        statusLabel.setText(
                "Added: " + name
        );

        nameField.setText("");
        phoneField.setText("");
    }

    private void searchContact() {

        String searchName =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Name"
                );

        if (searchName == null)
            return;

        Contact found =
                manager.searchContact(
                        searchName
                );

        if (found != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Found\n\n"
                            + found.getName()
                            + "\n"
                            + found.getPhone()
            );

            statusLabel.setText(
                    "Search successful"
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Contact not found"
            );

            statusLabel.setText(
                    "Search failed"
            );
        }
    }

    private void deleteSelectedContact() {

        int row =
                table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a contact first."
            );

            return;
        }

        manager.deleteAtIndex(row);

        refreshTable();

        statusLabel.setText(
                "Contact deleted"
        );
    }

    private void sortContacts() {

        manager.sortContacts();

        refreshTable();

        statusLabel.setText(
                "Contacts sorted"
        );
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Contact contact :
                manager.getContacts()) {

            tableModel.addRow(
                    new Object[]{
                            contact.getName(),
                            contact.getPhone()
                    }
            );
        }

        countLabel.setText(
                "Total Contacts: "
                        + manager.getContacts().size()
        );
    }
}