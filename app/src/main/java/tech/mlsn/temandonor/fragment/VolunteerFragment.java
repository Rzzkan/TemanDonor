package tech.mlsn.temandonor.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.BaseResponse;
import tech.mlsn.temandonor.response.CitiesResponse;
import tech.mlsn.temandonor.response.CityDataResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;
import tech.mlsn.temandonor.tools.Tools;

public class VolunteerFragment extends Fragment {
    TextInputLayout lytName, lytPhone,lytAge, lytWeight, lytLastDonor, lytRecovery, lytTotal;
    TextInputEditText etName, etPhone, etAge, etWeight, etLastDonor, etRecovery, etTotal;
    RadioGroup rgBlood, rgRhesus, rgGender, rgSymptoms;
    CheckBox cbEver, cbCovid;
    LinearLayout lytEver, lytCovid;
    Button btnLastDonor, btnRecovery, btnRegister;
    SmartMaterialSpinner spinCity;
    ArrayList<CityDataResponse> listCity;
    ArrayList<String> listCityName;

    String idCity="", gender="", blood="", rhesus="", symptoms="";
    String covid="Tidak", ever="Tidak";
    String total="0";

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer, container, false);
        initialization(view);
        getCity();
        radioListener();
        btnClickListener();
        checkBoxListener();
        spinnerListener();
        return view;
    }

    private  void  initialization(View view){
        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        lytName = view.findViewById(R.id.lytName);
        lytPhone = view.findViewById(R.id.lytPhone);
        lytAge = view.findViewById(R.id.lytAge);
        lytWeight = view.findViewById(R.id.lytWeight);
        lytLastDonor = view.findViewById(R.id.lytLastDonor);
        lytRecovery = view.findViewById(R.id.lytRecovery);
        lytTotal = view.findViewById(R.id.lytTotal);
        etName = view.findViewById(R.id.etName);
        etPhone = view.findViewById(R.id.etPhone);
        etAge = view.findViewById(R.id.etAge);
        etWeight = view.findViewById(R.id.etWeight);
        etLastDonor = view.findViewById(R.id.etLastDonor);
        etRecovery = view.findViewById(R.id.etRecovery);
        etTotal =view.findViewById(R.id.etTotal);
        rgBlood = view.findViewById(R.id.rgBlood);
        rgGender = view.findViewById(R.id.rgGender);
        rgRhesus = view.findViewById(R.id.rgRhesus);
        rgSymptoms = view.findViewById(R.id.rgSymptoms);
        cbEver = view.findViewById(R.id.cbEver);
        cbCovid = view.findViewById(R.id.cbCovid);
        lytEver = view.findViewById(R.id.lytEverDonor);
        lytCovid = view.findViewById(R.id.lytCovid);
        btnLastDonor = view.findViewById(R.id.btnDateDonor);
        btnRecovery = view.findViewById(R.id.btnDateRecovery);
        btnRegister = view.findViewById(R.id.btnRegister);
        spinCity = view.findViewById(R.id.spinCity);

        listCity = new ArrayList<>();
        listCityName = new ArrayList<>();
    }

    private void getCity(){
        Call<CitiesResponse> getCity = apiInterface.allCities();
        getCity.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                if (response.body().getSuccess()==1) {
//                    snackbar.snackSuccess("Success");
                    for (int i=0; i<response.body().getData().size();i++){
                        listCity.add(new CityDataResponse(
                           response.body().getData().get(i).getId(),
                           response.body().getData().get(i).getName()
                        ));
                        listCityName.add(new String(
                                response.body().getData().get(i).getName()
                        ));
                        spinCity.setItem(listCityName);
                    }
                } else{
//                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<CitiesResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });
    }

    private void spinnerListener(){
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCity = findIdCity(spinCity.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void btnClickListener(){
        btnLastDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        etLastDonor.setText(dateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        etRecovery.setText(dateFormatter.format(newDate.getTime()));
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    postVolunteer();
                }
            }
        });
    }

    private void radioListener(){
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbMale:
                        gender="Laki-Laki";
                        break;
                    case R.id.rbFemale:
                        gender="Perempuan";
                        break;
                }
            }
        });

        rgBlood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.a:
                        blood ="1";
                        break;
                    case R.id.b:
                        blood ="2";
                        break;
                    case R.id.ab:
                        blood ="3";
                        break;
                    case R.id.o:
                        blood ="4";
                        break;
                }
            }
        });

        rgRhesus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbPositive:
                        rhesus ="+";
                        break;
                    case R.id.rbNegative:
                        rhesus ="-";
                        break;
                }
            }
        });

        rgSymptoms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.high:
                        symptoms ="Berat";
                        break;
                    case R.id.medium:
                        symptoms ="Sedang";
                        break;
                    case R.id.low:
                        symptoms ="Ringan";
                        break;
                }
            }
        });
    }

    private void checkBoxListener(){
        cbEver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    lytEver.setVisibility(View.VISIBLE);
                }else   {
                    lytEver.setVisibility(View.GONE);
                }
            }
        });

        cbCovid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    lytCovid.setVisibility(View.VISIBLE);
                }else   {
                    lytCovid.setVisibility(View.GONE);
                }
            }
        });
    }

    private String findIdCity(String name){
        String id ="";
        for(CityDataResponse listA : listCity) {
            if (listA.getName().equalsIgnoreCase(name)) {
                id = listA.getId().toString();
            }
        }
        idCity = id;
        return id;
    }

    private Boolean validate(){
        Boolean valid = true;

        if (etName.getText().toString().isEmpty()){
            lytName.setError("Nama Tidak Boleh Kosong");
            valid = false;
        }

        if (etPhone.getText().toString().isEmpty()){
            lytPhone.setError("No HP Tidak Boleh Kosong");
            valid = false;
        }

        if (etWeight.getText().toString().isEmpty()){
            lytWeight.setError("Berat Tidak Boleh Kosong");
            valid = false;
        }

        if (etAge.getText().toString().isEmpty()){
            lytAge.setError("Umur Tidak Boleh Kosong");
            valid = false;
        }

        if (idCity.isEmpty()){
            snackbar.snackInfo("Silakan Pilih Kota");
            valid = false;
        }
        if (gender.isEmpty()){
            snackbar.snackInfo("Silakan Pilih Jenis Kelamin");
            valid = false;
        }
        if (blood.isEmpty()){
            snackbar.snackInfo("Silakan Pilih Golongan Darah");
            valid = false;
        }
        if (rhesus.isEmpty()){
            snackbar.snackInfo("Silakan Pilih Rhesis");
            valid=false;
        }

        if (!ever.equalsIgnoreCase("tidak")){
            if (etLastDonor.getText().toString().isEmpty()){
                lytLastDonor.setError("Isikan Tanggal Terakhir Donor");
                valid=false;
            }

            if (etTotal.getText().toString().isEmpty()){
                lytTotal.setError("Isikan Pernah Berapa Kali Donor");
                valid=false;
            }
        }

        if (!covid.equalsIgnoreCase("tidak")){
            if (symptoms.isEmpty()){
                snackbar.snackInfo("Isikan Gejala");
                valid=false;
            }

            if (etRecovery.getText().toString().isEmpty()){
                lytRecovery.setError("Isikan Tanggal Sembuh");
                valid=false;
            }

        }
        return valid;
    }

    private void postVolunteer(){
        if (!ever.equalsIgnoreCase("tidak")){
            total = etTotal.getText().toString();
        }
        Call<BaseResponse> addVolunteers = apiInterface.addVolunteers(
                etName.getText().toString(),
                blood,
                rhesus,
                idCity,
                gender,
                etAge.getText().toString(),
                etWeight.getText().toString(),
                etPhone.getText().toString(),
                total,
                etLastDonor.getText().toString(),
                covid,
                symptoms,
                etRecovery.getText().toString(),
                spManager.getSpId()
        );
        addVolunteers.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getSuccess()==1) {
                    snackbar.snackSuccess("Success");
                    Tools.removeAllFragment(getActivity(),new VolunteerFragment(),"volunteer");
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
}