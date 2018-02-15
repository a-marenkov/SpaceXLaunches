package amarenkov.spacexlaunches.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import amarenkov.spacexlaunches.data.Repository;

@Singleton
public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Repository repo;

    @Inject
    MyViewModelFactory(Repository repo) {
        this.repo = repo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LaunchesListViewModel.class))
            return (T) new LaunchesListViewModel(repo);
        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
