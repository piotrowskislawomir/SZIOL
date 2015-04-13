package sziolmobile;

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


    public RestService(RestClientService restClientService)
    {
        _restClientService = restClientService;
    }

    public void SendClientRegistry(User usr, int registryKey)
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
        _restClientService.SendPost(Users, jsonData.toString());
    }

    public void SendClientLogin(String login, String pass)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("UserName", login);
            jsonData.put("Password", pass);
        }
        catch (JSONException jex)
        {}
        _restClientService.PutPost(Users, jsonData.toString());
    }

    public void GetClientById(int id)
    {
        _restClientService.SetToken(UserLog.token);
        _restClientService.GetPost(Customers+"/"+id);
    }

    public void GetAllClients() // co jesli inne gruby zawodowe ???
    {
        _restClientService.SetToken(UserLog.token);
        _restClientService.GetPost(Customers);
    }

    public void AddNewClient(Client client)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("FirstName", client.getFirstName());
            jsonData.put("LastName", client.getLastName());
            jsonData.put("Address", client.getAddress());
        }
        catch (JSONException jex)
        {}
        _restClientService.SetToken(UserLog.token);
        _restClientService.SendPost(Customers, jsonData.toString());
    }

    public void AddNewOrder(Order order)
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

    public void AddOrder(Order order)
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

    public void GetAllOrders() // co jesli inne gruby zawodowe ???
    {
        _restClientService.SetToken(UserLog.token);
        _restClientService.GetPost(Tickets);
    }

    public void GetOrder(int id) // co jesli inne gruby zawodowe ???
    {
        _restClientService.SetToken(UserLog.token);
        _restClientService.GetPost(Tickets+"/"+id);
    }




    public void SendLocation(Integer workerId, String longitude, String latitude)
    {
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("workerId", workerId);
            jsonData.put("longitude", longitude);
            jsonData.put("latitude", latitude);
        }
        catch (JSONException jex)
        {}
        _restClientService.SendPost(CoordinateResource, jsonData.toString());
    }
}



