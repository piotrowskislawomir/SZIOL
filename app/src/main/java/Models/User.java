package Models;

/**
 * Created by Michał on 2015-04-12.
 */
public class User {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    //private String activation

    public User(String userName, String password, String firstName, String lastName)
    {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getPassword(){
        return this.password;
    }

    public void setUserName(String usrName){
        this.userName = usrName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setUserPassword(String pass){
        this.password = pass;
    }
}
