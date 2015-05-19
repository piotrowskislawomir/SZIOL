package sziolmobile;

import com.example.slawek.sziolmobile.Coordinate;
import com.example.slawek.sziolmobile.Order;
import com.example.slawek.sziolmobile.UserLog;
import com.example.slawek.sziolmobile.UserReg;

import org.json.JSONException;
import org.json.JSONObject;

import Models.Client;
import Models.User;

/**
 * Created by Slawek on 2015-03-21.
 */

public class RestService {
    public RestClientService _restClientService;
    public final String CoordinateResource = "coordinates";
    public final String Users = "Users";
    public final String Customers = "Customers";
    public final String Tickets = "Tickets";
    public final String Notifications = "Notifications";
    public final String Coordinates = "Coordinates";
    public final String Cards = "Cards";


    public RestService(RestClientService restClientService)
    {
        _restClientService = restClientService;
    }


    public int SendClientRegistry(User usr, int registryKey)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("UserName", usr.getUserName());
            jsonData.put("Password", usr.getPassword());
            jsonData.put("FirstName", usr.getFirstName());
            jsonData.put("LastName", usr.getLastName());
            jsonData.put("ActivationCode", registryKey);

        }
        catch (JSONException jex)
        {}
        return _restClientService.SendPost(Users, jsonData.toString());
    }

    public int SendClientLogin(String login, String pass)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("UserName", login);
            jsonData.put("Password", pass);
        }
        catch (JSONException jex)
        {}
        return _restClientService.PutPost(Users, jsonData.toString());
    }

    public int GetClientById(int id)
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Customers+"/"+id);
    }

    public int GetCustomerCard(int idCard)
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Cards+"/"+idCard);
    }


    public int GetAllCustomers() // co jesli inne gruby zawodowe ???
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Customers);
    }

    public int GetAllTeam() // co jesli inne gruby zawodowe ???
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Cards);
    }

    public int AddNewCustomer(Client client)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("FirstName", client.getFirstName());
            jsonData.put("LastName", client.getLastName());
           // jsonData.put("Address", client.getAddress());
            jsonData.put("City", client.getCity());
            jsonData.put("Street", client.getStreet());
            jsonData.put("HomeNo", client.getHomeNumber());
            jsonData.put("FlatNo", client.getFlatNumber());
            jsonData.put("GpsLatitude", 50.343);
            jsonData.put("GpsLongitude", 58.343);
        }


        catch (JSONException jex)
        {}
        _restClientService.SetToken(UserLog.token);
        return _restClientService.SendPost(Customers, jsonData.toString());
    }

    public int EditCustomer(int id, Client client)
    {
    //    Url: http://s384027.iis.wmi.amu.edu.pl/api/Customers/12?token=1234
        {
            JSONObject jsonData = new JSONObject();
            try {
                jsonData.put("FirstName", client.getFirstName());
                jsonData.put("LastName", client.getLastName());
                jsonData.put("City", client.getCity());
                jsonData.put("Street", client.getStreet());
                jsonData.put("HomeNo", client.getHomeNumber());
                jsonData.put("FlatNo", client.getFlatNumber());
                jsonData.put("GpsLatitude", 54.222);
                jsonData.put("GpsLongitude", 11.333);
  }
            catch (JSONException jex)
            {}
            _restClientService.SetToken(UserLog.token);
            return _restClientService.PutPost(Customers+"/"+id, jsonData.toString());

        }


    }


    public int DeleteCustomer(String id)
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.DeletePost(Customers+"/"+id);

    }

   public int DeleteTicket(int id)
    {
       // Url: http://s384027.iis.wmi.amu.edu.pl/api/Tickets/10?token=1234

        _restClientService.SetToken(UserLog.token);
        return _restClientService.DeletePost(Tickets+"/"+id);

    }



    public int AddNewOrder(Order order)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("Title", order.getTitle());
            jsonData.put("Description", order.getDescription());
            jsonData.put("Status", order.getStatus());
            jsonData.put("CustomerId", order.getCustomerId());
        }
        catch (JSONException jex)
        {}
        _restClientService.SetToken(UserLog.token);
        return _restClientService.SendPost(Tickets, jsonData.toString());
    }

    /*
    public int AddOrder(Order order)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("Title", order.getTitle());
            jsonData.put("Description", order.getDescription());
            jsonData.put("Status", order.getStatus());
            jsonData.put("CustomerId", order.getCustomerId());
        }
        catch (JSONException jex)
        {}
        _restClientService.SetToken(UserLog.token);
        _restClientService.SendPost(Tickets, jsonData.toString());
    }
    */

    public int GetAllOrders() // co jesli inne gruby zawodowe ???
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Tickets);
    }

    public int GetOrder(int id) // co jesli inne gruby zawodowe ???
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Tickets+"/"+id);
    }

    public int GetNotification()
    {
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Notifications);

    }


    public int GetCards()
    {
        // Url: http://s384027.iis.wmi.amu.edu.pl/api/Cards?token=1234
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Cards);
    }

    public int EditOrder(int id, Order order) {
        //13.	Edycja zgłoszenia
        // Typ: put
        // Url: http://s384027.iis.wmi.amu.edu.pl/api/Tickets/10?token=1234


        /* TO DOSTANĘ ZAPISUJE DO OBIEKTU I NASTĘPNIE CZĘSC DANYCH MOGĘ ZMIENIC
        "Title": "ticket4",
            "Description": "desc4",
            "CreatorId": 10,
            "Creator": null,
            "ExecutorId": 10,
            "CreateDate": "2015-04-19T16:36:56.95",
            "TeamId": 5,
            "Status": "C ",
            "CustomerId": 1
*/
        JSONObject jsonData = new JSONObject();
        try {
            // from class Order
            jsonData.put("Description", order.getDescription());
            jsonData.put("CustomerId", order.getCustomerId());
            jsonData.put("Status", order.getStatus());
            jsonData.put("Title", order.getTitle());
            jsonData.put("ExecutorId", order.getExecutorId());
            jsonData.put("AssignToTicket", order.getAssignToTicket());



            // ?????????
            //    jsonData.put("HomeNo", client.getHomeNumber());
            //    jsonData.put("FlatNo", client.getFlatNumber());
            //    jsonData.put("GpsLatitude", 54.222);
            //    jsonData.put("GpsLongitude", 11.333);
        } catch (JSONException jex) {
        }
        _restClientService.SetToken(UserLog.token);
        return _restClientService.PutPost(Tickets + "/" + id, jsonData.toString());
    }

    public int PinOrder(int id, Order order) {
        //13.	Edycja zgłoszenia
        // Typ: put
        // Url: http://s384027.iis.wmi.amu.edu.pl/api/Tickets/10?token=1234


        /* TO DOSTANĘ ZAPISUJE DO OBIEKTU I NASTĘPNIE CZĘSC DANYCH MOGĘ ZMIENIC
        "Title": "ticket4",
            "Description": "desc4",
            "CreatorId": 10,
            "Creator": null,
            "ExecutorId": 10,
            "CreateDate": "2015-04-19T16:36:56.95",
            "TeamId": 5,
            "Status": "C ",
            "CustomerId": 1
*/
        JSONObject jsonData = new JSONObject();
        try {
            // from class Order
            jsonData.put("CreatorId", order.getCreatorId());
            jsonData.put("Description", order.getDescription());
            jsonData.put("CustomerId", order.getCustomerId());
            jsonData.put("Status", order.getStatus());
            jsonData.put("Title", order.getTitle());
            jsonData.put("ExecutorId", order.getExecutorId()); // tutaj na stałę
            jsonData.put("AssignToTicket", true);


            // ?????????
            //    jsonData.put("HomeNo", client.getHomeNumber());
            //    jsonData.put("FlatNo", client.getFlatNumber());
            //    jsonData.put("GpsLatitude", 54.222);
            //    jsonData.put("GpsLongitude", 11.333);
        } catch (JSONException jex) {
        }
        _restClientService.SetToken(UserLog.token);
        return _restClientService.PutPost(Tickets + "/" + id, jsonData.toString());
    }





    public int AddCoordinate(Coordinate coordinate)
        {
          //  Url: http://s384027.iis.wmi.amu.edu.pl/api/Coordinates?token=1234
            JSONObject jsonData = new JSONObject();
            try {
                jsonData.put("Latitude", coordinate.getLatitude());
                jsonData.put("Longitude", coordinate.getLongitude());
            }
            catch (JSONException jex)
            {}
            _restClientService.SetToken(UserLog.token);
            return _restClientService.SendPost(Coordinates, jsonData.toString());
        }


    public int GetNotifications()
    {
        //  Url: http://s384027.iis.wmi.amu.edu.pl/api/Notifications?token=1234

        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Notifications);
    }

    public int DeleteNotification(int id)
    {
        // Url: http://s384027.iis.wmi.amu.edu.pl/api/Notifications/1?token=1234
        _restClientService.SetToken(UserLog.token);
        return _restClientService.DeletePost(Notifications+"/"+id);
    }


    public int GetMyCard()
    {
        //  Url: http://s384027.iis.wmi.amu.edu.pl/api/Cards/1?token=1234
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Cards+"/"+"0");
    }

    public int GetCard(int workerId)
    {
        //  Url: http://s384027.iis.wmi.amu.edu.pl/api/Cards/1?token=1234
        _restClientService.SetToken(UserLog.token);
        return _restClientService.GetPost(Cards+"/"+workerId);
    }















    public int SendLocation(Coordinate cor)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("longitude", cor.getLongitude());
            jsonData.put("latitude", cor.getLatitude());
        }
        catch (JSONException jex)
        {}
        _restClientService.SetToken(UserLog.token);
        return _restClientService.SendPost(CoordinateResource, jsonData.toString());
    }
}



