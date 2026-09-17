package prog5121part1;
import java.util.regex.Pattern;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Student
 */
// This class validates user details and handles login functionality.
public class Login {
    
    //Declare the user's registration information
    private String username;
    private String password;
    private String cellphoneNumber;
    private String firstname;
     private String lastname;
     
     //Create a variable that will store whether the login was a success
     private boolean loginSuccessful;
     
     public Login(String username,String password,String cellphoneNumber,String firstname, String lastname) {
         this.username = username;
         this.password = password;
         this.cellphoneNumber = cellphoneNumber;
         this.firstname = firstname;
         this.lastname = lastname;
     } 
    
     //Creating a method to check if the username contains an underscore and is no more than 5 characters
     public boolean checkUserName() {
         return username !=null && username.contains("_") && username.length() <= 5;
         
     }
    //Checks if password has atlest 8 characters, a capital letter, a number and special character     
     public boolean checkPasswordComplexity() {
         
         if (password ==null || password.length() <8) {
             return false;
       
         } 
         boolean hasCapitalLetter = false;
         boolean hasNumber = false;
         boolean hasSpecialCharacter = false;
         
         //create a loop that will go check each character in the password
         for (int i = 0; i < password.length(); i++) {
             
             char character = password.charAt(i);
                
             if (Character.isUpperCase(character)) {
                 hasCapitalLetter = true;
             }
   
             if (Character.isDigit(character)){
                 hasNumber = true;
             }
         
             if (!Character.isLetterOrDigit(character)){
                 hasSpecialCharacter = true;
             }
           
         }
        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
        
         }
     
     
         //checks the cellphone number if its South African
         public boolean checkCellPhoneNumber(){
             
             if (cellphoneNumber == null) {
             return false;
             
         }
         
         String regex = "\\+27[0-9]{9}$";
         
         return Pattern.matches (regex, cellphoneNumber);
         
}
       public String registerUser()  {
           
           if (!checkUserName()) {
               return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters.";
    
           }
           
           if (!checkPasswordComplexity()) {
               return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, and a special character.";
               
           }  
           
           if (!checkCellPhoneNumber()) {
               return "Cell phone number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
           }
           
           return "Registration successful.";
       }
       
       //Compares the username and password entered during login with the details stored during registration.
           public boolean loginUser(String enteredUsername, String enteredPassword) {
               loginSuccessful = username.equals(enteredUsername) && password.equals(enteredPassword);
               
               return loginSuccessful;
               
           }
           
           public String returnLoginStatus() {
               if (loginSuccessful){
                  return ("Welcome" + " " + firstname + " " + lastname+ "," + "it is great to see you again.");
               }
    
               return "Username or Password is incorrect, please try again.";
           }
           
    
}
       
    

