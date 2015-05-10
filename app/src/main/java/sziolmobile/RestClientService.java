package sziolmobile;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Slawek on 2015-03-21.
 */

public class RestClientService {
    HttpClient client = new DefaultHttpClient();
   // JSONArray cl = new JSONArray();

    private final String _serviceUrl;
    private  String _token = "";

    // dodane
    public static String resp;

    public RestClientService(String serviceUrl)
    {
        _serviceUrl = serviceUrl;
    }

    public void SetToken(String token)
    {
        _token = "?token=" + token;
    }
// public int SendPost(String resource,String jsonData)

    public int PutPost(String resource,String jsonData)
    {
        try {
            HttpPut req = new HttpPut(_serviceUrl + resource + _token);
            req.setHeader("Content-type", "application/json");
            req.setEntity(new ByteArrayEntity(jsonData.getBytes("UTF8")));
            HttpResponse res = client.execute(req);
//
            resp = Userrequest(res);

            return res.getStatusLine().getStatusCode();
        }
        catch (UnsupportedEncodingException ueex)
        {}
        catch (ClientProtocolException cpex)
        {}
        catch (IOException ioex)
        {}

        return -1;
    }

    public int DeletePost(String resource)
    {
        try {
            HttpDelete req = new HttpDelete(_serviceUrl + resource + _token);
            req.setHeader("Content-type", "application/json");
          //  req.setEntity(new ByteArrayEntity(jsonData.getBytes("UTF8")));
            HttpResponse res = client.execute(req);
//
            resp = Userrequest(res);

            return res.getStatusLine().getStatusCode();
        }
        catch (UnsupportedEncodingException ueex)
        {}
        catch (ClientProtocolException cpex)
        {}
        catch (IOException ioex)
        {}

        return -1;
    }

    public int GetPost(String resource)
    {
        try {
            HttpGet req = new HttpGet(_serviceUrl + resource + _token);
            req.setHeader("Content-type", "application/json");
           // req.setEntity(new ByteArrayEntity(jsonData.getBytes("UTF8")));

            HttpResponse res = client.execute(req);
//
            resp = Userrequest(res);

            return res.getStatusLine().getStatusCode();
        }
        catch (UnsupportedEncodingException ueex)
        {}
        catch (ClientProtocolException cpex)
        {}
        catch (IOException ioex)
        {}

        return -1;


    }

    public int SendPost(String resource,String jsonData)
    {
        try {
            HttpPost request = new HttpPost(_serviceUrl + resource + _token);
            request.setHeader("Content-type", "application/json");
            request.setEntity(new ByteArrayEntity(jsonData.getBytes("UTF8")));
            HttpResponse response = client.execute(request);
            resp = Userrequest(response);
            return response.getStatusLine().getStatusCode();
        }
        catch (UnsupportedEncodingException ueex)
        {}
        catch (ClientProtocolException cpex)
        {}
        catch (IOException ioex)
        {}

        return -1;
    }

    public String Userrequest(HttpResponse response)
    {
        String result = "";
        try
        {

            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
            //updateData(result);
        }
        catch(Exception ex)
        {
            //responsetxt.setText(ex.getMessage());
        }
        return result;
    }
}

