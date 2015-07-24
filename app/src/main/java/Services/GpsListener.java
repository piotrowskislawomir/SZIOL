package Services;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.slawek.sziolmobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import Models.CoordinateModel;

/**
 * Created by Michał on 2015-05-09.
 */
public class GpsListener implements LocationListener {

    Context context;
    String token;
    String login;
    String password;
    Boolean localizationEnable;

    private SharedPropertiesManager sharedPropertiesManager;
    private Resources resources;
    RestClientService restClientService;
    RestService restService;

    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public Location previousBestLocation = null;

    public GpsListener(Context context)
    {
        this.context= context;
        resources = context.getResources();
        sharedPropertiesManager = new SharedPropertiesManager(context);

        Initialize();
    }

    private void Initialize()
    {
        restClientService  = new RestClientService("http://s384027.iis.wmi.amu.edu.pl/api/");
        restService = new RestService(restClientService);
    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            if (isBetterLocation(location, previousBestLocation)) {
                CoordinateModel coordinate = new CoordinateModel(location.getLatitude(), location.getLongitude());

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                localizationEnable = Boolean.parseBoolean(sharedPropertiesManager.GetValue(resources.getString(R.string.shared_localization_enable), "false"));
                token = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_token), null);

                if (localizationEnable && token != null) {
                    int status = restService.SendLocation(coordinate);

                    if (status != 201) {
                        Login();
                        restService.SendLocation(coordinate);
                    }
                }
                previousBestLocation = location;
            }
        }
        catch (Exception ex)
        {
            ExceptionLoggerService exceptionLogger = new ExceptionLoggerService();
            exceptionLogger.writefile("sziolgps.txt", ex.getMessage());
        }
    }

    private boolean Login()
    {
        login = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_login), null);
        password = sharedPropertiesManager.GetValue(resources.getString(R.string.shared_password), null);

        if(login != null && password != null)
        {
            int status = restService.SendClientLogin(login, password);

            try
            {
                if(status == 200)
                {
                    JSONObject jsonObj = new JSONObject(RestClientService.resp);
                    String result = jsonObj.get("Result").toString();
                    String token = jsonObj.get("Token").toString();

                    if(result == "true" && token != null)
                    {
                        RestClientService.SetToken(token);
                        return  true;
                    }
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
