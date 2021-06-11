package tech.mlsn.temandonor.fragment.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tech.mlsn.temandonor.R;
import tech.mlsn.temandonor.activity.LoginActivity;
import tech.mlsn.temandonor.tools.SPManager;
import tech.mlsn.temandonor.tools.Tools;

public class AccountFragment extends Fragment {
    Button btnUpdateProfile, btnUpdatePassword, btnAbout, btnTesti, btnLogout;
    SPManager spManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initialization(view);
        clickListener();
        return view;
    }

    private void initialization(View view){
        btnUpdateProfile = view.findViewById(R.id.btnChangeProfile);
        btnUpdatePassword = view.findViewById(R.id.btnChangePassword);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnTesti = view.findViewById(R.id.btnTestimoni);

        spManager = new SPManager(getContext());
    }

    private void clickListener(){
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addFragment(getActivity(), new UpdateAccountFragment(),null, "update_accont");
            }
        });

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addFragment(getActivity(), new UpdatePasswordFragment(),null, "update_pw");
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addFragment(getActivity(), new AboutFragment(),null, "about");
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {logoutDialog();
            }
        });

        btnTesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.addFragment(getActivity(), new AddTestimoniFragment(), null,"add-testi");
            }
        });
    }

    public void logoutDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle    ("Logout");
        builder.setMessage("Apakah yakin ingin keluar ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                spManager.saveSPBoolean(spManager.SP_IS_SIGNED, false);
                spManager.removeSP();
                startActivity(new Intent(getActivity(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                getActivity().finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}