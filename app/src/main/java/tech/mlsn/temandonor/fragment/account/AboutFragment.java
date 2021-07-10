package tech.mlsn.temandonor.fragment.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tech.mlsn.temandonor.R;

public class AboutFragment extends Fragment {
    ImageView ivRifqi, ivBowo, ivLogo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        initialization(view);
        return view;
    }

    private void initialization(View view){
        ivLogo = view.findViewById(R.id.ivLogo);
        ivRifqi = view.findViewById(R.id.ivRifqi);
        ivBowo = view.findViewById(R.id.ivBowo);

        Glide.with(getContext()).load(R.drawable.logo).into(ivLogo);
        Glide.with(getContext()).load("http://temandonor.rzzkan.com/iki/img/blood/IMG_6125.JPG").centerCrop().into(ivRifqi);
        Glide.with(getContext()).load("http://temandonor.rzzkan.com/iki/img/blood/bowo.jpeg").centerCrop().into(ivBowo);
    }
}