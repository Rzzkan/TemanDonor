package tech.mlsn.temandonor.fragment.blood;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.adapter.NewsAdapter;
import tech.mlsn.temandonor.adapter.VolunteersAdapter;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.NewsDataResponse;
import tech.mlsn.temandonor.response.CitiesResponse;
import tech.mlsn.temandonor.response.VolunteersResponse;
import tech.mlsn.temandonor.response.VolunteersDataResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;
import tech.mlsn.temandonor.tools.Tools;

public class BloodFragment extends Fragment {
    SmartMaterialSpinner spinCity;
    RadioGroup rgBlood;
    RadioButton a, b, ab, o;
    RecyclerView rvBlood;
    TabLayout tabCovid;

    VolunteersAdapter adapter;
    ArrayList<VolunteersDataResponse> listVolunteer;
    ArrayList<VolunteersDataResponse> listVolunteerFiltered;
    ArrayList<String> listCity;

    String city="", blood="",covid="tidak";

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood, container, false);
        initialization(view);
        getCity();
        getVolunteer();
        spinnerListener();
        radioListener();
        tabListener();
        return view;
    }

    private void initialization(View view){
        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        spinCity = view.findViewById(R.id.spinCity);
        a = view.findViewById(R.id.a);
        b = view.findViewById(R.id.b);
        ab = view.findViewById(R.id.ab);
        o = view.findViewById(R.id.o);
        rgBlood = view.findViewById(R.id.rgBlood);
        tabCovid = view.findViewById(R.id.lytTab);

        rvBlood = view.findViewById(R.id.rvBlood);

        rvBlood.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBlood.setHasFixedSize(true);
        listVolunteer = new ArrayList<>();
        listVolunteerFiltered = new ArrayList<>();
        adapter = new VolunteersAdapter(getContext(), listVolunteerFiltered);
        rvBlood.setAdapter(adapter);
        listCity = new ArrayList<>();

    }

    private void getCity(){
        Call<CitiesResponse> getCity = apiInterface.allCities();
        getCity.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(Call<CitiesResponse> call, Response<CitiesResponse> response) {
                if (response.body().getSuccess()==1) {
//                    snackbar.snackSuccess("Success");
                    listCity.add("Semua");
                    for (int i=0; i<response.body().getData().size();i++){
                        listCity.add(new String(
                                response.body().getData().get(i).getName()
                        ));
                        spinCity.setItem(listCity);
                        spinCity.setSelection(0);
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

    private void getVolunteer(){
        Call<VolunteersResponse> getNews = apiInterface.allVolunteers();
        getNews.enqueue(new Callback<VolunteersResponse>() {
            @Override
            public void onResponse(Call<VolunteersResponse> call, Response<VolunteersResponse> response) {
                if (response.body().getSuccess()==1) {
//                    snackbar.snackSuccess("Success");
                    for (int i=0; i<response.body().getData().size();i++){
                        VolunteersDataResponse data = response.body().getData().get(i);
                        listVolunteer.add(new VolunteersDataResponse(
                                data.getId(),
                                data.getName(),
                                data.getBlood(),
                                data.getRhesus(),
                                data.getCity(),
                                data.getGender(),
                                data.getAge(),
                                data.getWeight(),
                                data.getPhone(),
                                data.getTotalDonor(),
                                data.getLastDonor(),
                                data.getStatusCovid(),
                                data.getSymptomps(),
                                data.getRecoveryDate(),
                                data.getUserId()
                        ));
                    }
                    performFilter(city,blood,covid);
                } else{
//                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<VolunteersResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });
    }

    private void spinnerListener(){
        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinCity.getSelectedItem().toString().equalsIgnoreCase("semua")){
                    city = "";
                    performFilter(city,blood,covid);
                }else {
                    city = spinCity.getSelectedItem().toString();
                    performFilter(city,blood,covid);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                performFilter(city,blood,covid);
            }
        });
    }


    private void tabListener(){
       tabCovid.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               switch (tab.getPosition()){
                   case 0:
                       covid ="tidak";
                       performFilter(city,blood,covid);
                       break;
                   case 1:
                       covid ="ya";
                       performFilter(city,blood,covid);
                       break;
               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
    }
    
    private void radioListener(){
        rgBlood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.a:
                        blood ="a";
                        performFilter(city,blood,covid);
                        break;
                    case R.id.b:
                        blood ="b";
                        performFilter(city,blood,covid);
                        break;
                    case R.id.ab:
                        blood ="ab";
                        performFilter(city,blood,covid);
                        break;
                    case R.id.o:
                        blood ="o";
                        performFilter(city,blood,covid);
                        break;
                }
            }
        });
    }

    public ArrayList<VolunteersDataResponse> multipleFilter(String cityFilter ,String bloodFilter,String covidFilter,ArrayList<VolunteersDataResponse> listAllVolunteers)
    {
        ArrayList<VolunteersDataResponse> listTasksAfterFiltering = new ArrayList<>();
        for(VolunteersDataResponse obj : listAllVolunteers)
        {
            String blood = obj.getBlood();
            String city = obj.getCity();
            String covid = obj.getStatusCovid();

            if(cityFilter.equalsIgnoreCase(city) || cityFilter.isEmpty())
                if(bloodFilter.equalsIgnoreCase(blood) || bloodFilter.isEmpty())
                    if(covidFilter.equalsIgnoreCase(covid) || covidFilter.isEmpty())
                        if(!cityFilter.isEmpty() || !bloodFilter.isEmpty() || !covidFilter.isEmpty()){
                            listTasksAfterFiltering.add(obj);
                        }
        }

        return listTasksAfterFiltering;
    }

    private void performFilter(String cityFilter,String  bloodFilter, String covidFilter){
        ArrayList<VolunteersDataResponse> data = multipleFilter(cityFilter,bloodFilter, covidFilter, listVolunteer);
        Log.d("cek data", String.valueOf(data.size()));
        listVolunteerFiltered = new ArrayList<>();
        for(int i=0; i<data.size();i++){
            VolunteersDataResponse x = data.get(i);
            listVolunteerFiltered.add(new VolunteersDataResponse(
                    x.getId(),
                    x.getName(),
                    x.getBlood(),
                    x.getRhesus(),
                    x.getCity(),
                    x.getGender(),
                    x.getAge(),
                    x.getWeight(),
                    x.getPhone(),
                    x.getTotalDonor(),
                    x.getLastDonor(),
                    x.getStatusCovid(),
                    x.getSymptomps(),
                    x.getRecoveryDate(),
                    x.getUserId()
            ));
        }
        adapter = new VolunteersAdapter(getContext(), listVolunteerFiltered);
        adapter.notifyDataSetChanged();
        rvBlood.setAdapter(adapter);
        itemClickListener();
    }

    private void itemClickListener(){
        adapter.setOnItemClickListener(new VolunteersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, VolunteersDataResponse obj, int position) {
                Bundle data = new Bundle();
                data.putString("id", obj.getId());
                Tools.addFragment(getActivity(), new BloodDetailFragment(), data, "detail-volunteer");
            }
        });
    }

}