/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;
import Entity.Fruit;
import Entity.Order;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.midi.SysexMessage;

/**
 *
 * @author ADMIN
 */
public class Validation {
    Scanner in = new Scanner(System.in);
    
    public int checkInputIntLimit(String input,int min, int max) {
        System.out.println(input);
        //loop until user input correct
        while (true) {
            try {
                int result = Integer.parseInt(in.nextLine().trim());
                if (result < min || result > max) {
                    throw new NumberFormatException();

                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input number in rage [" + min + ", " + max + "]");
                System.out.print("Enter again: ");
            }
        }
    }
    
     //check user input string
    public String checkInputString(String input) {
        System.out.println(input);
        //loop until user input correct
        while (true) {
            String result = in.nextLine().trim();
            if (result.isEmpty() || result.equalsIgnoreCase("")) {
                System.err.println("Not empty");
                System.out.print("Enter again: ");
            } else {
                return result;
            }
        }
    }
    
    public int checkInputInt(String input) {
        System.out.println(input);
        //loop until user input correct
        while (true) {
            try {
                int result = Integer.parseInt(in.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Must be input integer.");
                System.out.print("Enter again: ");
            }
        }
    }
    public int checkInputTakeList(String input) {
        System.out.println(input);
        //loop until user input correct
        while (true) {
            try {
                int result = Integer.parseInt(in.nextLine().trim());
                if (result == 0){
                    System.out.println("Go back to the Fruit management");
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Must be input integer.");
                System.out.print("Enter again: ");
            }
        }
    }
    
    public double checkInputPrice(String input) {
        System.out.println(input);
        //loop until user input correct
        while (true) {
            try {
                double result = Double.parseDouble(in.nextLine().trim());
                if (result < 0){
                    System.err.println("price must >= 0");
                    System.out.println("Try again: ");
                }else
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Must be input double");
                System.out.print("Enter again: ");
            }

        }
    }
    
    public double checkInputDouble(String input) {
        System.out.println(input);
        //loop until user input correct
        while (true) {
            try {
                double result = Double.parseDouble(in.nextLine().trim());
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Must be input double");
                System.out.print("Enter again: ");
            }

        }
    }
    
    public boolean checkInputYN(){
        System.out.println("Do you want to continue? (Y/N): ");
        while (true){
            String result = in.nextLine().trim();
            
            if (result.equalsIgnoreCase("Y")){
                return true;
            }
            
            if (result.equalsIgnoreCase("N")){
                return false;
            }
            
            System.err.println("Please input y/Y or n/N.");
            System.out.print("Enter again: ");
        }
    }
    
    public boolean checkIDExist(ArrayList<Fruit> lf, String id){
        for (Fruit fruit : lf) {
            if (id.equalsIgnoreCase(fruit.getFruitID())){
                return false;
            }
        }
        return true;
    }
    
    public boolean checkItemExist(ArrayList<Order> lo, String id){
        for (Order order : lo) {
            if (id.equalsIgnoreCase(order.getFruitID())) {
                return false;
            }
        }
        return true;
    }
    
}
