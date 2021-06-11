package tech.mlsn.temandonor.model;

/**
 * Created by Rzzkan on 10/06/2021.
 */
public class SliderModel {
    private String image, title, desc;

    public SliderModel(String image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
