package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.controller.RestManager;
import com.project.markodobrinic1gmailcom.kupnjam.model.adapter.ProductAdapter;
import com.project.markodobrinic1gmailcom.kupnjam.model.callback.ProductFetchListener;
import com.project.markodobrinic1gmailcom.kupnjam.model.database.ProductDatabase;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Utilities;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.ShoppingProductDetail;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListMain extends AppCompatActivity implements ProductAdapter.ProductClickListener, ProductFetchListener, View.OnClickListener{

    private static final String TAG = ProductListMain.class.getSimpleName();
    boolean doubleBackToExitPressed = false;
    private RecyclerView mRecyclerView;
    private RestManager mManager;
    private ProductAdapter mProductAdapter;
    private ProductDatabase mDatabase;
    private Button mReload, mSearch;
    private ImageButton mSettings;
    private ProgressDialog mDialog;
    private Toolbar toolbar;
    private DrawerLayout mMainDrawer;
    private EditText mSearchField;
    private TextView mCounter;
    private int counter;
    private Map<String, Integer> mBasket = new HashMap<>();
    private List<ShoppingProductDetail> mShoppinList = new ArrayList<>();
    private ImageView mShoppingList;
    private NavigationView mNavigationView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main);
        mManager = new RestManager();
        mDatabase = new ProductDatabase(this);

        /** Configuring Views **/
        configViews();

        /** onTextChanged Listener **/
        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mProductAdapter.filter(s.toString().toLowerCase());
            }
        });

        /** loading product Feed **/
        loadProductFeed();

        mReload.setOnClickListener(this);

        mSearch.setOnClickListener(this);

        mSettings.setOnClickListener(this);

        mShoppingList.setOnClickListener(this);
    }

    private void loadProductFeed() {

        mDialog = new ProgressDialog(ProductListMain.this);
        mDialog.setMessage("Učitavanje proizvoda...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);

        mProductAdapter.reset();
        mSwipeRefreshLayout.setRefreshing(false);
        mDialog.show();

        if (getNetworkAvailability()) {
            getFeed();
        } else {
            getFeedFromDatabase();
        }
    }

    //private
    public void getFeedFromDatabase() {
       mDatabase.fetchProducts(this);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void configViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /** ako bude moguće, ovo maknuti **/
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadProductFeed();
            }
        });
        mSettings = (ImageButton) findViewById(R.id.sandwich_menu);
        mMainDrawer = (DrawerLayout) findViewById(R.id.mainDrawer);
        mCounter = (TextView) findViewById(R.id.counter);
        mSearch = (Button) findViewById(R.id.search_product);
        mReload = (Button) findViewById(R.id.reload);
        mSearchField = (EditText) findViewById(R.id.searchField);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mSearchField, InputMethodManager.SHOW_IMPLICIT);
        mShoppingList = (ImageView) findViewById(R.id.shoppingList);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_id:
                        Toast.makeText(ProductListMain.this, "Ova funkcionalnost trenutno nije dostupna!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout_id:
                        doLogout();
                        return true;
                    case R.id.home_id:
                        Toast.makeText(ProductListMain.this, "Ova funkcionalnost trenutno nije dostupna!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.reloadProducts_id:
                        mMainDrawer.closeDrawer(Gravity.LEFT);
                        loadProductFeed();
                        Intent i = new Intent(ProductListMain.this, ProductListMain.class);
                        startActivity(i);
                        return true;
                    case R.id.mylist_id:
                        loadMyShoppingList();
                        return true;
                    case R.id.about_id:
                        AlertDialog dialog = new AlertDialog.Builder(ProductListMain.this)
                                .setTitle("")
                                .setView(R.layout.about_layout)
                                .setCancelable(true)
                                .setPositiveButton("Zatvori", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                        dialog.show();
                        return true;
                }
                return false;
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMain);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mProductAdapter = new ProductAdapter(this);
        mRecyclerView.setAdapter(mProductAdapter);
    }

    private void doLogout() {
//        SharedPreferences pref = getPreferences(0);
//        SharedPreferences.Editor editor = pref.edit();
//
//        editor.putBoolean(Constants.USERS.IS_LOGGED_IN, false);
//        editor.putString(Constants.USERS.EMAIL,"");
//        editor.putString(Constants.USERS.NAME,"");
//        editor.putString(Constants.USERS.UNIQUE_ID,"");
//        editor.clear();
//        editor.commit();
        finish();
        Intent intent = new Intent(this, UserMain.class);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        Product selectedProduct = mProductAdapter.getSelectedProduct(position);
        Intent intent = new Intent(ProductListMain.this, ProductDetail.class);
        intent.putExtra(Constants.REFERENCE.PRODUCT, selectedProduct);
        startActivity(intent);
    }

    @Override
    public void onAddClicked() {
        mCounter.setText(getCounter(++counter));
    }

    private String getCounter(int counter) {
        return Integer.toString(counter);
    }

    @Override
    public void onUpdateBasket(Product product) {

        if(product.isChecked() && !isProductAlreadyInTheList(product.getName())) {

            ShoppingProductDetail details = new ShoppingProductDetail();
            details.setProductName(product.getName());
            details.setPrice(product.getPrice());
            details.setStoreId(product.getStore_id());

            mShoppinList.add(details);
        } else if(!product.isChecked() && isProductAlreadyInTheList(product.getName())) {
            removeByProductName(product.getName());
        }

        //mBasket.put(product.getName(), 1);

       // Log.d(TAG, "------------------------------------------------");

        //for (Map.Entry<String, Integer> entry : mBasket.entrySet()) {
          //  Log.d(TAG, entry.getKey());
        //}
        mCounter.setText(Integer.toString(mShoppinList.size()));
       // Toast.makeText(ProductListMain.this, "Count - " + count, Toast.LENGTH_SHORT).show();
    }

    private boolean isProductAlreadyInTheList(String name) {
        for (ShoppingProductDetail shoppingProductDetail : mShoppinList) {
            if(shoppingProductDetail.getProductName().equals(name))
                return true;
        }
        return false;
    }

    private void removeByProductName(String name) {

        ShoppingProductDetail readyToBeRemoved = null;

        for (ShoppingProductDetail detail : mShoppinList) {
            if(detail.getProductName().equals(name))
                readyToBeRemoved = detail;
        }

        mShoppinList.remove(readyToBeRemoved);
    }

    @Override
    public int getCounter(String name) {
        return mBasket.get(name) == null ? 0 : mBasket.get(name);
    }


    public void getFeed() {

        Call<List<Product>> listCall = mManager.getProductService().getAllProducts();
        listCall.enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                if (response.isSuccessful()) {
                    List<Product> productList = response.body();

                    for (int i = 0; i < productList.size(); i++) {
                        Product product = productList.get(i);

                        SaveIntoDatabase task = new SaveIntoDatabase();
                        task.execute(product);

                        mProductAdapter.addProduct(product);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.reload:
                loadProductFeed();
                break;
            case R.id.search_product:
                if(mSearchField.getVisibility() == View.VISIBLE){
                    mSearchField.setVisibility(View.GONE);
                }else {
                    mSearchField.setVisibility(View.VISIBLE);
                    mSearchField.requestFocus();
                }
                break;
            case R.id.sandwich_menu:
                mMainDrawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.shoppingList:
                loadMyShoppingList();
            default:
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        this.doubleBackToExitPressed = true;
        Toast.makeText(this, "Kliknite nazad još jednom za izlaz.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressed=false;
            }
        }, 2000);
    }

    private void loadMyShoppingList(){
        Intent intent = new Intent(this, ProductListUser.class);
        //intent.putExtra("products", (Serializable) mBasket);
        intent.putExtra("productList", (ArrayList<ShoppingProductDetail>) mShoppinList);
        startActivity(intent);
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
