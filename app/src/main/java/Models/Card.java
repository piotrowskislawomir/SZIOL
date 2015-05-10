package Models;

/**
 * Created by Michał on 2015-05-10.
 */
public class Card {
        private int cardId;
        private String firstName;
        private String lastName;

    public Card(int carId, String firstName, String lastName)
    {
        this.cardId = carId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getCardId(){return cardId;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}


}
