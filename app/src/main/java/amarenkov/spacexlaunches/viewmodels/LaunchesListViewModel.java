package amarenkov.spacexlaunches.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.util.SparseArray;

import java.util.ArrayList;

import javax.inject.Inject;

import amarenkov.spacexlaunches.Utils;
import amarenkov.spacexlaunches.data.Repository;
import amarenkov.spacexlaunches.data.network.ImageLoaderCallback;
import amarenkov.spacexlaunches.data.network.SpaceXDataServiceCallback;
import amarenkov.spacexlaunches.data.pojo.Launch;

public class LaunchesListViewModel extends ViewModel
        implements SpaceXDataServiceCallback {

    private Repository repo;
    private String year;
    private MutableLiveData<ArrayList<Launch>> launches;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> alert;
    private SparseArray<Bitmap> cashedThumbnails;

    @Inject
    LaunchesListViewModel(Repository repository) {
        this.repo = repository;
        this.year = "";
        this.launches = new MutableLiveData<>();
        this.alert = new MutableLiveData<>();
        this.alert.setValue(Utils.NO_ALERT);
        this.isLoading = new MutableLiveData<>();
        this.isLoading.setValue(false);
        this.cashedThumbnails = new SparseArray<>();
    }

    private void loadData(String year) {
        isLoading.setValue(true);
        repo.loadLauncesData(year, this);
    }

    public LiveData<ArrayList<Launch>> getLaunchesList() {
        return launches;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getAlert() {
        return alert;
    }

    public void onAlertHasBeenSwown() {
        alert.setValue(Utils.NO_ALERT);
    }

    public void setYear(String newYear) {
        if(!year.equals(newYear)) loadData(newYear);
    }

    @Override
    public void onSpaceXDataServiceSuccess(ArrayList<Launch> response, String year) {
        this.year = year;
        isLoading.postValue(false);
        launches.postValue(response);
        cashedThumbnails.clear();
    }

    @Override
    public void onSpaceXDataServiceFailure(Exception error) {
        isLoading.postValue(false);
        alert.postValue(error.getMessage());
    }

    public Bitmap getThumbnail(int position) {
        return cashedThumbnails.get(position);
    }

    public void loadThumbnail(String imageUrl, int position, ImageLoaderCallback callback) {
        repo.loadThumbnail(imageUrl, position, callback);
    }

    public void putThumbnail(int position, Bitmap thumbnail) {
        cashedThumbnails.put(position, thumbnail);
    }

    public void setAlert(String alert) {
        this.alert.setValue(alert);
    }
}
