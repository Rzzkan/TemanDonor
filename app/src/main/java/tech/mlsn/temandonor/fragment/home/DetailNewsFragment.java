package tech.mlsn.temandonor.fragment.home;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.network.ApiClient;
import tech.mlsn.temandonor.network.ApiInterface;
import tech.mlsn.temandonor.response.EventDataResponse;
import tech.mlsn.temandonor.response.GetNewsResponse;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.SnackbarHandler;
import tech.mlsn.temandonor.tools.Tools;

public class DetailNewsFragment extends Fragment {
    ImageView ivNews;
    TextView tvHeadline, tvDate, tvContent;
    HtmlTextView htmlText;

    String id="";
    Spanned content;

    SPManager spManager;
    SnackbarHandler snackbar;
    ApiInterface apiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_news, container, false);
        initialization(view);
        getData();
        return view;
    }

    private void initialization(View view){

        spManager = new SPManager(getContext());
        snackbar = new SnackbarHandler(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        tvHeadline = view.findViewById(R.id.tvHeadline);
        tvDate = view.findViewById(R.id.tvDate);
        tvContent = view.findViewById(R.id.tvContent);
        ivNews = view.findViewById(R.id.ivNews);
        htmlText = view.findViewById(R.id.htmlText);
    }

    private void getData(){
        Bundle data = this.getArguments();
        id = data.getString("id","0");
        getNews(id);
    }

    private void getNews(String id){
        Call<GetNewsResponse> getNews = apiInterface.getNews(
                id
        );
        getNews.enqueue(new Callback<GetNewsResponse>() {
            @Override
            public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
                if (response.body().getSuccess()==1) {
                    snackbar.snackSuccess("Success");
                    tvHeadline.setText(response.body().getData().getHeadline());
                    tvDate.setText(Tools.dateParser(response.body().getData().getNewsDate()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        content = Html.fromHtml(response.body().getData().getContent(),Html.FROM_HTML_MODE_LEGACY);
                        tvContent.setText(content);
                        htmlText.setHtml(content.toString());
                    }else {
                        Html.fromHtml(response.body().getData().getContent()).toString();
                        tvContent.setText(content);
                        htmlText.setHtml(content.toString());
                    }

                    Glide.with(getContext()).load("http://temandonor.rzzkan.com/picture/"+response.body().getData().getFilename()).centerCrop().into(ivNews);
                } else{
                    snackbar.snackError("Failed");
                }
            }
            @Override
            public void onFailure(Call<GetNewsResponse> call, Throwable t) {
                snackbar.snackInfo("No Connection");
            }
        });
    }
}