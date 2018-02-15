package amarenkov.spacexlaunches.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import amarenkov.spacexlaunches.SpacexlaunchesApp;
import amarenkov.spacexlaunches.di.ActivityComponent;
import amarenkov.spacexlaunches.di.ActivityModule;
import amarenkov.spacexlaunches.di.DaggerActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((SpacexlaunchesApp) getApplication()).getAppComponent())
                .build();
    }

    public ActivityComponent getComponent() {
        return component;
    }
}
