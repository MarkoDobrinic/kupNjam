package com.project.markodobrinic1gmailcom.kupnjam.model.pojo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.annotations.Expose;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by marko.dobrinic1@gmail.com on 23.4.2016..
 */
public class Product implements Serializable {

    public byte[] imageByteArray;
    private Bitmap picture;
    private boolean isFromDatabase;


    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private double discounted_price;

    @Expose
    private double price;

    @Expose
    private String image;

    @Expose
    private int store_id;

    @Expose
    private String start_date;

    @Expose
    private String end_date;

    @Expose
    private String description;

    private boolean checked;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(double discounted_price) {
        this.discounted_price = discounted_price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public boolean isFromDatabase() {
        return isFromDatabase;
    }

    public void setFromDatabase(boolean fromDatabase) {
        isFromDatabase = fromDatabase;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {

        out.writeObject(id);
        out.writeObject(name);
        out.writeObject(discounted_price);
        out.writeObject(price);
        out.writeObject(image);
        out.writeObject(store_id);
        out.writeObject(start_date);
        out.writeObject(end_date);
        out.writeObject(description);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {

        id = (Integer) in.readObject();
        name = (String) in.readObject();
        discounted_price = (Double) in.readObject();
        price = (Double) in.readObject();
        image = (String) in.readObject();
        store_id = (Integer) in.readObject();
        start_date = (String) in.readObject();
        end_date = (String) in.readObject();
        description = (String) in.readObject();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        picture = BitmapFactory.decodeByteArray(bitmapBytes, 0,
                bitmapBytes.length);
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
