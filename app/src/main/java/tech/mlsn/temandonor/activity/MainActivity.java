package tech.mlsn.temandonor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.fragment.BloodFragment;
import tech.mlsn.temandonor.fragment.EventFragment;
import tech.mlsn.temandonor.fragment.HomeFragment;
import tech.mlsn.temandonor.fragment.VolunteerFragment;
import tech.mlsn.temandonor.fragment.account.AccountFragment;
import tech.mlsn.temandonor.tools.Tools;

public class MainActivity extends AppCompatActivity {
    ImageButton btnHome,btnVolunteer,btnEvent, btnAccount;
    FloatingActionButton btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializtion();
    }

    private void initializtion(){
        btnHome = findViewById(R.id.btnHome);
        btnVolunteer = findViewById(R.id.btnVolunteer);
        btnEvent = findViewById(R.id.btnEvent);
        btnAccount = findViewById(R.id.btnAccount);
        btnSearch = findViewById(R.id.btnSearch);

        btnHome.setOnClickListener(new clickListener(btnHome));
        btnVolunteer.setOnClickListener(new clickListener(btnVolunteer));
        btnEvent.setOnClickListener(new clickListener(btnEvent));
        btnAccount.setOnClickListener(new clickListener(btnAccount));
        btnSearch.setOnClickListener(new clickListener(btnSearch));

        Tools.removeAllFragment(MainActivity.this,new HomeFragment(), "home");
        btnHome.setImageTintList(getResources().getColorStateList(R.color.pink_paradise));
        btnVolunteer.setImageTintList(getResources().getColorStateList(R.color.white));
        btnEvent.setImageTintList(getResources().getColorStateList(R.color.white));
        btnAccount.setImageTintList(getResources().getColorStateList(R.color.white));
    }

    private class clickListener implements View.OnClickListener{
        ImageButton btn;
        FloatingActionButton btnFloat;

        public clickListener(ImageButton btn) {
            this.btn = btn;
        }

        public clickListener(FloatingActionButton btnFloat) {
            this.btnFloat = btnFloat;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnHome:
                    Tools.removeAllFragment(MainActivity.this,new HomeFragment(), "home");
                    btnHome.setImageTintList(getResources().getColorStateList(R.color.pink_paradise));
                    btnVolunteer.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnEvent.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnAccount.setImageTintList(getResources().getColorStateList(R.color.white));
                    break;
                case R.id.btnVolunteer:
                    Tools.removeAllFragment(MainActivity.this,new VolunteerFragment(), "volunteer");
                    btnHome.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnVolunteer.setImageTintList(getResources().getColorStateList(R.color.pink_paradise));
                    btnEvent.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnAccount.setImageTintList(getResources().getColorStateList(R.color.white));
                    break;
                case R.id.btnSearch:
                    Tools.removeAllFragment(MainActivity.this,new BloodFragment(), "blood");
                    btnHome.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnVolunteer.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnEvent.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnAccount.setImageTintList(getResources().getColorStateList(R.color.white));
                    break;
                case R.id.btnEvent:
                    Tools.removeAllFragment(MainActivity.this,new EventFragment(), "event");
                    btnHome.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnVolunteer.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnEvent.setImageTintList(getResources().getColorStateList(R.color.pink_paradise));
                    btnAccount.setImageTintList(getResources().getColorStateList(R.color.white));
                    break;
                case R.id.btnAccount:
                    Tools.removeAllFragment(MainActivity.this,new AccountFragment(), "account");
                    btnHome.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnVolunteer.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnEvent.setImageTintList(getResources().getColorStateList(R.color.white));
                    btnAccount.setImageTintList(getResources().getColorStateList(R.color.pink_paradise));
                    break;
            }
        }
    }
}