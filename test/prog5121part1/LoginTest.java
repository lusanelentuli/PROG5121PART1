/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121part1;
// Unit tests for Login class.
import org.junit.Test;
import prog5121part1.Login;
import static org.junit.Assert.assertEquals;
/**
 *
 * @author Student
 */
public class LoginTest {
    
    @Test
    public void testCheckUserNameCorrect() {
        Login user = new Login(
                "ky1_1",
                "Ch&&sec@ke99!",
                "+27838768976",
                "John",
                "Smith"
        );
        
        assertEquals(true, user.checkUserName());
    }
    
    @Test
    public void testCheckPasswordComplexityCorrect() {
        Login user = new Login(
                "ky1_1",
                "Ch&&sec@ke99!",
                "+27838768976",
                "John",
                "Smith"
        );
        
        assertEquals(true, user.checkPasswordComplexity());
    }
    
    @Test
    public void testCheckCellPhooneCorrrect() {
        Login user = new Login(
                "ky1_1",
                "Ch&&sec@ke99!",
                "+27838768976",
                "John",
                "Smith"
        );
        
        assertEquals(true, user.checkCellPhoneNumber());
    }
    
    @Test
    
   public void testCheckCellPhooneInCorrrect() {
        Login user = new Login(
                "ky1_1",
                "Ch&&sec@ke99!",
                "08966553",
                "John",
                "Smith"
        );
        
       assertEquals(false, user.checkCellPhoneNumber());
       
   }
       @Test
       public void testLoginSuccessful() {
        Login user = new Login(
                "ky1_1",
                "Ch&&sec@ke99!",
                "+27838768976",
                "John",
                "Smith"
        );
        
        assertEquals(true, user.loginUser("ky1_1", "Ch&&sec@ke99!"));
        
        
   }
       @Test
       public void testLoginFailed() {
        Login user = new Login(
                "ky1_1",
                "Ch&&sec@ke99!",
                "+27838768976",
                "John",
                "Smith"
        );
        
        assertEquals(false, user.loginUser("ky1_1", "password"));
    
    }
}
