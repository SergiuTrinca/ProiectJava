
package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;




class ListSelectionListenerImpl implements ListSelectionListener {

    private final JTable table;

    public ListSelectionListenerImpl(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int[] selectedRows = table.getSelectedRows();
        int[] selectedColumns = table.getSelectedColumns();
        StringBuilder value = new StringBuilder();
        for (int selectedRow : selectedRows) {
            for (int selectedColumn : selectedColumns) {
                value.append(table.getValueAt(selectedRow, selectedColumn));
            }
        }
        System.out.println("Table element selected is: " + value);
    }
}
class TestTableSortFilter extends JPanel {

    private String[] columnNames
            = {"Nume",
            "Prenume",
            "Specializare",
            "An de studii",
            "Buget"};;

    private Object[][] data = {
            {"Bura", "Casian", "Calculatoare", 1, false},
            {"Vasadi", "Caleb", "Inginerie Mecanica", 3, true},
            {"Eghedi", "Fabian", "Chimie", 2, false},
            {"Olariu", "Stefan", "Litere", 1, true},
            {"Buda", "Alex", "Medicina Generala", 5, false},
            {"Efrata", "Fabian", "Gastronomie", 1, false},
            {"Jan", "Paula", "Pedagogie", 3, false},
            {"Uri", "George", "Fizica", 2, true},
            {"Manci", "Andreea", "Kinetoterapie", 3, true},
            {"Burlan", "Adriana", "Medicina Dentara", 6, false},
            {"Costache", "Timotei", "TI", 1, false},
            {"Lazar", "Cristiana", "Robotica", 4, false},
            {"Razmus", "Elena", "Biologie", 2, true},
            {"Cioara", "Eustache", "Sport", 1, true},
            {"Pele", "Alin", "Inginerie Dentara", 5, true},
            {"Mohamed", "Salah", "Asistenta medicala", 4, true},
            {"Akir", "Ravi", "Sisteme electrice", 3, false},
            {"Salaj", "Bogdan", "Economie", 2, true},
            {"Loransz", "Csaba", "Portectia Mediului", 2, true},
            {"Geana", "Cosmina", "Informatica", 2, true}


    };

    private DefaultTableModel model = new DefaultTableModel(data, columnNames);
    private JTable jTable = new JTable(model);

    private TableRowSorter<TableModel> rowSorter
            = new TableRowSorter<>(jTable.getModel());

    private JTextField jtfFilter = new JTextField();
    private JButton jbtFilter = new JButton("Filtru");


    public TestTableSortFilter() {
        jTable.setRowSorter(rowSorter);
        jTable.setBackground(Color.GRAY);
        jTable.setGridColor(Color.magenta);
        jTable.setForeground(Color.white);
        jTable.getTableHeader().setBackground(Color.GRAY);
        jTable.getTableHeader().setForeground(Color.WHITE);
        JButton addRowButton = new JButton("Add Row");

        // add action listener to button
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }

            public void actionPerformed(ActiveEvent e) {
                addRow("", "", "", 0, false);
            }
        });

        // add button to panel
        add(addRowButton);

        ListSelectionListenerImpl listSelectionListener = new ListSelectionListenerImpl(jTable);

        ListSelectionModel select = jTable.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        select.addListSelectionListener(listSelectionListener);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Specificati cuvantul cautat"),
                BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);
        jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Nu e suportat.");
            }

        });


    }

    private void addRow(String s, String s1, String s2, int i, boolean b) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Tabel");
                frame.add(new TestTableSortFilter());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }

        });
    }
}