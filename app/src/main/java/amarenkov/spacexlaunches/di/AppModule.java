package amarenkov.spacexlaunches.di;

import android.app.Application;
import android.content.Context;

import amarenkov.spacexlaunches.di.annotations.AppContext;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    Application getApplication() {
        return app;
    }

    @Provides
    @AppContext
    Context getApplicationContext() {
        return app;
    }
}
