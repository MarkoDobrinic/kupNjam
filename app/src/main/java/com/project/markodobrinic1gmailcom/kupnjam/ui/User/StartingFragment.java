package com.project.markodobrinic1gmailcom.kupnjam.ui.User;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.controller.usercontroller.ServerRequest;
import com.project.markodobrinic1gmailcom.kupnjam.controller.usercontroller.ServerResponse;
import com.project.markodobrinic1gmailcom.kupnjam.model.callback.UsersInterface;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.User;
import com.project.markodobrinic1gmailcom.kupnjam.ui.ProductListMain;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marko.dobrinic1@gmail.com on 28.4.2016..
 */
public class StartingFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton mBtnEmail, mBtnFacebook, mBtnGoogle;
    private TextView mGoToProducts;
    private SharedPreferences prefStart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start,container,false);
        initViews(view);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


    }


    private void initViews(View view){

        prefStart = getActivity().getPreferences(0);
        mBtnEmail = (AppCompatButton) view.findViewById(R.id.btnStartEmail);
        mBtnFacebook = (AppCompatButton) view.findViewById(R.id.btn_facebook);
        mBtnGoogle = (AppCompatButton)view.findViewById(R.id.btn_google);
        mGoToProducts = (TextView)view.findViewById(R.id.tv_products);

        mBtnEmail.setOnClickListener(this);
        mBtnFacebook.setOnClickListener(this);
        mBtnGoogle.setOnClickListener(this);
        mGoToProducts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartEmail:
                goToMainLogin();
                break;
            case R.id.tv_products:
                Intent intent = new Intent(getActivity(), ProductListMain.class);
                startActivity(intent);
                break;
            case R.id.btn_facebook:
                Toast.makeText(getActivity(), "Facebook prijava trenutno nije dostupna!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_google:
                Toast.makeText(getActivity(), "Google+ prijava trenutno nije dostupna!", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void goToMainLogin(){
        Fragment register = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,register);
        ft.commit();
    }



}
