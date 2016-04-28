package com.project.markodobrinic1gmailcom.kupnjam.ui.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.ui.ProductListMain;

/**
 * Created by marko.dobrinic1@gmail.com on 25.4.2016..
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{


    private TextView tv_name,tv_email,tv_message;
    private SharedPreferences pref;
    private AppCompatButton btn_logout;
    private AppCompatButton btn_search_products;
    private AlertDialog dialog;
    private ProgressBar progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        pref = getActivity().getPreferences(0);
        tv_name.setText("Dobrodošli : "+ pref.getString(Constants.USERS.NAME,""));
        tv_email.setText(pref.getString(Constants.USERS.EMAIL,""));
        Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();

    }

    private void initViews(View view){

        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_email = (TextView)view.findViewById(R.id.tv_email);
        btn_logout = (AppCompatButton)view.findViewById(R.id.btn_logout);
        btn_search_products = (AppCompatButton)view.findViewById(R.id.btn_search_products);
        btn_logout.setOnClickListener(this);
        btn_search_products.setOnClickListener(this);

    }

    private void showDialog(){

        /** TU pogledati što treba pokrenut */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_product_main, null);
       // tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /** tu promijeniti za buttone gdje i što */
//            case R.id.btn_login:
//                showDialog();
//                break;
            case R.id.btn_logout:
                logout();
                break;
            case R.id.btn_search_products:
                goToProducts();
                break;
            default:
                goToProducts();
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.USERS.IS_LOGGED_IN,false);
        editor.putString(Constants.USERS.EMAIL,"");
        editor.putString(Constants.USERS.NAME,"");
        editor.putString(Constants.USERS.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    private void goToLogin(){
        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    private void goToProducts(){
        Intent intent = new Intent(getActivity(), ProductListMain.class);
        startActivity(intent);
    }


}
