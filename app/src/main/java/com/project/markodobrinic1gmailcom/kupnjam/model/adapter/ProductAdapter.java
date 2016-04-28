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
        holder.handleCounter(position);

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

        private ImageView mPhoto;
        private TextView mName, mPrice, mDiscPrice;
        private CounterView mCounter;

        public Holder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.productImage);
            mName = (TextView) itemView.findViewById(R.id.productName);
            mPrice = (TextView) itemView.findViewById(R.id.productPrice);
            mDiscPrice = (TextView) itemView.findViewById(R.id.productDiscPrice);
            mCounter = (CounterView) itemView.findViewById(R.id.counterView);
            mCounter.setOnCounterChanged(new CounterView.OnCounterChanged() {
                @Override
                public void onChange(int count) {
                    int x = count;
                    //Product product = mFilterableProducts.get(getLayoutPosition());
//                    Product product = new Product();
//                    product.setName("Test");
//                    mListener.onUpdateBasket(count, product);
                }
            });
            itemView.setOnClickListener(this);
        }

        public void handleCounter(int position) {
            Product product = mFilterableProducts.get(position);
            mCounter.setCounterValue(mListener.getCounter(product.getName()));
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

    /**  FILTIRARNJE VIEWA */

    /**za filter listu */
//    private LayoutInflater mInflater;
//    private ProductService mProductService;


    /** konstruktor za filter listu*/
//    public ProductAdapter(ProductClickListener listener, LayoutInflater inflater) {
//
//        this.mListener = listener;
//        mInflater = inflater;
//        mDefaultProducts = (JSONArray) mProductService.getAllProducts();
//        mFilteredProducts = mDefaultProducts;
//    }

//    public void filter(String query) {
//        /** trebamo prvo stvoriti instancu objketa Cheeses */
//        mFilteredProducts = new ArrayList<>();
//        /** iteriramo kroz listu sireva i uspoređujemo je li objekt sadrži traženi query */
//        for (String product : mProducts.get(getSelectedProduct().getName())) {
//            if(product.toLowerCase().contains(query.toLowerCase())){ /** da budemo sigurni da prepozna string */
//                mFilteredProducts.add(product);
//            }
//        }
//        notifyDataSetChanged(); /**obavijestimo layout */
//    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                final FilterResults oReturn = new FilterResults();
//                final List<Product> resultProducts = new ArrayList<Product>();
//                if(mProducts == null){
//                    mProducts = mFiltered;
//                    if(constraint != null){
//                        if(mProducts !=null && mProducts.size()>0){
//                            for(final Product product : mProducts){
//                                if (product.getName().toLowerCase().contains(constraint.toString())) {
//                                    resultProducts.add(product);
//                                }
//                            }
//                        }
//                    }
//                    oReturn.values = resultProducts;
//                }
//                return oReturn;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                mProducts = (ArrayList<Product>)results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }


}



