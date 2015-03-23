package sziolmobile;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Slawek on 2015-03-21.
 */

public class RestClientService {
    HttpClient client = new DefaultHttpClient();
    private final String _serviceUrl;

    public RestClientService(String serviceUrl)
    {
        _serviceUrl = serviceUrl;
    }

    public int SendPost(String resource,String jsonData)
    {
        try {
            HttpPost request = new HttpPost(_serviceUrl + resource);
            request.setHeader("Content-type", "application/json");
            request.setEntity(new ByteArrayEntity(jsonData.getBytes("UTF8")));
            HttpResponse response = client.execute(request);
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
}

