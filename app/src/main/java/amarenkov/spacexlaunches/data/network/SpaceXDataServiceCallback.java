package amarenkov.spacexlaunches.data.network;

import java.util.ArrayList;

import amarenkov.spacexlaunches.data.pojo.Launch;

public interface SpaceXDataServiceCallback {
    void onSpaceXDataServiceSuccess(ArrayList<Launch> launches, String year);

    void onSpaceXDataServiceFailure(Exception error);
}