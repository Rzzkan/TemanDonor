package tech.mlsn.temandonor.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tech.mlsn.temandonor.response.BaseResponse;
import tech.mlsn.temandonor.response.BloodsResponse;
import tech.mlsn.temandonor.response.CitiesResponse;
import tech.mlsn.temandonor.response.CommentsResponse;
import tech.mlsn.temandonor.response.EventsResponse;
import tech.mlsn.temandonor.response.GetNewsResponse;
import tech.mlsn.temandonor.response.GetVolunteerResponse;
import tech.mlsn.temandonor.response.LoginResponse;
import tech.mlsn.temandonor.response.NewsResponse;
import tech.mlsn.temandonor.response.VolunteersResponse;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> postLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<BaseResponse> postRegister(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("updateUser.php")
    Call<BaseResponse> postUpdateUser(
            @Field("id") String id,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<BaseResponse> postUpdatePassword(
            @Field("id") String id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );

    @GET("allNews.php")
    Call<NewsResponse> allNews();

    @GET("allCities.php")
    Call<CitiesResponse> allCities();

    @GET("allComments.php")
    Call<CommentsResponse> allComments();

    @GET("allVolunteers.php")
    Call<VolunteersResponse> allVolunteers();

    @GET("allEvents.php")
    Call<EventsResponse> allEvents();

    @GET("allBloods.php")
    Call<BloodsResponse> allBloods();

    @FormUrlEncoded
    @POST("addComment.php")
    Call<BaseResponse> addComment(
            @Field("username") String username,
            @Field("comment") String comment,
            @Field("comment_date") String comment_date
    );

    @FormUrlEncoded
    @POST("addVolunteers.php")
    Call<BaseResponse> addVolunteers(
            @Field("name") String name,
            @Field("bloodtype_id") String bloodtype_id,
            @Field("rhesus") String rhesus,
            @Field("city_id") String city_id,
            @Field("gender") String gender,
            @Field("age") String age,
            @Field("weight") String weight,
            @Field("phone") String phone,
            @Field("total_donor") String total_donor,
            @Field("last_donor") String last_donor,
            @Field("status_covid") String status_covid,
            @Field("symptoms") String symptoms,
            @Field("recovery_date") String recovery_date,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("getNews.php")
    Call<GetNewsResponse> getNews(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("getVolunteer.php")
    Call<GetVolunteerResponse> getVolunteer(
            @Field("id") String id
    );


}
