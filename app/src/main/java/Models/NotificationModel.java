package Models;

import java.io.Serializable;

/**
 * Created by Michał on 2015-05-20.
 */
public class NotificationModel implements Serializable {
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

    public String getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getTicketId()
    {
        return this.ticketId;
    }

    @Override
    public String toString() {
        return "NotificationModel [id=" + id + ", title=" + title + ", description=" + description + ", ticketId=" + ticketId
                + "]";
    }

}
