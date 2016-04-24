package com.project.markodobrinic1gmailcom.kupnjam.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko.dobrinic1@gmail.com on 23.4.2016..
 */
public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.Holder> {

    private static final String TAG = ProductAdapter.class.getSimpleName();
    private final ProductClickListener mListener;
    private List<Product> mProducts;

    public ProductAdapter(ProductClickListener listener) {
        mProducts = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Product currProduct = mProducts.get(position);

        holder.mName.setText(currProduct.getName());
        holder.mPrice.setText(String.format("$%.2f", currProduct.getPrice()));

        if (currProduct.isFromDatabase()) {
            holder.mPhoto.setImageBitmap(currProduct.getPicture());
        } else {
            if(currProduct.getImage() != null) {
                Picasso.with(holder.itemView.getContext()).load(Constants.HTTP.IMAGE_URL + currProduct.getImage()).into(holder.mPhoto);
            }else
            {
                Picasso.with(holder.itemView.getContext()).load(R.mipmap.ic_launcher).into(holder.mPhoto);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void addProduct(Product product) {
        mProducts.add(product);
        notifyDataSetChanged();
    }

    /**
     * @param position
     * @return
     */
    public Product getSelectedProduct(int position) {
        return mProducts.get(position);
    }

    public void reset() {
        mProducts.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhoto;
        private TextView mName, mPrice, mDiscPrice;

        public Holder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.productImage);
            mName = (TextView) itemView.findViewById(R.id.productName);
            mPrice = (TextView) itemView.findViewById(R.id.productPrice);
            mDiscPrice = (TextView) itemView.findViewById(R.id.productDiscPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface ProductClickListener {
        void onClick(int position);
    }




}
