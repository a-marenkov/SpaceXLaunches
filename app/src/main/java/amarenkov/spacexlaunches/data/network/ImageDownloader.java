package amarenkov.spacexlaunches.data.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Inject;

import amarenkov.spacexlaunches.R;
import amarenkov.spacexlaunches.di.annotations.AppContext;

public class ImageDownloader {

    private Bitmap nophoto;
    private Context appContext;

    @Inject
    ImageDownloader(@AppContext Context context) {
        this.nophoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.nophoto);
        this.appContext = context;
    }

    public void loadImageAsync(final String imgUrl, final int position, final ImageLoaderCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = nophoto;
                InputStream inputStream = null;
                if (!hasInternetConnection()) {
                    callback.onFailure(new NetworkException("No internet connection"));
                } else try {
                    URL url = new URL(imgUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);
                } catch (Exception e) {
                    callback.onFailure(e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            callback.onFailure(e);
                        }
                    }
                }
                callback.onSuccess(bitmap, position);
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
}