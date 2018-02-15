package amarenkov.spacexlaunches.data.network;

import android.graphics.Bitmap;

public interface ImageLoaderCallback {

    void onSuccess(Bitmap image, int position);

    void onFailure(Exception ex);

}
