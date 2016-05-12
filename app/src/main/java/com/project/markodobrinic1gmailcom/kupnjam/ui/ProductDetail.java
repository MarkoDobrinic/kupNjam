package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImage, mStore;
    private TextView mName, mId, mPrice, mDiscPrice, mDescription, mDate;
    private FloatingActionButton mButtonCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

//        /** facebook logging stuff **/
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);


        Intent intent = getIntent();

        Product product = (Product) intent.getSerializableExtra(Constants.REFERENCE.PRODUCT);

        configViews();

        mName.setText(product.getName());
        mPrice.setText(String.format("%.2f kn", product.getPrice()));
        mPrice.setPaintFlags(mPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        mDiscPrice.setText(String.format("%.2f kn", product.getDiscounted_price()));
        mDate.setText("Na akciji od: " + product.getStart_date() + " do " + product.getEnd_date());
        mDescription.setText(product.getDescription());

        switch (product.getStore_id()) {
            case 1:
                mStore.setImageResource(R.drawable.konzum);
                break;
            case 2:
                mStore.setImageResource(R.drawable.billa);
                break;
            case 3:
                mStore.setImageResource(R.drawable.spar);
                break;
            default:
        }


        if (product.isFromDatabase()) {
            mImage.setImageBitmap(product.getPicture());
        } else {
            Picasso.with(getApplicationContext()).load(Constants.HTTP.IMAGE_URL + product.getImage()).into(mImage);
        }

       mButtonCart.setOnClickListener(this);

    }

    private void configViews() {

        mButtonCart = (FloatingActionButton) findViewById(R.id.btnDetailCart);
        mDate = (TextView) findViewById(R.id.productDetailDate);
        mDescription = (TextView) findViewById(R.id.productDetailInfo);
        mImage = (ImageView) findViewById(R.id.productDetailImage);
        mName = (TextView) findViewById(R.id.productDetailName);
        mDiscPrice = (TextView) findViewById(R.id.productDetailDPrice);
        mPrice = (TextView) findViewById(R.id.productDetailPrice);
        mStore = (ImageView) findViewById(R.id.productDetailStore);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDetailCart:
                Toast.makeText(ProductDetail.this, "Ova funkcionalnost trenutno nije dostupna!", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(ProductDetail.this, "TBA", Toast.LENGTH_SHORT).show();
        }
    }
}
