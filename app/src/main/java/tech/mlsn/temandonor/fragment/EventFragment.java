package tech.mlsn.temandonor.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.adapter.EventsAdapter;
import tech.mlsn.temandonor.adapter.NewsAdapter;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.EventDataResponse;
import tech.mlsn.temandonor.response.NewsDataResponse;
import tech.mlsn.temandonor.response.EventsResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;

public class EventFragment extends Fragment {
    RecyclerView rvEvent;

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;

    EventsAdapter adapter;
    ArrayList<EventDataResponse> listEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        initialization(view);
        getEvent();
        return view;
    }

    private void initialization(View view){
        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        rvEvent = view.findViewById(R.id.rvEvent);

        rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvEvent.setHasFixedSize(true);
        listEvent = new ArrayList<>();
        adapter = new EventsAdapter(getContext(), listEvent);
        rvEvent.setAdapter(adapter);
    }

    private void getEvent(){
        Call<EventsResponse> getEvent = apiInterface.allEvents();

        getEvent.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                if (response.body().getSuccess()==1) {
//                    snackbar.snackSuccess("Success");
                    for (int i=0; i<response.body().getData().size();i++){
                        EventDataResponse data = response.body().getData().get(i);
                        listEvent.add(new EventDataResponse(
                                data.getId(),
                                data.getName(),
                                data.getDescription(),
                                data.getEventDate(),
                                data.getPath(),
                                data.getFilename()
                        ));
                    }
                    adapter.notifyDataSetChanged();
                } else{
//                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });
    }
}