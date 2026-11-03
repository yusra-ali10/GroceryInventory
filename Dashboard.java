import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Dashboard extends JFrame {
    private final InventoryList inv = new InventoryList();
    private final DefaultTableModel model;
    private final JTable table;
    private final JTextField idF = new JTextField();
    private final JTextField nameF = new JTextField();
    private final JTextField priceF = new JTextField();
    private final JTextField qtyF = new JTextField();
    private final JTextField searchF = new JTextField(10);
    private final JLabel totalProductsL = new JLabel("0");
    private final JLabel totalValueL = new JLabel("Rs. 0");
    private final JLabel lowStockL = new JLabel("0");
    private static final int LOW = 10;

    public Dashboard() {
        setTitle("Grocery Store Inventory Dashboard");
        setSize(900, 620);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 242, 245));

        // ---------- Header ----------
        JLabel title = new JLabel("  Grocery Store Inventory (Linked List)", JLabel.LEFT);
        title.setOpaque(true);
        title.setBackground(new Color(30, 41, 59));
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setPreferredSize(new Dimension(0, 55));

        // ---------- Summary cards ----------
        JPanel cards = new JPanel(new GridLayout(1, 3, 15, 0));
        cards.setOpaque(false);
        cards.add(makeCard("Total Products", totalProductsL, new Color(59, 130, 246)));
        cards.add(makeCard("Total Inventory Value", totalValueL, new Color(16, 185, 129)));
        cards.add(makeCard("Low Stock Items", lowStockL, new Color(239, 68, 68)));

        JPanel top = new JPanel(new BorderLayout(0, 10));
        top.setOpaque(false);
        top.add(title, BorderLayout.NORTH);
        JPanel cardWrap = new JPanel(new BorderLayout());
        cardWrap.setOpaque(false);
        cardWrap.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        cardWrap.add(cards);
        top.add(cardWrap, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // ---------- Table ----------
        model = new DefaultTableModel(new String[]{"ID", "Name", "Price", "Quantity"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionBackground(new Color(191, 219, 254));
        table.setSelectionForeground(Color.BLACK);

        // low stock rows laal dikhayen
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
                                                           boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                int qty = Integer.parseInt(model.getValueAt(row, 3).toString());
                if (!sel) c.setBackground(qty <= LOW ? new Color(254, 226, 226) : Color.WHITE);
                return c;
            }
        });

        // row click par form bhar jaye
        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0 && !e.getValueIsAdjusting()) {
                idF.setText(model.getValueAt(r, 0).toString());
                nameF.setText(model.getValueAt(r, 1).toString());
                priceF.setText(model.getValueAt(r, 2).toString());
                qtyF.setText(model.getValueAt(r, 3).toString());
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(sp, BorderLayout.CENTER);

        // ---------- Form + Buttons ----------
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 5));
        form.setOpaque(false);
        form.add(new JLabel("ID")); form.add(new JLabel("Name"));
        form.add(new JLabel("Price")); form.add(new JLabel("Quantity"));
        form.add(idF); form.add(nameF); form.add(priceF); form.add(qtyF);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        btns.setOpaque(false);
        btns.add(makeBtn("Add (End)", new Color(59, 130, 246), e -> addProduct(false)));
        btns.add(makeBtn("Add (Start)", new Color(99, 102, 241), e -> addProduct(true)));
        btns.add(makeBtn("Update", new Color(245, 158, 11), e -> updateProduct()));
        btns.add(makeBtn("Delete", new Color(239, 68, 68), e -> deleteProduct()));
        btns.add(makeBtn("Clear", new Color(100, 116, 139), e -> clearForm()));
        btns.add(new JLabel("   Search ID:"));
        btns.add(searchF);
        btns.add(makeBtn("Search", new Color(16, 185, 129), e -> searchProduct()));

        JPanel bottom = new JPanel(new BorderLayout(0, 5));
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        bottom.add(form, BorderLayout.NORTH);
        bottom.add(btns, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    // ---------- CRUD actions (Linked List methods use ho rahe hain) ----------
    private void addProduct(boolean atStart) {
        try {
            int id = Integer.parseInt(idF.getText().trim());
            String name = nameF.getText().trim();
            double price = Double.parseDouble(priceF.getText().trim());
            int qty = Integer.parseInt(qtyF.getText().trim());
            if (name.isEmpty()) { msg("Name likhein."); return; }
            if (inv.search(id) != null) { msg("Is ID ka product pehle se maujood hai!"); return; }
            if (atStart) inv.addAtHead(id, name, price, qty);
            else inv.add(id, name, price, qty);
            clearForm();
            refresh();
        } catch (NumberFormatException ex) {
            msg("ID, Price aur Quantity mein sirf number likhein.");
        }
    }

    private void updateProduct() {
        try {
            int id = Integer.parseInt(idF.getText().trim());
            if (inv.search(id) == null) { msg("Product nahi mila."); return; }
            inv.update(id, nameF.getText().trim(),
                    Double.parseDouble(priceF.getText().trim()),
                    Integer.parseInt(qtyF.getText().trim()));
            clearForm();
            refresh();
        } catch (NumberFormatException ex) {
            msg("ID, Price aur Quantity mein sirf number likhein.");
        }
    }

    private void deleteProduct() {
        try {
            int id = Integer.parseInt(idF.getText().trim());
            if (inv.search(id) == null) { msg("Product nahi mila."); return; }
            int ok = JOptionPane.showConfirmDialog(this, "Product " + id + " delete karna hai?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                inv.delete(id);
                clearForm();
                refresh();
            }
        } catch (NumberFormatException ex) {
            msg("Pehle valid ID likhein ya table se row select karein.");
        }
    }

    private void searchProduct() {
        try {
            int id = Integer.parseInt(searchF.getText().trim());
            Product p = inv.search(id);
            if (p == null) { msg("Product nahi mila."); return; }
            for (int i = 0; i < model.getRowCount(); i++) {
                if (Integer.parseInt(model.getValueAt(i, 0).toString()) == id) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    break;
                }
            }
        } catch (NumberFormatException ex) {
            msg("Search ke liye valid ID likhein.");
        }
    }

    // ---------- Helpers ----------
    private void refresh() {
        model.setRowCount(0);
        Product t = inv.getHead();           // linked list traversal
        while (t != null) {
            model.addRow(new Object[]{t.id, t.name, t.price, t.quantity});
            t = t.next;
        }
        totalProductsL.setText(String.valueOf(inv.count()));
        totalValueL.setText("Rs. " + String.format("%.2f", inv.totalValue()));
        lowStockL.setText(String.valueOf(inv.lowStockCount(LOW)));
    }

    private void clearForm() {
        idF.setText(""); nameF.setText(""); priceF.setText(""); qtyF.setText("");
        table.clearSelection();
    }

    private void msg(String m) {
        JOptionPane.showMessageDialog(this, m);
    }

    private JPanel makeCard(String label, JLabel value, Color color) {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBackground(color);
        p.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel l = new JLabel(label);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        value.setForeground(Color.WHITE);
        value.setFont(new Font("Segoe UI", Font.BOLD, 26));
        p.add(l);
        p.add(value);
        return p;
    }

    private JButton makeBtn(String text, Color color, java.awt.event.ActionListener al) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.addActionListener(al);
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}
