package tech.mlsn.temandonor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karan.churi.PermissionManager.PermissionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.LoginDataResponse;
import tech.mlsn.temandonor.response.LoginResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;


public class LoginActivity extends AppCompatActivity {
    TextInputLayout lytUsername, lytPassword;
    TextInputEditText etUsername, etPassword;
    Button btnLogin, btnRegister;

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;

    PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
        btnListener();
    }

    private void initialization(){
        spManager = new SPManager(this);
        snackbar = new SnackbarHandler(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        lytUsername = findViewById(R.id.lytUsername);
        lytPassword = findViewById(R.id.lytPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        permissionManager = new PermissionManager() {};
        permissionManager.checkAndRequestPermissions(this);

        if (spManager.getSpIsSigned()){
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    private void btnListener(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void postLogin(){
        Call<LoginResponse> postSingnin = apiInterface.postLogin(
                etUsername.getText().toString(),
                etPassword.getText().toString()
        );

        postSingnin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getSuccess()==1) {
                    LoginDataResponse currentUser = response.body().getData();
                    spManager.saveSPString(spManager.SP_ID, currentUser.getId());
                    spManager.saveSPString(spManager.SP_NAME, currentUser.getName());
                    spManager.saveSPString(spManager.SP_EMAIL, currentUser.getEmail());
                    spManager.saveSPBoolean(spManager.SP_IS_SIGNED, true);
                    snackbar.snackSuccess("Login Success");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                } else{
                    snackbar.snackError("Wrong Password");
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                snackbar.snackInfo("Not Connected");
            }
        });
    }
}