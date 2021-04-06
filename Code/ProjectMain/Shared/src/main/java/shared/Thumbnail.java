package shared;
import java.io.Serializable;
import java.util.UUID;

public class Thumbnail implements Serializable {

    private String title;
    private String description;

    private byte[] image;
    private UUID guideUUID;

    public Thumbnail(UUID guideUUID){
        this.guideUUID = guideUUID;
    }

    //<editor-fold desc="Getters & setters">
    public UUID getGuideUUID() {
        return guideUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    //</editor-fold>
}
