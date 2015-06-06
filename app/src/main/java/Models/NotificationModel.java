package Models;

import java.io.Serializable;

/**
 * Created by Michał on 2015-05-20.
 */
public class NotificationModel implements Serializable {
    private final String type;
    String id;
    String title;
    String description;
    String ticketId;

    public NotificationModel(String id, String title, String description, String ticketId, String type)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ticketId = ticketId;
        this.type = type;
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

    public String getType()
    {
        return this.type;
    }

    @Override
    public String toString() {
        return "NotificationModel [id=" + id + ", title=" + title + ", description=" + description +
               ", ticketId=" + ticketId + ", type=" + type
                + "]";
    }

}
