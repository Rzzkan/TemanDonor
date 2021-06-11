package tech.mlsn.temandonor.tools;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tech.mlsn.temandonor.R;

/**
 * Created by Rzzkan on 10/05/2021.
 */
public class Tools {
    public static void addFragment(FragmentActivity activity, Fragment fragment, Bundle bundle , String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(tag);
        ft.replace(R.id.main_content, fragment, tag);
        ft.commitAllowingStateLoss();
        fragment.setArguments(bundle);
    }

    public static void removeAllFragment(FragmentActivity activity,Fragment replaceFragment, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.replace(R.id.main_content, replaceFragment);
        ft.commitAllowingStateLoss();
    }

    public static String dateParser(String currentDate){
        SimpleDateFormat currentFormat,newFormat;
        String newDate = "";
        currentFormat = new SimpleDateFormat("yyyy-MM-dd");
        newFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        try {
            Date getDate = currentFormat.parse(currentDate);
            newDate = newFormat.format(getDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
