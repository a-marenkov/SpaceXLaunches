package amarenkov.spacexlaunches.di;

import android.content.Context;

import javax.inject.Singleton;

import amarenkov.spacexlaunches.data.Repository;
import amarenkov.spacexlaunches.di.annotations.AppContext;
import amarenkov.spacexlaunches.viewmodels.MyViewModelFactory;
import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    @AppContext Context getAppContext();

    Repository getRepository();

    MyViewModelFactory getMyViewModelFactory();
}
