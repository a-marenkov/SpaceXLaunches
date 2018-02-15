package amarenkov.spacexlaunches.di;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import amarenkov.spacexlaunches.di.annotations.ActivityContext;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    AppCompatActivity getActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    Context getContext() {
        return activity;
    }
}
