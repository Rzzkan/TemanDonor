package tech.mlsn.temandonor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rzzkan on 10/06/2021.
 */
public class BloodDataResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("blood_type")
    @Expose
    private String bloodType;

    public BloodDataResponse(String id, String bloodType) {
        this.id = id;
        this.bloodType = bloodType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
