package amarenkov.spacexlaunches.data.pojo;

import org.json.JSONObject;

public class Links {
    private String imageUrl;
    private String articleUrl;
    private String videoUrl;

    Links(JSONObject links) {
        this.imageUrl = links.optString("mission_patch");
        this.articleUrl = links.optString("article_link");
        this.videoUrl = links.optString("video_link");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
