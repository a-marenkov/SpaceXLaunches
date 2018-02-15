package amarenkov.spacexlaunches.data.pojo;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Launch {
    private Long launchDateUnix;
    private Rocket rocket;
    private Links links;
    private String details;

    public Launch(JSONObject launch) {
        this.launchDateUnix = launch.optLong("launch_date_unix");
        this.rocket = new Rocket(launch.optJSONObject("rocket"));
        this.links = new Links(launch.optJSONObject("links"));
        String details = launch.optString("details");
        if(details.equals("null")){
            this.details = "";
        } else {
            this.details = details;
        }
    }

    public String getLaunchDateString() {
        String date = new SimpleDateFormat("E dd-MM-yy", Locale.getDefault())
                .format(new Date(launchDateUnix*1000L));
        return date.substring(0, 1).toUpperCase() + date.substring(1);
    }

    public Long getLaunchDateUnix() {
        return launchDateUnix;
    }

    public void setLaunchDateUnix(Long launchDateUnix) {
        this.launchDateUnix = launchDateUnix;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
