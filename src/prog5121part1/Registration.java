/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prog5121part1;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Registration {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner input = new Scanner(System.in);
        
        System.out.println("Registration");
        
        System.out.print("Enter your first name: ");
        String firstname = input.nextLine();
        
        System.out.print("Enter your last name: ");
        String lastname = input.nextLine();
        
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        
        System.out.print("Enter your cell phone number: ");
        String cellphoneNumber = input.nextLine();
        
        Login user = new Login(
               username,
                password,
                cellphoneNumber,
                firstname,
                lastname
        );
        
        System.out.println();
        
        String registrationMessage = user.registerUser();
        
        if (registrationMessage.equals("Registration successful.")) {
            
            System.out.println();
            System.out.println("Login");
            
            System.out.print("Enter your username: ");
            String Username = input.nextLine();
            
            System.out.print("Enter your password: ");
            String Password = input.nextLine();
            
            boolean loginSuccessful = user.loginUser(
                            Username,
                            Password
            
            );
            
            if (loginSuccessful)  {
                System.out.println("Welcome" + " " + firstname + " " + lastname+ "," + "it is great to see you again.");
            } else {
                System.out.println("Username or Password incorrect, please try again.");
            }
        }
        input.close();
    }
    
}
