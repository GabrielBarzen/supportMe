import org.supportmeinc.DatabaseManager;
import org.supportmeinc.ServerLog;

public class getAccessListTest {
    public static void main(String[] args) {
        new getAccessListTest();
        new ServerLog();
    }

    DatabaseManager dbm;
    getAccessListTest() {
        dbm = new DatabaseManager();

        String[] emails = dbm.getAccessList("37d94e27-766c-4c46-8138-118f3d7515c1");

        for (String email: emails) {
            System.out.println("has access : " + email);
        }
    }
}
