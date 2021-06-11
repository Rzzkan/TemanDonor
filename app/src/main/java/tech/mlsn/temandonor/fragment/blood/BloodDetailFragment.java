package tech.mlsn.temandonor.fragment.blood;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.GetVolunteerResponse;
import tech.mlsn.temandonor.response.VolunteersDataResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;
import tech.mlsn.temandonor.tools.Tools;

public class BloodDetailFragment extends Fragment {
    TextView tvName, tvCity, tvGender, tvAge, tvWeight, tvBlood, tvRhesus, tvEver, tvCovid;
    Button btnCall, btnWhatsapp;
    CardView cardEver, cardCovid;

    String id="";
    String phone ="", wa="";

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_detail, container, false);
        initialization(view);
        getData();
        btnListener();
        return view;
    }

    private void initialization(View view){
        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        tvName = view.findViewById(R.id.tvName);
        tvCity = view.findViewById(R.id.tvCity);
        tvGender = view.findViewById(R.id.tvGender);
        tvAge = view.findViewById(R.id.tvAge);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvBlood = view.findViewById(R.id.tvBlood);
        tvRhesus = view.findViewById(R.id.tvRhesus);
        tvEver   = view.findViewById(R.id.tvEver);
        tvCovid = view.findViewById(R.id.tvCovid);
        btnCall = view.findViewById(R.id.btnCall);
        btnWhatsapp = view.findViewById(R.id.btnWhatsapp);
        cardEver = view.findViewById(R.id.cardEver);
        cardCovid = view.findViewById(R.id.cardCovid);

    }

    private void getData(){
        Bundle data = this.getArguments();
        id = data.getString("id","0");
        getVolunteer(id);
    }

    private void btnListener(){
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
            }
        });

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent sendIntent =new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"");
                    sendIntent.putExtra("jid", wa +"@s.whatsapp.net");
                    sendIntent.setPackage("com.whatsapp");
                    getActivity().startActivity(sendIntent);
                }
                catch(Exception e)
                {
                    Toast.makeText(getContext(),"Whatsapp Tidak Ditemukan",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getVolunteer(String id){
        Call<GetVolunteerResponse> getVolunteer = apiInterface.getVolunteer(
                id
        );
        getVolunteer.enqueue(new Callback<GetVolunteerResponse>() {
            @Override
            public void onResponse(Call<GetVolunteerResponse> call, Response<GetVolunteerResponse> response) {
                if (response.body().getSuccess()==1) {
//                    snackbar.snackSuccess("Success");
                    VolunteersDataResponse data = response.body().getData();
                    tvName.setText(": "+data.getName());
                    tvCity.setText(": "+data.getCity());
                    tvGender.setText(": "+data.getGender());
                    tvAge.setText(": "+data.getAge()+" Tahun");
                    tvWeight.setText(": "+data.getCity()+" Kg");
                    tvBlood.setText(": "+data.getBlood());
                    tvRhesus.setText(": "+data.getRhesus());

                    if (Integer.valueOf(data.getTotalDonor())>0){
                        cardEver.setVisibility(View.VISIBLE);
                        tvEver.setText("Pernah "+ data.getTotalDonor()+" kali donor, terakhir kali pada "+ Tools.dateParser(data.getLastDonor()));
                    }

                    if (data.getStatusCovid().equalsIgnoreCase("ya")){
                        cardCovid.setVisibility(View.VISIBLE);
                        tvCovid.setText("Pernah terkena Covid-19 dengan gejala "+data.getSymptomps()+" dan dinyatakan sembuh pada "+Tools.dateParser(data.getRecoveryDate()));
                    }

                    phone  = data.getPhone();
                    wa = data.getPhone().toString().replaceFirst("0","62");
                } else{
//                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<GetVolunteerResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });

    }
}