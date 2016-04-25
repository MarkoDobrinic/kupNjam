package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.controller.RestManager;
import com.project.markodobrinic1gmailcom.kupnjam.model.adapter.ProductAdapter;
import com.project.markodobrinic1gmailcom.kupnjam.model.callback.ProductFetchListener;
import com.project.markodobrinic1gmailcom.kupnjam.model.callback.ProductService;
import com.project.markodobrinic1gmailcom.kupnjam.model.database.ProductDatabase;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Utilities;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marko.dobrinic1@gmail.com on 25.4.2016..
 */
public class ProductListFilter extends AppCompatActivity{




}


