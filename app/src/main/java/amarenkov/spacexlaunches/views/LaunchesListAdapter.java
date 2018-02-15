package amarenkov.spacexlaunches.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import amarenkov.spacexlaunches.R;
import amarenkov.spacexlaunches.data.network.ImageLoaderCallback;
import amarenkov.spacexlaunches.data.pojo.Launch;
import amarenkov.spacexlaunches.di.annotations.ActivityContext;
import amarenkov.spacexlaunches.viewmodels.LaunchesListViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchesListAdapter extends RecyclerView.Adapter<LaunchesListAdapter.LaunchesViewHolder>
        implements ImageLoaderCallback {

    private LaunchesListViewModel viewModel;
    private ArrayList<Launch> launches;
    private Handler uiThread;
    private ListItemClickListener clickListener;

    @Inject
    LaunchesListAdapter(@ActivityContext Context context) {
        this.launches = new ArrayList<>();
        this.uiThread = new Handler(Looper.getMainLooper());
        this.clickListener = (ListItemClickListener) context;
    }

    void setLaunches(ArrayList<Launch> launches) {
        this.launches = launches;
        notifyDataSetChanged();
    }

    void setViewModel(LaunchesListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onSuccess(Bitmap image, final int position) {
        viewModel.putThumbnail(position, image);
        uiThread.post(new Runnable() {
            @Override
            public void run() {
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public void onFailure(Exception ex) {
        viewModel.setAlert(ex.getMessage());
    }

    @Override
    public int getItemCount() {
        return launches.size();
    }

    @Override
    public LaunchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LaunchesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_launch, parent, false));
    }

    @Override
    public void onBindViewHolder(final LaunchesViewHolder holder, int position) {
        Bitmap thumbnail = viewModel.getThumbnail(position);
        if (thumbnail == null) {
            viewModel.loadThumbnail(launches.get(position).getLinks().getImageUrl(), position, this);
            holder.progbar.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.GONE);
        } else {
            holder.img.setImageBitmap(thumbnail);
            holder.progbar.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
        }
        holder.tvDate.setText(launches.get(position).getLaunchDateString());
        holder.tvRocket.setText(launches.get(position).getRocket().getName());
        holder.tvDetails.setText(launches.get(position).getDetails());
    }


    class LaunchesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvRocket)
        TextView tvRocket;
        @BindView(R.id.tvDetails)
        TextView tvDetails;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.progbar)
        ProgressBar progbar;

        LaunchesViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onListItemShortClick(launches.get(getAdapterPosition())
                            .getLinks().getArticleUrl());
                }
            });
        }
    }
}
