package Models;

/**
 * Created by Michał on 2015-05-20.
 */
public class NotificationModel {
    String id;
    String title;
    String description;
    String ticketId;

    public NotificationModel(String id, String title, String description, String ticketId)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ticketId = ticketId;
    }

    public String getTicketId()
    {
        return this.ticketId;
    }

    public String getNotificationId()
    {
        return this.id;
    }

}
