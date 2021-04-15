import org.supportmeinc.Authenticator;
import org.supportmeinc.ServerLog;
import org.supportmeinc.UserDatabaseConnection;
import shared.User;

public class databaseConnectionTest {

    private UserDatabaseConnection db;
    private Authenticator auth;
    private User testUser1;
    private User testUser2;
    private User testUser3;
    private User testUser4;

    public static void main(String[] args) {
        new databaseConnectionTest();
    }

    private databaseConnectionTest(){
        db =  new UserDatabaseConnection();
        populateTestUsers();
        testLogin();
    }

    private void populateTestUsers(){
        testUser1 = new User("existinguser@testuser.1", "test1", "test1");
        testUser1.setNewUser(true);

        testUser2 = new User("existinguser@testuser.1", "test1", "test1");
        testUser2.setNewUser(false);

        testUser3 = new User("existinguser@testuser.1", "test1", "test1");
        testUser3.setNewUser(true);

        testUser4 = new User("existinguser@testuser.2", "test2", "test2");
        testUser4.setNewUser(false);
    }

    private void testLogin(){

        //Adds first user to Database, should succeed
        auth = new Authenticator(testUser1, db);
        testUser1 = auth.authenticate();
        if(testUser1 != null) {
            System.out.println(testUser1.getEmail());
        } else {
            System.out.println("Adding user failed");
        }

        //Logins in with first users credentials, should succeed
        auth = new Authenticator(testUser2, db);
        testUser2 = auth.authenticate();
        if(testUser2 != null) {
            System.out.println(testUser2.getEmail());
        } else {
            System.out.println("Login failed");
        }

        //Registers with existing credentials, should fail
        auth = new Authenticator(testUser3, db);
        testUser3 = auth.authenticate();
        if(testUser3 != null) {
            System.out.println(testUser3.getEmail());
        } else {
            System.out.println("Adding user failed");
        }

        //Tries to login with credentials not already registered, should fail
        auth = new Authenticator(testUser4, db);
        testUser4 = auth.authenticate();
        if(testUser4 != null) {
            System.out.println(testUser4.getEmail());
        } else {
            System.out.println("Login failed");
        }



    }


}
