package shared;

public class User {

    private String email;
    private String userName;
    private String password;
    private boolean newUser;

    public User(String email, String userName, String password){
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
