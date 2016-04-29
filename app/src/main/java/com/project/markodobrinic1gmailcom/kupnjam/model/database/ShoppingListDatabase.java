package com.project.markodobrinic1gmailcom.kupnjam.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.project.markodobrinic1gmailcom.kupnjam.model.callback.ProductFetchListener;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Constants;
import com.project.markodobrinic1gmailcom.kupnjam.model.helper.Utilities;
import com.project.markodobrinic1gmailcom.kupnjam.model.pojo.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko.dobrinic1@gmail.com on 29.4.2016..
 */
public class ShoppingListDatabase extends SQLiteOpenHelper {

    /**
     * tagging neccessary data for inspection
     **/
    private static final String TAG = ProductDatabase.class.getSimpleName();
    private SQLiteDatabase mDb;

    /**  **/
    public ShoppingListDatabase(Context context) {
        super(context, Constants.DATABASE.DB_NAME, null, Constants.DATABASE.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.DATABASE.CREATE_TABLE_BASKET);
        } catch (SQLException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DATABASE.DROP_QUERY_BASKET);
        this.onCreate(db);
    }

    /**  **/
    public void  addProductToList(Product product){

        Log.d(TAG, "Got Value in THE USER LIST-> " + product.getId() + " " + product.getName());

        if(!checkId(Integer.toString(product.getId()))) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.DATABASE.ID, product.getId());
            values.put(Constants.DATABASE.NAME, product.getName());
            values.put(Constants.DATABASE.DISC_PRICE, Double.toString(product.getDiscounted_price()));
            values.put(Constants.DATABASE.PRICE, Double.toString(product.getPrice()));
            values.put(Constants.DATABASE.STORE_ID, product.getStore_id());
            values.put(Constants.DATABASE.START_DATE, product.getStart_date());
            values.put(Constants.DATABASE.END_DATE, product.getEnd_date());
            values.put(Constants.DATABASE.DESCRIPTION, product.getDescription());
            values.put(Constants.DATABASE.IMAGE_URL, product.getImage());
            values.put(Constants.DATABASE.IMAGE, Utilities.getPictureByteOfArray(product.getPicture()));

            try {
                db.insert(Constants.DATABASE.TABLE_BASKET, null, values);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            db.close();
        }
    }

    public void fetchProducts(ProductFetchListener listener) {
        ProductFetcher fetcher = new ProductFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }


    public class ProductFetcher extends Thread {

                private final ProductFetchListener mListener;
                private final SQLiteDatabase mDb;

                public ProductFetcher(ProductFetchListener listener, SQLiteDatabase db) {
                    mListener = listener;
                    mDb = db;
                }

                @Override
                public void run() {
                    Cursor cursor = mDb.rawQuery(Constants.DATABASE.GET_BASKET_QUERY, null);

                    final List<Product> productList = new ArrayList<>();

                    if (cursor.getCount() > 0) {

                        if (cursor.moveToFirst()) {
                            do {
                                Product product = new Product();
                                product.setFromDatabase(true);
                                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.ID))));
                                product.setName(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.NAME)));
                                product.setDiscounted_price(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.DISC_PRICE))));
                                product.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.PRICE))));
                                product.setStore_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.STORE_ID))));
                                product.setStart_date(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.START_DATE)));
                                product.setEnd_date(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.END_DATE)));
                                product.setDescription(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.DESCRIPTION)));
                                product.setPicture(Utilities.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(Constants.DATABASE.IMAGE))));
                                product.setImage(cursor.getString(cursor.getColumnIndex(Constants.DATABASE.IMAGE_URL)));

                                productList.add(product);
                                publishProduct(product);
                            } while (cursor.moveToNext());
                        }
                    }
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onDeliverAllProducts(productList);
                            mListener.onHideDialog();
                        }
                    });
                }

                public void publishProduct(final Product product) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onDeliverProduct(product);
                        }
                    });
                }
            }

    public boolean checkId(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.DATABASE.TABLE_BASKET,
                new String[] { Constants.DATABASE.ID },
                Constants.DATABASE.ID + " = ? ",
                new String[] {id},
                null, null, null, null);

        if(cursor.moveToFirst())

            return true; //row exists
        else
            return false;
    }



}
