package com.project.markodobrinic1gmailcom.kupnjam.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Utilities;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;
import com.project.markodobrinic1gmailcom.kupnjam.ui.custom.CounterView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko.dobrinic1@gmail.com on 23.4.2016..
 * extending RecyclerView
 **/

/** extending RecyclerView **/

/** implements Filterable? **/
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {


    private static final String TAG = ProductAdapter.class.getSimpleName();
    private final ProductClickListener mListener;
    public List<Product> mProducts;
    public List<Product> mFilterableProducts;


    public ProductAdapter(ProductClickListener listener) {
        mProducts = new ArrayList<>();
        mFilterableProducts = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Product currProduct = mFilterableProducts.get(position);

        holder.mName.setText(currProduct.getName());
        holder.mPrice.setText(String.format("%.2f kn", currProduct.getPrice()));
        holder.mDiscPrice.setText(String.format("%.2f kn", currProduct.getDiscounted_price()));
        holder.mProductDate.setText("Akcija traje od: " + currProduct.getStart_date() + " do " + currProduct.getEnd_date());

       // holder.handleCounter(position);

        switch (currProduct.getStore_id()) {
            case 1:
                holder.mStorePhoto.setImageResource(R.drawable.konzum);
                    break;
            case 2:
                holder.mStorePhoto.setImageResource(R.drawable.billa);
                break;
            case 3:
                holder.mStorePhoto.setImageResource(R.drawable.spar);
                break;
            default:
        }

        if (currProduct.isFromDatabase()) {
            holder.mPhoto.setImageBitmap(currProduct.getPicture());
        } else {
            if (currProduct.getImage() != null) {
                Picasso.with(holder.itemView.getContext()).load(Constants.HTTP.IMAGE_URL + currProduct.getImage()).into(holder.mPhoto);
            } else {
                Picasso.with(holder.itemView.getContext()).load(R.mipmap.ic_launcher).into(holder.mPhoto);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFilterableProducts.size();
    }

    public void addProduct(Product product) {
        mProducts.add(product);
        mFilterableProducts.add(product);
        notifyDataSetChanged();
    }


    public Product getSelectedProduct(int position) {
        return mFilterableProducts.get(position);
    }

    public void reset() {
        mProducts.clear();
        mFilterableProducts.clear();
        notifyDataSetChanged();
    }

    public void filter(String key) {
        mFilterableProducts = new ArrayList<>();
        for (Product product : mProducts) {
            if (product.getName().toLowerCase().contains(key.toString())) {
                mFilterableProducts.add(product);
            }
        }
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhoto, mStorePhoto;
        private TextView mName, mPrice, mDiscPrice, mProductDate;
        private CounterView mCounter;

        public Holder(View itemView) {
            super(itemView);
            mProductDate = (TextView) itemView.findViewById(R.id.productDate);
            mPhoto = (ImageView) itemView.findViewById(R.id.productImage);
            mStorePhoto = (ImageView) itemView.findViewById(R.id.productStoreImage);
            mName = (TextView) itemView.findViewById(R.id.productName);
            mPrice = (TextView) itemView.findViewById(R.id.productPrice);
            mDiscPrice = (TextView) itemView.findViewById(R.id.productDiscPrice);
//            mCounter = (CounterView) itemView.findViewById(R.id.counterView);
//            mCounter.setOnCounterChanged(new CounterView.OnCounterChanged() {
//                @Override
//                public void onChange(int count) {
//                    //int x = count;
//                    Product product = mFilterableProducts.get(getLayoutPosition());
//                    //Product product = new Product();
//                    product.setName("Test");
//                    mListener.onUpdateBasket(count, product);
//                }
//            });
            itemView.setOnClickListener(this);
        }

        public void handleCounter(int position) {
            Product product = mFilterableProducts.get(position);
           // mCounter.setCounterValue(mListener.getCounter(product.getName()));
        }

        public void handleClick(){

        }

        @Override
        public void onClick(View v) {
                    int layoutPosition = getLayoutPosition();
            switch (v.getId()) {
                default:
                    mListener.onClick(layoutPosition);
            }
        }
    }

    public interface ProductClickListener {

        void onClick(int position);

        void onAddClicked();

        void onRemovedClicked();

        void onUpdateBasket(int count, Product product);

        int getCounter(String name);
    }


}



