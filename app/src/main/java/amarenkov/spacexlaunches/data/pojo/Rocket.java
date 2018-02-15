package amarenkov.spacexlaunches.data.pojo;

import org.json.JSONObject;

public class Rocket {
    private String id;
    private String name;

    Rocket(JSONObject rocket) {
        this.id = rocket.optString("rocket_id");
        this.name = rocket.optString("rocket_name");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
