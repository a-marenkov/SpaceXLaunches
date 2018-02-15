package amarenkov.spacexlaunches.di;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ArrayAdapter;

import java.util.Calendar;

import amarenkov.spacexlaunches.R;
import amarenkov.spacexlaunches.Utils;
import amarenkov.spacexlaunches.di.annotations.ActivityContext;
import dagger.Module;
import dagger.Provides;

@Module
class UtilsModule {

    @Provides
    ArrayAdapter<String> getLaunchYearsStringAdapter(@ActivityContext Context context) {
        int tempYear = Calendar.getInstance().get(Calendar.YEAR);
        int firstYear = Utils.YEAR_OF_FIRST_FLIGHT;
        String[] years = new String[tempYear - firstYear + 1];
        for (int i = 0; tempYear >= firstYear; i++) {
            years[i] = String.valueOf(tempYear);
            tempYear--;
        }
        ArrayAdapter<String> launchYearsAdapter = new ArrayAdapter<>(context,
                R.layout.custom_spinner_item, years);
        launchYearsAdapter.setDropDownViewResource(R.layout.custom_list_item);
        return launchYearsAdapter;
    }

    @Provides
    LinearLayoutManager getLinearLayoutManager(@ActivityContext Context context){
        return new LinearLayoutManager(context);
    }

    @Provides
    DividerItemDecoration getDividerItemDecoration(@ActivityContext Context context,
                                               LinearLayoutManager manager){
        return new DividerItemDecoration(context, manager.getOrientation());
    }

    @Provides
    Intent getEmptyIntent() {
        return new Intent();
    }
}

