package amarenkov.spacexlaunches.data.network;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.inject.Inject;

import amarenkov.spacexlaunches.data.pojo.Launch;
import amarenkov.spacexlaunches.di.annotations.AppContext;

public class SpaceXDataService {

    private static String launchesBaseUrl = "https://api.spacexdata.com/v2/launches?launch_year=";
    private Context appContext;

    @Inject
    SpaceXDataService(@AppContext Context context) {
        this.appContext = context;
    }

    public void loadLauncesDataAsync(final String year, final SpaceXDataServiceCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!hasInternetConnection()){
                    callback.onSpaceXDataServiceFailure(new NetworkException("No internet connection"));
                } else try {
                    URL url = new URL(launchesBaseUrl + year);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    ArrayList<Launch> launches = populate(result.toString(), callback);
                    callback.onSpaceXDataServiceSuccess(launches, year);
                } catch (IOException e) {
                    callback.onSpaceXDataServiceFailure(e);
                }
            }
        }).start();
    }

    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null
                && connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private ArrayList<Launch> populate(String json, SpaceXDataServiceCallback callback) {
        ArrayList<Launch> launches = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int index = 0; index < array.length(); index++) {
                launches.add(new Launch(array.getJSONObject(index)));
            }
        } catch (JSONException e) {
            callback.onSpaceXDataServiceFailure(e);
        }
        return launches;
    }
}
