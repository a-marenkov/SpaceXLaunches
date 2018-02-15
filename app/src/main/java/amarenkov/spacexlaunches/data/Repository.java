package amarenkov.spacexlaunches.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import amarenkov.spacexlaunches.data.network.ImageDownloader;
import amarenkov.spacexlaunches.data.network.ImageLoaderCallback;
import amarenkov.spacexlaunches.data.network.SpaceXDataService;
import amarenkov.spacexlaunches.data.network.SpaceXDataServiceCallback;

@Singleton
public class Repository {

    private SpaceXDataService dataService;
    private ImageDownloader imageDownloader;

    @Inject
    Repository(SpaceXDataService dataService, ImageDownloader imageDownloader) {
        this.dataService = dataService;
        this.imageDownloader = imageDownloader;
    }

    public void loadLauncesData(String year, SpaceXDataServiceCallback callback) {
        dataService.loadLauncesDataAsync(year, callback);
    }

    public void loadThumbnail(String imageUrl, int position, ImageLoaderCallback callback) {
        imageDownloader.loadImageAsync(imageUrl, position, callback);
    }
}
