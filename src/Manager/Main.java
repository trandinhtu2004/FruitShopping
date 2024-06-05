/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;
import Entity.Fruit;
import Entity.Order;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author ADMIN
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Fruit> lf = new ArrayList<>();
        Hashtable<String, ArrayList<Order>> customerOrder = new Hashtable<>();
        Validation validation = new Validation();
        Manager manager = new Manager();
        manager.generateFruit(lf);
        while (true){
            int choice = manager.menu();
            switch (choice) {
                case 1:
                    manager.createFruit(lf);
                    break;
                case 2:
                    manager.viewOrder(customerOrder);
                    break;
                case 3:
                    manager.shopping(lf, customerOrder);
                    break;
                case 4:
                    return;
                default:
                    throw new AssertionError();
            }
        }
        
    }
}
