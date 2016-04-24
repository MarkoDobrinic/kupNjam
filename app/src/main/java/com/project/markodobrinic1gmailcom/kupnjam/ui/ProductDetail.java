package com.project.markodobrinic1gmailcom.kupnjam.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    private ImageView mImage;
    private TextView mName, mId, mPrice, mDiscPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();

        Product product = (Product) intent.getSerializableExtra(Constants.REFERENCE.PRODUCT);

        configViews();

        mName.setText(product.getName());
        mPrice.setText(String.format("$%.2f", product.getPrice()));
        mDiscPrice.setText(String.format("$%.2f", product.getDiscounted_price()));

        if (product.isFromDatabase()) {
            mImage.setImageBitmap(product.getPicture());
        } else {
            Picasso.with(getApplicationContext()).load(Constants.HTTP.IMAGE_URL + product.getImage()).into(mImage);
        }
    }

    private void configViews() {
        mImage = (ImageView) findViewById(R.id.productDetailImage);
        mName = (TextView) findViewById(R.id.productDetailName);
        mDiscPrice = (TextView) findViewById(R.id.productDetailDPrice);
        mPrice = (TextView) findViewById(R.id.productDetailPrice);
    }

}
