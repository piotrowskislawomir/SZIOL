package sziolmobile;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Slawek on 2015-03-21.
 */

public class RestService {
    public final RestClientService _restClientService;
    public final String CoordinateResource = "coordinates";

    public RestService(RestClientService restClientService)
    {
        _restClientService = restClientService;
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



