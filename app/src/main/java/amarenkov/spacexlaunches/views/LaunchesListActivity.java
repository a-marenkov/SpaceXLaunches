package amarenkov.spacexlaunches.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import amarenkov.spacexlaunches.R;
import amarenkov.spacexlaunches.Utils;
import amarenkov.spacexlaunches.base.BaseActivity;
import amarenkov.spacexlaunches.data.pojo.Launch;
import amarenkov.spacexlaunches.viewmodels.LaunchesListViewModel;
import amarenkov.spacexlaunches.viewmodels.MyViewModelFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchesListActivity extends BaseActivity implements ListItemClickListener {

    @BindView(R.id.rvLaunches)
    RecyclerView rvLaunches;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.progbar)
    ProgressBar progbar;
    @BindView(R.id.tvEmptyList)
    TextView tvEmptyList;

    @Inject
    MyViewModelFactory factory;
    @Inject
    LaunchesListAdapter adapter;

    private LaunchesListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launches_list);
        ButterKnife.bind(this);
        getComponent().inject(this);
        viewModel = ViewModelProviders.of(this, factory)
                .get(LaunchesListViewModel.class);
        setToolbar();
        setList();
        setObservers();
    }

    private void setToolbar() {
        spinner.setAdapter(getComponent().getLaunchYearsStringAdapter());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setYear((String) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setList() {
        adapter.setViewModel(viewModel);
        rvLaunches.setLayoutManager(getComponent().getLinearLayoutManager());
        rvLaunches.addItemDecoration(getComponent().getDividerItemDecoration());
        rvLaunches.setAdapter(adapter);
    }

    private void setObservers() {
        viewModel.getLaunchesList().observe(this, new Observer<ArrayList<Launch>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Launch> launches) {
                if (launches == null) return;
                if (launches.isEmpty()) {
                    rvLaunches.setVisibility(View.GONE);
                    tvEmptyList.setVisibility(View.VISIBLE);
                    tvEmptyList.setText(R.string.no_launches);
                    tvEmptyList.append(" ");
                    tvEmptyList.append((String) spinner.getSelectedItem());
                } else {
                    adapter.setLaunches(launches);
                    tvEmptyList.setVisibility(View.GONE);
                    rvLaunches.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading == null) return;
                if (isLoading) {
                    progbar.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                } else {
                    progbar.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.getAlert().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String alert) {
                if (alert != null && !alert.equals(Utils.NO_ALERT)) {
                    showSnackbarShortly(alert);
                    viewModel.onAlertHasBeenSwown();
                }
            }
        });
    }

    @Override
    public void onListItemShortClick(String articleUrl) {
        if (articleUrl.equals("null")) {
            showSnackbarShortly(getString(R.string.article_not_found));
        } else {
            Intent intent = getComponent().getEmptyIntent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(articleUrl));
            startActivity(intent);
        }
    }

    private void showSnackbarShortly(String message) {
        Snackbar.make(rvLaunches, message, Snackbar.LENGTH_SHORT).show();
    }
}
