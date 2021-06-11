package tech.mlsn.temandonor.fragment.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.adapter.CommentsAdapter;
import tech.mlsn.temandonor.adapter.NewsAdapter;
import tech.mlsn.temandonor.adapter.SliderAdapter;
import tech.mlsn.temandonor.model.SliderModel;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.CommentDataResponse;
import tech.mlsn.temandonor.response.CommentsResponse;
import tech.mlsn.temandonor.response.NewsDataResponse;
import tech.mlsn.temandonor.response.NewsResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;
import tech.mlsn.temandonor.tools.Tools;

public class HomeFragment extends Fragment {
    SliderView imgSlider;
    RecyclerView rvNews, rvTesti;

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;

    SliderAdapter sliderAdapter;

    NewsAdapter adapterNews;
    ArrayList<NewsDataResponse> listNews;

    CommentsAdapter adapterComment;
    ArrayList<CommentDataResponse> listComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initialization(view);
        addSlider();
        getNews();
        getComments();
        newsClickListener();
        return view;
    }

    private void initialization(View view){
        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        imgSlider = view.findViewById(R.id.imageSlider);
        rvNews = view.findViewById(R.id.rvNews);
        rvTesti = view.findViewById(R.id.rvTesti);

        sliderAdapter = new SliderAdapter(getContext());
        imgSlider.setSliderAdapter(sliderAdapter);

        rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNews.setHasFixedSize(true);
        listNews = new ArrayList<>();
        adapterNews = new NewsAdapter(getContext(), listNews);
        rvNews.setAdapter(adapterNews);

        rvTesti.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTesti.setHasFixedSize(true);
        listComments = new ArrayList<>();
        adapterComment = new CommentsAdapter(getContext(), listComments);
        rvTesti.setAdapter(adapterComment);

    }

    private void addSlider(){
        sliderAdapter.addItem(new SliderModel("http://temandonor.rzzkan.com/iki/img/blood/darah02.jpg","Donor Darah","Merupakan proses pengambilan darah dari seseorang secara sukarela untuk disimpan di bank darah sebagai stok darah untuk kemudian digunakan untuk transfusi darah."));
        sliderAdapter.addItem(new SliderModel("http://temandonor.rzzkan.com/iki/img/blood/darah4.jpg","Manfaat Donor Darah","Bagi pendonor: Menurunkan risiko penyakit jantung & pembuluh darah, menurunkan risiko kanker, menurunkan berat badan, mendeteksi penyakit dan membuat lebih sehat secara psikologis dan memperpanjang usia."));
        sliderAdapter.addItem(new SliderModel("http://temandonor.rzzkan.com/iki/img/blood/darah1.jpg","Misi Teman Donor","Memudahkan pencarian orang yang bersedia mendonorkan darahnya bagi yang membutuhkan."));
    }

    private void newsClickListener(){
        adapterNews.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, NewsDataResponse obj, int position) {
                Bundle data = new Bundle();
                data.putString("id", obj.getId());
                Tools.addFragment(getActivity(), new DetailNewsFragment(), data, "detail-news");
            }
        });
    }

    private void getNews(){
        Call<NewsResponse> getNews = apiInterface.allNews();

        getNews.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.body().getSuccess()==1) {
//                    snackbar.snackSuccess("Success");
                    for (int i=0; i<response.body().getData().size();i++){
                        NewsDataResponse data = response.body().getData().get(i);
                        listNews.add(new NewsDataResponse(
                                data.getId(),
                                data.getHeadline(),
                                data.getDescription(),
                                data.getContent(),
                                data.getNewsDate(),
                                data.getPath(),
                                data.getFilename()
                        ));

                    }
                    adapterNews.notifyDataSetChanged();
                } else{
//                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });
    }

    private void getComments(){
        Call<CommentsResponse> getComment = apiInterface.allComments();

        getComment.enqueue(new Callback<CommentsResponse>() {
            @Override
            public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                if (response.body().getSuccess()==1) {
//                    snackbar.snackSuccess("Success");
                    for (int i=0; i<response.body().getData().size();i++){
                        CommentDataResponse data = response.body().getData().get(i);
                        listComments.add(new CommentDataResponse(
                                data.getId(),
                                data.getUsername(),
                                data.getCommentDate(),
                                data.getComment()
                        ));

                    }
                    adapterComment.notifyDataSetChanged();
                } else{
//                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<CommentsResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });
    }
}