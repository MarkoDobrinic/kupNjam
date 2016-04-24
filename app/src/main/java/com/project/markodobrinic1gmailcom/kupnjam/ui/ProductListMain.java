package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.controller.RestManager;
import com.project.markodobrinic1gmailcom.kupnjam.model.callback.ProductFetchListener;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;
import com.project.markodobrinic1gmailcom.kupnjam.model.adapter.ProductAdapter;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.database.ProductDatabase;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Utilities;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListMain extends AppCompatActivity implements ProductAdapter.ProductClickListener, ProductFetchListener{

    private static final String TAG = ProductListMain.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RestManager mManager;
    private ProductAdapter mProductAdapter;
    private ProductDatabase mDatabase;
    private Button mReload;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);

        configViews();

        mManager = new RestManager();
        mDatabase = new ProductDatabase(this);

        loadProductFeed();

        mReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductFeed();
            }
        });
    }

    private void loadProductFeed() {

        mDialog = new ProgressDialog(ProductListMain.this);
        mDialog.setMessage("Učitavanje podataka o proizvodima...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);

        mProductAdapter.reset();

        mDialog.show();

        if (getNetworkAvailability()) {
            getFeed();
        } else {
            getFeedFromDatabase();
        }
    }

    private void getFeedFromDatabase() {
       mDatabase.fetchProducts(this);
    }

    private void configViews() {

        mReload = (Button) findViewById(R.id.reload);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mProductAdapter = new ProductAdapter(this);

        mRecyclerView.setAdapter(mProductAdapter);
    }

    @Override
    public void onClick(int position) {
        Product selectedProduct = mProductAdapter.getSelectedProduct(position);
        Intent intent = new Intent(ProductListMain.this, ProductDetail.class);
        intent.putExtra(Constants.REFERENCE.PRODUCT, selectedProduct);
        startActivity(intent);
    }


    public void getFeed() {

        Call<List<Product>> listCall = mManager.getFlowerService().getAllProducts();
        listCall.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    List<Product> productList = response.body();

                    for (int i = 0; i < productList.size(); i++) {
                        Product flower = productList.get(i);

                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(flower);

                        mProductAdapter.addProduct(flower);
                    }
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean getNetworkAvailability() {
        return Utilities.isNetworkAvailable(getApplicationContext());
    }

    @Override
    public void onDeliverAllProducts(List<Product> productses) {
    }

    @Override
    public void onDeliverProduct(Product product) {
        mProductAdapter.addProduct(product);
    }

    @Override
    public void onHideDialog() {
        mDialog.dismiss();
    }


    public class SaveIntoDatabase extends AsyncTask<Product, Void, Void> {


        private final String TAG = SaveIntoDatabase.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Product... params) {

            Product product = params[0];

            try {
                InputStream stream = new URL(Constants.HTTP.IMAGE_URL + product.getImage()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                product.setPicture(bitmap);
                mDatabase.addProduct(product);

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            return null;
        }
    }





}
