package services.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import models.TicketModel;

/**
 * Created by Slawek on 2015-07-15.
 */
public class DatabaseLogic {
    private SQLiteDatabase db;

    public DatabaseLogic(Context context)
    {
        TicketsDbHelper ticketsDbHelper = new TicketsDbHelper(context);
        db = ticketsDbHelper.getWritableDatabase();
    }

    public void InsertTicket(TicketModel ticket)
    {
        ContentValues values = new ContentValues();
        values.put(TicketsContract.TicketEntry.COLUMN_NAME_ID, ticket.getId());
        values.put(TicketsContract.TicketEntry.COLUMN_NAME_TITLE, ticket.getTitle());

        long newRowId  = db.insert(
                        TicketsContract.TicketEntry.TABLE_NAME,
                        null,
                        values);
    }

    public TicketModel GetTicket(int id)
    {
        String selectQuery = "SELECT " +
                TicketsContract.TicketEntry.COLUMN_NAME_ID + ", " +
                TicketsContract.TicketEntry.COLUMN_NAME_TITLE + " FROM " +
                TicketsContract.TicketEntry.TABLE_NAME + " WHERE " +
                TicketsContract.TicketEntry.COLUMN_NAME_ID + "=?";

        Cursor c = db.rawQuery(selectQuery, new String[]{Integer.toString(id)});

        if(c.moveToFirst())
        {
            TicketModel ticket = new TicketModel();
            int index = c.getColumnIndexOrThrow(TicketsContract.TicketEntry.COLUMN_NAME_ID);
            ticket.setId(c.getInt(index));
            index = c.getColumnIndexOrThrow(TicketsContract.TicketEntry.COLUMN_NAME_TITLE);
            ticket.setTitle(c.getString(index));

            return ticket;
        }

        return null;
    }

    public List<TicketModel> GetTickets()
    {
        String selectQuery = "SELECT " +
                TicketsContract.TicketEntry.COLUMN_NAME_ID + ", " +
                TicketsContract.TicketEntry.COLUMN_NAME_TITLE + " FROM " +
                TicketsContract.TicketEntry.TABLE_NAME;

        Cursor c = db.rawQuery(selectQuery, null);

        ArrayList<TicketModel> list = new ArrayList<>();
        while(c.moveToNext())
        {
            TicketModel ticket = new TicketModel();
            int index = c.getColumnIndexOrThrow(TicketsContract.TicketEntry.COLUMN_NAME_ID);
            ticket.setId(c.getInt(index));
            index = c.getColumnIndexOrThrow(TicketsContract.TicketEntry.COLUMN_NAME_TITLE);
            ticket.setTitle(c.getString(index));

            list.add(ticket);
        }

        return list;
    }
}
