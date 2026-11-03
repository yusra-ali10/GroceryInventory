import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InventoryList inv = new InventoryList();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== GROCERY STORE INVENTORY =====");
            System.out.println("1. Add Product (End)");
            System.out.println("2. Add Product (Start)");
            System.out.println("3. Display All");
            System.out.println("4. Search by ID");
            System.out.println("5. Update Product");
            System.out.println("6. Delete Product");
            System.out.println("7. Low Stock Alert");
            System.out.println("8. Total Inventory Value");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1, 2 -> {
                    System.out.print("ID: "); int id = sc.nextInt();
                    System.out.print("Name: "); String name = sc.next();
                    System.out.print("Price: "); double price = sc.nextDouble();
                    System.out.print("Quantity: "); int qty = sc.nextInt();
                    if (choice == 1) inv.add(id, name, price, qty);
                    else inv.addAtHead(id, name, price, qty);
                }
                case 3 -> inv.display();
                case 4 -> {
                    System.out.print("ID: ");
                    Product p = inv.search(sc.nextInt());
                    if (p == null) System.out.println("Product nahi mila.");
                    else System.out.println(p.id + " | " + p.name + " | " + p.price + " | " + p.quantity);
                }
                case 5 -> {
                    System.out.print("ID: "); int id = sc.nextInt();
                    System.out.print("New Name: "); String name = sc.next();
                    System.out.print("New Price: "); double price = sc.nextDouble();
                    System.out.print("New Quantity: "); int qty = sc.nextInt();
                    inv.update(id, name, price, qty);
                }
                case 6 -> {
                    System.out.print("ID: ");
                    inv.delete(sc.nextInt());
                }
                case 7 -> inv.lowStock(10);
                case 8 -> System.out.println("Total Value: Rs. " + inv.totalValue());
                case 0 -> System.out.println("Khuda hafiz!");
                default -> System.out.println("Galat choice.");
            }
        } while (choice != 0);
         sc.close();   // <-- ye line add karein
    }
}