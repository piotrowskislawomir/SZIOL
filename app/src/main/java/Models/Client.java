package Models;

/**
 * Created by Michał on 2015-04-12.
 */
public class Client {

    private String firstName;
    private String lastName;
    private String address;
    private String id;
    private String teamId;
    private String team;


    public Client(String firstName, String lastName, String address)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Client(String id, String firstName, String lastName, String address)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getAddress()
    {
        return address;
    }


}
