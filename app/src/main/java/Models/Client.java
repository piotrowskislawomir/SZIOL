package Models;

/**
 * Created by Michał on 2015-04-12.
 */
public class Client {

    private String firstName;
    private String lastName;
  //  private String address;
    private String city;
    private String street;
    private String homeNumber;
    private String flatNumber;

    // To nie po stronie serwera ?
    double gpsLatitude;
    double gpsLongtitude;

    private String id;
    private String teamId;
    private String team;


    public Client(String id, String firstName, String lastName, String city, String street, String homeNumber, String flatNumber)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
        this.flatNumber = flatNumber;
       // this.address = address;
    }

    public Client(String firstName, String lastName, String city, String street, String homeNumber, String flatNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
        this.flatNumber = flatNumber;
        // this.address = address;
    }


    public Client(String id, String firstName, String lastName, String street)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
    }


    public String getFirstName(){ return firstName; }

    public String getId(){ return id; }


    public String getLastName(){ return lastName; }

    public String getCity(){ return city; }

    public String getStreet(){ return street; }

    public String getHomeNumber(){ return homeNumber; }

    public String getFlatNumber(){ return flatNumber; }

    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
