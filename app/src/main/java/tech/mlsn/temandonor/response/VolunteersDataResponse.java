package tech.mlsn.temandonor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rzzkan on 10/06/2021.
 */
public class VolunteersDataResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("blood")
    @Expose
    private String blood;
    @SerializedName("rhesus")
    @Expose
    private String rhesus;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("total_donor")
    @Expose
    private String totalDonor;
    @SerializedName("last_donor")
    @Expose
    private String lastDonor;
    @SerializedName("status_covid")
    @Expose
    private String statusCovid;
    @SerializedName("symptomps")
    @Expose
    private String symptomps;
    @SerializedName("recovery_date")
    @Expose
    private String recoveryDate;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public VolunteersDataResponse(String id, String name, String blood, String rhesus, String city, String gender, String age, String weight, String phone, String totalDonor, String lastDonor, String statusCovid, String symptomps, String recoveryDate, String userId) {
        this.id = id;
        this.name = name;
        this.blood = blood;
        this.rhesus = rhesus;
        this.city = city;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.phone = phone;
        this.totalDonor = totalDonor;
        this.lastDonor = lastDonor;
        this.statusCovid = statusCovid;
        this.symptomps = symptomps;
        this.recoveryDate = recoveryDate;
        this.userId = userId;
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

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getRhesus() {
        return rhesus;
    }

    public void setRhesus(String rhesus) {
        this.rhesus = rhesus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotalDonor() {
        return totalDonor;
    }

    public void setTotalDonor(String totalDonor) {
        this.totalDonor = totalDonor;
    }

    public String getLastDonor() {
        return lastDonor;
    }

    public void setLastDonor(String lastDonor) {
        this.lastDonor = lastDonor;
    }

    public String getStatusCovid() {
        return statusCovid;
    }

    public void setStatusCovid(String statusCovid) {
        this.statusCovid = statusCovid;
    }

    public String getSymptomps() {
        return symptomps;
    }

    public void setSymptomps(String symptomps) {
        this.symptomps = symptomps;
    }

    public String getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(String recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
