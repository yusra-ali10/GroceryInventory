public class Product {
    int id;
    String name;
    double price;
    int quantity;
    Product next;

    Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.next = null;
    }
}