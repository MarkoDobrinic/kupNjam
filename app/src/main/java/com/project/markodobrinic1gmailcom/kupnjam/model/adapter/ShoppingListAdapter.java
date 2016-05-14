package com.project.markodobrinic1gmailcom.kupnjam.model.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.markodobrinic1gmailcom.kupnjam.R;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Header;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Item;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.ShoppingItem;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.ShoppingProductDetail;
import com.project.markodobrinic1gmailcom.kupnjam.ui.MainMaps;
import com.project.markodobrinic1gmailcom.kupnjam.ui.ProductListUser;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by marko.dobrinic1@gmail.com on 30.4.2016..
 */
public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.Holder>{

    private LayoutInflater mInflater;
    private List<Item> mShoppingItems = new ArrayList<>();

    public ShoppingListAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case Item.HEADER_TYPE:
                return new Holder(mInflater.inflate(R.layout.item_header, parent, false));
            default:
                return new Holder(mInflater.inflate(R.layout.item_shopping, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if(isHeaderType(position)) {
            bindHeader(holder, position);
        } else {
            bindShoppingItem(holder, position);
        }
    }

    private void bindShoppingItem(Holder holder, int position) {

        ShoppingItem item = (ShoppingItem) mShoppingItems.get(position);
        TextView iteName = (TextView) holder.itemView.findViewById(R.id.itemName);
        TextView itemPrice = (TextView) holder.itemView.findViewById(R.id.itemPrice);

        ShoppingProductDetail detail = item.getDetail();
        iteName.setText(detail.getProductName());
        itemPrice.setText(Double.toString(detail.getPrice()) + "kn");
    }

    private void bindHeader(Holder holder, int position) {

        final Header header = (Header) mShoppingItems.get(position);
        final  ImageView icon = (ImageView) holder.itemView.findViewById(R.id.itemLogo);
        final ImageView location = (ImageView) holder.itemView.findViewById(R.id.itemLocation);

        final ViewTreeObserver viewTreeObserver = location.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver observer = location.getViewTreeObserver();
                if(observer.isAlive()) {
                    observer.removeOnGlobalLayoutListener(this);

                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) icon.getLayoutParams();
                    layoutParams.leftMargin = location.getMeasuredWidth();
                }

            }
        });

        icon.setImageDrawable(ContextCompat.getDrawable(icon.getContext(), header.getResourceId()));

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.itemLocation:
                        Intent intent = new Intent(v.getContext(), MainMaps.class);
                        v.getContext().startActivity(intent);
                        break;
                    default:
                }
            }
        });

//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog dialog = new AlertDialog.Builder(v.getContext())
//                        .setTitle("Lokacije")
//                        .setView(R.layout.store_location)
//                        .setCancelable(true)
//                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//
//                        }).create();
//                dialog.show();
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return mShoppingItems.size();
    }

    public void addItem(Item item) {
        mShoppingItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return isHeaderType(position) ? 0 : 1;
    }

    private boolean isHeaderType(int position) {
        Item item = mShoppingItems.get(position);
        return item.getType() == Item.HEADER_TYPE ? true: false;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }
}