package tech.mlsn.temandonor.fragment.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class AddTestimoniFragment extends Fragment {
    TextInputLayout lytTesti;
    TextInputEditText etTesti;
    Button btnSave;


    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_testimoni, container, false);
        initialization(view);
        clickListener();
        return view;
    }

    private void initialization(View view){
        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        lytTesti = view.findViewById(R.id.lytTestimoni);
        etTesti = view.findViewById(R.id.etTestimoni);
        btnSave = view.findViewById(R.id.btnSave);

    }

    private void clickListener(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    addTesti();
                }
            }
        });
    }

    private void addTesti(){
        Call<BaseResponse> postUpdateUser= apiInterface.addComment(
               spManager.getSpName(),
               etTesti.getText().toString(),
               ""
        );

        postUpdateUser.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess()==1) {
                    snackbar.snackSuccess("Berhasil");
                    Tools.removeAllFragment(getActivity(), new AccountFragment(),"profile");
                } else{
                    snackbar.snackError("Gagal");
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
        if (etTesti.getText().toString().isEmpty()){
            lytTesti.setError("Harus Diisi");
            valid = false;
        }

        return valid;
    }
}