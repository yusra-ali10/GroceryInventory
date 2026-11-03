# Grocery Store Inventory Management System

**Student:** Yusra Khuhro
**Instructor:** Engr. Asmatullah Zubair
**Department:** Software Engineering, MUET SZAB Campus, Khairpur Mir's

## Description
A Java Swing dashboard that manages a grocery store inventory using a
custom-built Singly Linked List. No arrays or built-in collections
(ArrayList, LinkedList) are used to store the data. Every product is a node.

## Features
- Dashboard cards: total products, total inventory value, low stock items
- Table view with low stock rows highlighted in red
- Click a row to load it into the form for update or delete

## CRUD Operations (Linked List)
| Operation | Method | Description |
|---|---|---|
| Create | `add()` | Insert a product at the end of the list |
| Create | `addAtHead()` | Insert a product at the start of the list |
| Read | `search()` | Find a product by ID using traversal from `head` |
| Read | `display()` | Traverse and show all products |
| Update | `update()` | Search a node and modify its data |
| Delete | `delete()` | Remove a node (head or middle) by relinking `next` |

## Files
- `Product.java`: Node class (id, name, price, quantity, next)
- `InventoryList.java`: Singly Linked List with all CRUD methods
- `Dashboard.java`: Swing GUI (run this file)
- `Main.java`: Console version

## How to Run
1. Install JDK and VS Code with the Extension Pack for Java
2. Clone or download this repository and open the folder in VS Code
3. Open `Dashboard.java` and click **Run**
