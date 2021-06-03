package tech.mlsn.temandonor.fragment.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;
import tech.mlsn.temandonor.tools.Tools;

public class UpdateAccountFragment extends Fragment {
    TextInputLayout lytName;
    TextInputEditText etName;
    Button btnSave;


    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        initializtion(view);
        clickListener();
        return view;
    }

    private void initializtion(View view){
        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        lytName = view.findViewById(R.id.lytName);
        etName = view.findViewById(R.id.etName);
        btnSave = view.findViewById(R.id.btnSave);
        etName.setText(spManager.getSpName());

    }

    private void clickListener(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    updateAccount();
                }
            }
        });
    }

    private void updateAccount(){
        Call<BaseResponse> postUpdateUser= apiInterface.postUpdateUser(
                spManager.getSpId(),
                etName.getText().toString()
        );

        postUpdateUser.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess()==1) {
                    spManager.saveSPString(SPManager.SP_NAME,etName.getText().toString());
                    snackbar.snackSuccess("Success");
                    Tools.removeAllFragment(getActivity(), new AccountFragment(),"profile");
                } else{
                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });
    }

    private Boolean validate(){
        Boolean valid = true;
        if (etName.getText().toString().isEmpty()){
            lytName.setError("Name Required");
            valid = false;
        }

        return valid;
    }
}