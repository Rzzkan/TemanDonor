package tech.mlsn.temandonor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.BaseResponse;
import tech.mlsn.temandonor.response.LoginDataResponse;
import tech.mlsn.temandonor.response.LoginResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout lytName, lytEmail, lytPassword, lytConfirmPassword;
    TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    Button btnRegister;

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialization();
        clickListener();
    }

    private void initialization(){
        lytName = findViewById(R.id.lytName);
        lytEmail = findViewById(R.id.lytEmail);
        lytPassword = findViewById(R.id.lytPassword);
        lytConfirmPassword = findViewById(R.id.lytConfirmPassword);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        spManager = new SPManager(this);
        snackbar = new SnackbarHandler(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    private void clickListener(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    postRegister();
                }
            }
        });
    }

    private void postRegister(){
        Call<BaseResponse> postSingup = apiInterface.postRegister(
              etName.getText().toString(),
              etEmail.getText().toString(),
              etConfirmPassword.getText().toString()
        );

        postSingup.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess()==1) {
                    snackbar.snackSuccess("Register Success");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
                } else{
                    snackbar.snackError("Register Failed");
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                snackbar.snackInfo("Not Connected");
            }
        });
    }

    private Boolean validate(){
        Boolean valid = true;
        if (etName.getText().toString().isEmpty()){
            lytName.setError("Name Required");
            valid = false;
        }

        if (etPassword.getText().toString().isEmpty()){
            lytPassword.setError("Password Required");
            valid = false;
        }
        if (etConfirmPassword.getText().toString().isEmpty()){
            lytConfirmPassword.setError("Password Required");
            valid = false;
        }
        if (etEmail.getText().toString().isEmpty()){
            lytEmail.setError("Email Required");
            valid = false;
        }

        if (!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())){
            lytConfirmPassword.setError("Not Match");
            valid = false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            lytEmail.setError("Email Not Valid");
            valid = false;
        }

        return valid;
    }
}