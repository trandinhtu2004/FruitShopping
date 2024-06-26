/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

import Entity.Fruit;
import Entity.Order;
import java.awt.AWTEventMulticaster;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author ADMIN
 */
public class Manager {

    Validation validation = new Validation();

    public int menu() {
        String input = "Input your choice: ";
        String err = "Invalid choice!";
        System.out.println("1. Create Fruit.");
        System.out.println("2. View Orders.");
        System.out.println("3. Shoping (for buyer).");
        System.out.println("4. Exit.");
        int choice = validation.checkInputIntLimit(input, 1, 5);
        return choice;
    }

    public void createFruit(ArrayList<Fruit> lf) {
        while (true) {
            String fruitID;
            fruitID = validation.checkInputString("enter fruitID: ");
            //id exist = out!
            if (!validation.checkIDExist(lf, fruitID)) {
                System.err.println("ID existed");
                return;
            }
            String fruitName;
            fruitName = validation.checkInputString("enter name of fruit: ");
            double price = validation.checkInputPrice("Enter price: ");
            int quantity = validation.checkInputInt("Enter quantity: ");
            String origin = validation.checkInputString("Enter origin: ");

            lf.add(new Fruit(fruitID, fruitName, price, quantity, origin));

            if (!validation.checkInputYN()) {
                return;
            }
        }
    }

    public void viewOrder(Hashtable<String, ArrayList<Order>> orderCustomer) {
        if (orderCustomer.isEmpty()) {
            System.err.println("No order yet");
        }

        for (String name : orderCustomer.keySet()) {
            System.out.println("Customer: " + name);
            ArrayList<Order> lo = orderCustomer.get(name);
            displayListOrder(lo);
        }
    }

     public void shopping(ArrayList<Fruit> lf,
            Hashtable<String, ArrayList<Order>> orderCustomer) {
        boolean productsAvailable = true;
        CheckListFruit(lf);
        DeleteOutOfFruit(lf);
        //if list empty, customer can't buy
        if (lf.isEmpty()) {
            System.err.println("Out of fruit!!!!");
            return; // Thoát khỏi phương thức và quay lại trang chủ
        }
        /*//check if there are fruit that quantity > 0 so the shop can continue buying
        boolean outOfStockinCheck = true;
        for (Fruit fruit : lf) {
            if (fruit.getQuantity() > 0) {
                outOfStockinCheck = false;
                break;
            }

            if (outOfStockinCheck) {
                System.err.println("Out of fruit.");
                return;
            }
        }*/
        ArrayList<Order> lo = new ArrayList<>();
        while (true) {
            //check if store has fruits
            CheckListFruit(lf);
            boolean outOfStock = true;
            //kiem tra xem fruit co out of stock
            for (Fruit fruit : lf) {
                if (fruit.getQuantity() > 0) {
                    outOfStock = false;
                    break;
                }
            }
            if (outOfStock) {
                System.err.println("Out of fruit.");
                displayListOrder(lo);
                String nameCustomer = validation.checkInputString("Enter name: ");
                orderCustomer.put(nameCustomer, lo);
                System.err.println("Add successful!");
                return;
            }
            displayListfruit(lf);
            //end kiem tra xem fruit co out of stock
            int item = validation.checkInputInt("Get your Item on list: ");
            if (item == 0){
                System.err.print("\nReturn to the menu\n");
                return;
            }
            Fruit fruit = getFruitByItem(lf, item - 1);
            if (fruit == null) {
                System.err.println("Invalid item. Please try again.");
                continue;
            }
            int quantity = validation.checkInputIntLimit("Enter quantity: ",
                    1, fruit.getQuantity());
            //minor quantity of item.
            fruit.setQuantity(fruit.getQuantity() - quantity);

            // item exist or not
            if (!validation.checkItemExist(lo, fruit.getFruitID())) {
                updateOrder(lo, fruit.getFruitID(), quantity);
            } else {
                lo.add(new Order(fruit.getFruitID(), fruit.getFruitName(),
                        quantity, fruit.getPrice()));
            }

            if (!validation.checkInputYN()) {
                break;
            }
        }
        displayListOrder(lo);
        String nameCustomer = validation.checkInputString("Enter name: ");
        //orderCustomer.put(nameCustomer, lo);
        if (orderCustomer.containsKey(nameCustomer)) {
        mergeOrders(orderCustomer.get(nameCustomer), lo);
    } else {
        orderCustomer.put(nameCustomer, lo);
    }
        System.err.println("Add successful!");

    }
     
     public void mergeOrders(ArrayList<Order> existingOrders, ArrayList<Order> newOrders) {
    for (Order newOrder : newOrders) {
        boolean orderExists = false;
        for (Order existingOrder : existingOrders) {
            if (existingOrder.getFruitID().equals(newOrder.getFruitID())) {
                existingOrder.setQuantity(existingOrder.getQuantity() + newOrder.getQuantity());
                orderExists = true;
                break;
            }
        }
        if (!orderExists) {
            existingOrders.add(newOrder);
        }
    }
}
     
    // check if there are fruits in the shop
    public String CheckListFruit(ArrayList<Fruit> lf){
        int countItem = 1;
        for (Fruit fruit : lf) {
            //check shop have item or not 
            if (fruit.getQuantity() != 0) {
                return fruit.toString();
            }
        }
        return null;
        } 
    
    
    //display fruits in shop
    public void displayListfruit(ArrayList<Fruit> lf) {
        int countItem = 1;
        System.out.printf("%-10s%-20s%-20s%-20s\n", "| ++ Item ++ |",
                "++ Fruit name ++ |", " ++ Origin ++ |",
                " ++ Price ++ |", " ++ Quantity ++ |");
        for (Fruit fruit : lf) {
            //check shop have item or not 
            if (fruit.getQuantity() != 0) {
                System.out.printf("\t%-12s%-17s%-20s%5.0f$\n", countItem++,
                        fruit.getFruitName(), fruit.getOrigin(),
                        fruit.getPrice());
            }
        }
    }
    //admin to check the code

    public void DeleteOutOfFruit(ArrayList<Fruit> lf) {
        //System.out.println("List of unavailable food: ");
        int countItem = 1;
        ArrayList<Fruit> itemsToRemove = new ArrayList<>(); // List of items to remove
        //System.out.printf("%-15s%-10s%-20s%-20s%-15s\n", "ID", "Item", "Fruit name", "Origin", "Price");
        for (Fruit fruit : lf) {
            if (fruit.getQuantity() == 0) {
                /*System.out.printf("%-15s%-10d%-20s%-20s%-15.0f$\n",
       fruit.getFruitID(), countItem++, fruit.getFruitName(),
       fruit.getOrigin(), fruit.getPrice());*/
                itemsToRemove.add(fruit); // Add item to remove list
                continue; // Skip this item and continue loop
            }
        }
        // Remove out-of-stock items from the main list
        lf.removeAll(itemsToRemove);
    }

    public void displayListOrder(ArrayList<Order> lo) {
        double total = 0;
        String DISPLAYLISTORDER = "Product | Quantity | Price | Amount";
        System.out.println(DISPLAYLISTORDER);
        for (Order order : lo) {
            System.out.printf("%s%8d%10.0f$%8.0f$\n", order.getFruitName(), order.getQuantity(),
                    order.getPrice(), (order.getPrice() * order.getQuantity()));
            total += order.getPrice() * order.getQuantity();
        }
        System.out.println("Total: " + total + "$");
    }

    public Fruit getFruitByItem(ArrayList<Fruit> lf, int item) {
        int countItem = 0;
        for (Fruit fruit : lf) {
            if (fruit.getQuantity() != 0) {
                if (countItem == item) {
                    return fruit;
                }
                countItem++;
            }
        }
        return null;
    }

    void updateOrder(ArrayList<Order> lo, String ID, int quantity) {
        for (Order order : lo) {
            if (order.getFruitID().equalsIgnoreCase(ID)) {
                order.setQuantity(order.getQuantity() + quantity);
                return;
            }
        }
    }
    
    public void generateFruit(ArrayList<Fruit> lf){
        lf.add(new Fruit("1", "banana", 5,150, "Vietnam"));
        lf.add(new Fruit("2", "apple", 5,150, "Vietnam"));
        lf.add(new Fruit("3", "mango", 5,150, "Vietnam"));
    }
}
