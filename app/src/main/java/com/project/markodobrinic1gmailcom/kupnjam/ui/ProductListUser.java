package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.adapter.ShoppingListAdapter;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.StoreComparator;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Header;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Item;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.ShoppingItem;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.ShoppingProductDetail;

import java.util.Collections;
import java.util.List;

public class ProductListUser extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ProductListUser.class.getSimpleName();
    private RecyclerView mList;
    //private ShoppingListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ShoppingProductDetail> myList;
    private Button mBack;
    private ShoppingListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_user);

        Intent intent = getIntent();
        myList = (List<ShoppingProductDetail>) intent.getSerializableExtra("productList");

        Collections.sort(myList, new StoreComparator());

       // myList = productList;
        //Toast.makeText(ProductListUser.this, productList.toString(), Toast.LENGTH_SHORT).show();

//        Map<String, Integer> products = (Map<String, Integer>) intent.getSerializableExtra("products");
//        Toast.makeText(ProductListUser.this, products.toString(), Toast.LENGTH_SHORT).show();

        configViews();
        mBack.setOnClickListener(this);

        int tempStoreId = -1;

        for (int i = 0; i < myList.size(); i++) {

            ShoppingProductDetail detail = myList.get(i);
            int storeId = detail.getStoreId();
            if(tempStoreId != storeId){
                tempStoreId = storeId;
                mAdapter.addItem(new Header(tempStoreId));
            }

            mAdapter.addItem(new ShoppingItem(detail));
        }
    }

    private void configViews() {

        mBack = (Button) findViewById(R.id.back);
        mList = (RecyclerView) findViewById(R.id.recyclerViewUser);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ShoppingListAdapter(getLayoutInflater());
        mList.setAdapter(mAdapter);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this,  android.R.layout.simple_list_item_1, myList  );
      //mList.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                super.onBackPressed();
        }
    }
}
