package tech.mlsn.temandonor.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tech.mlsn.temandonor.response.BaseResponse;
import tech.mlsn.temandonor.response.LoginResponse;


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

//    @GET("allUsers.php")
//    Call<AllUsersResponse>getAllUsers();
//
//
//    @FormUrlEncoded
//    @POST("userDashboard.php")
//    Call<DashboardResponse> postDashboard(
//            @Field("id_user") String id,
//            @Field("date") String date,
//            @Field("number") String number
//    );
//
//    @FormUrlEncoded
//    @POST("genNumber.php")
//    Call<GenerateNumberResponse> postGenNumber(
//            @Field("date") String date
//    );
//
//    @FormUrlEncoded
//    @POST("addQueue.php")
//    Call<BaseResponse> postAddQueue(
//            @Field("id_user") String id,
//            @Field("date") String date,
//            @Field("number") String number,
//            @Field("status") String status
//    );
//
//    @FormUrlEncoded
//    @POST("updatePw.php")
//    Call<BaseResponse> postUpdatePw(
//            @Field("id_user") String id,
//            @Field("password") String password
//    );
//
//
//    @FormUrlEncoded
//    @POST("updateUser.php")
//    Call<BaseResponse> postUpdateUser(
//            @Field("id_user") String id,
//            @Field("name") String name,
//            @Field("phone") String phone
//    );
//
//    @FormUrlEncoded
//    @POST("allQueueNow.php")
//    Call<QueuesResponse> getQueue(
//            @Field("date") String date
//    );
//
//    @FormUrlEncoded
//    @POST("updateStatus.php")
//    Call<BaseResponse> setStatus(
//            @Field("id_queue") String id_queue,
//            @Field("status") String status
//    );
//
//    @FormUrlEncoded
//    @POST("updateMax.php")
//    Call<BaseResponse> setMax(
//            @Field("max") String max
//    );
//
//    @FormUrlEncoded
//    @POST("adminDashboard.php")
//    Call<AdminDashboardResponse> postAdminDashboard(
//            @Field("date") String date
//    );


}
