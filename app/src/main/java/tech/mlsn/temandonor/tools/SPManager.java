package tech.mlsn.temandonor.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SPManager {
    public static final String SP_LOGIN_APP = "spLoginApp";
    public static final String SP_ID = "spId";
    public static final String SP_NAME = "spName";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_PASSWORD = "spPassword";
    public static final String SP_IS_SIGNED = "spSignedIn";
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SPManager(Context context){
        sp = context.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.apply();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.apply();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.apply();
    }

    public void removeSP(){
        spEditor.remove(SP_NAME);
        spEditor.remove(SP_EMAIL);
        spEditor.remove(SP_PASSWORD);
        spEditor.apply();
    }

    public String getSpName(){
        return sp.getString(SP_NAME, "Temandonor");
    }

    public String getSpEmail(){
        return sp.getString(SP_EMAIL, "User@temandonot.com");
    }


    public String getSpPassword(){
        return sp.getString(SP_PASSWORD, "");
    }

    public String getSpId(){
        return sp.getString(SP_ID, "");
    }

    public Boolean getSpIsSigned(){
        return sp.getBoolean(SP_IS_SIGNED, false);
    }


}
