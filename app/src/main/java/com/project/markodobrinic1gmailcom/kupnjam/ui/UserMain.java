package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.ui.User.LoginFragment;
import com.project.markodobrinic1gmailcom.kupnjam.ui.User.ProfileFragment;
import com.project.markodobrinic1gmailcom.kupnjam.ui.User.StartingFragment;

public class UserMain extends AppCompatActivity{

    private SharedPreferences pref;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        pref = getPreferences(0);
        initFragment();
        mProgressDialog.dismiss();
    }

    private void initFragment(){

        mProgressDialog = new ProgressDialog(UserMain.this);
        mProgressDialog.setMessage("Molimo pričekajte...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        Fragment fragment;
//        if(pref.getBoolean(Constants.USERS.IS_LOGGED_IN, false)){
//            Intent intent = new Intent(this, ProductListMain.class);
//            startActivity(intent);
//            Toast.makeText(this, "Dobrodošli : " + pref.getString(Constants.USERS.NAME,""), Toast.LENGTH_LONG);
//        }else {
//            Fragment fragment;
//            fragment = new StartingFragment();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_frame,fragment);
//            ft.commit();
//            Toast.makeText(this, "drugo" , Toast.LENGTH_LONG).show();
//        }
            fragment = new StartingFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,fragment);
            ft.commit();
    }
}
