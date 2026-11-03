public class InventoryList {
    private Product head;

    // CREATE: end mein add
    public void add(int id, String name, double price, int quantity) {
        if (search(id) != null) {
            System.out.println("Is ID ka product pehle se maujood hai!");
            return;
        }
        Product p = new Product(id, name, price, quantity);
        if (head == null) {
            head = p;
        } else {
            Product temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = p;
        }
        System.out.println("Product add ho gaya.");
    }

    // CREATE: shuru mein add
    public void addAtHead(int id, String name, double price, int quantity) {
        if (search(id) != null) {
            System.out.println("Is ID ka product pehle se maujood hai!");
            return;
        }
        Product p = new Product(id, name, price, quantity);
        p.next = head;
        head = p;
        System.out.println("Product shuru mein add ho gaya.");
    }

    // READ: sab display
    public void display() {
        if (head == null) {
            System.out.println("Inventory khali hai.");
            return;
        }
        System.out.printf("%-6s %-15s %-10s %-8s%n", "ID", "Name", "Price", "Qty");
        Product temp = head;
        while (temp != null) {
            System.out.printf("%-6d %-15s %-10.2f %-8d%n",
                    temp.id, temp.name, temp.price, temp.quantity);
            temp = temp.next;
        }
    }

    // READ: ID se search
    public Product search(int id) {
        Product temp = head;
        while (temp != null) {
            if (temp.id == id) return temp;
            temp = temp.next;
        }
        return null;
    }

    // UPDATE
    public void update(int id, String name, double price, int quantity) {
        Product p = search(id);
        if (p == null) {
            System.out.println("Product nahi mila.");
            return;
        }
        p.name = name;
        p.price = price;
        p.quantity = quantity;
        System.out.println("Product update ho gaya.");
    }

    // DELETE
    public void delete(int id) {
        if (head == null) {
            System.out.println("Inventory khali hai.");
            return;
        }
        if (head.id == id) {
            head = head.next;
            System.out.println("Product delete ho gaya.");
            return;
        }
        Product temp = head;
        while (temp.next != null && temp.next.id != id) temp = temp.next;
        if (temp.next == null) {
            System.out.println("Product nahi mila.");
        } else {
            temp.next = temp.next.next;
            System.out.println("Product delete ho gaya.");
        }
    }

    // BONUS: low stock alert (console)
    public void lowStock(int limit) {
        boolean found = false;
        Product temp = head;
        while (temp != null) {
            if (temp.quantity <= limit) {
                System.out.println("LOW STOCK: " + temp.name + " (Qty: " + temp.quantity + ")");
                found = true;
            }
            temp = temp.next;
        }
        if (!found) System.out.println("Koi product low stock mein nahi hai.");
    }

    // BONUS: total inventory value
    public double totalValue() {
        double total = 0;
        Product temp = head;
        while (temp != null) {
            total += temp.price * temp.quantity;
            temp = temp.next;
        }
        return total;
    }

    // ---------- Dashboard ke liye helpers ----------
    public Product getHead() {
        return head;
    }

    public int count() {
        int c = 0;
        Product temp = head;
        while (temp != null) { c++; temp = temp.next; }
        return c;
    }

    public int lowStockCount(int limit) {
        int c = 0;
        Product temp = head;
        while (temp != null) {
            if (temp.quantity <= limit) c++;
            temp = temp.next;
        }
        return c;
    }
}