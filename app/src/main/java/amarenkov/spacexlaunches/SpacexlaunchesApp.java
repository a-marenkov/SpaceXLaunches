package amarenkov.spacexlaunches;

import android.app.Application;

import amarenkov.spacexlaunches.di.AppComponent;
import amarenkov.spacexlaunches.di.AppModule;
import amarenkov.spacexlaunches.di.DaggerAppComponent;

public class SpacexlaunchesApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
