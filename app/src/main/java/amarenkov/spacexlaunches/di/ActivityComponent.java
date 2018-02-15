package amarenkov.spacexlaunches.di;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;

import amarenkov.spacexlaunches.di.annotations.ActivityContext;
import amarenkov.spacexlaunches.di.annotations.PerActivity;
import amarenkov.spacexlaunches.views.LaunchesListActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, UtilsModule.class})
public interface ActivityComponent {
    void inject(LaunchesListActivity launchesListActivity);

    @ActivityContext Context getContext();

    ArrayAdapter<String> getLaunchYearsStringAdapter();

    LinearLayoutManager getLinearLayoutManager();

    DividerItemDecoration getDividerItemDecoration();

    Intent getEmptyIntent();
}
