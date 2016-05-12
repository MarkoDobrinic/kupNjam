package com.project.markodobrinic1gmailcom.kupnjam.model.helper;

/**
 * Created by marko.dobrinic1@gmail.com on 23.4.2016..
 */
public class Constants {

    /** URLs **/
    public static final class HTTP {
        public static final String IMAGE_URL = "https://kupnjam-abelso.rhcloud.com/images/";
        public static final String BASE_URL = "https://kupnjam-abelso.rhcloud.com";
    }


    public static final class USERS{

        public static final String BASE_URL = "https://kupnjam-abelso.rhcloud.com/";
        public static final String REGISTER_OPERATION = "register";
        public static final String LOGIN_OPERATION = "login";
        //public static final String CHANGE_PASSWORD_OPERATION = "chgPass";

        public static final String SUCCESS = "success";
        public static final String FAILURE = "failure";
        public static final String IS_LOGGED_IN = "isLoggedIn";

        public static final String NAME = "name";
        public static final String EMAIL = "email";
       public static final String UNIQUE_ID = "unique_id";

        public static final String TAG = "kupNjam_USERS";
    }

    public static final class REFERENCE {
        public static final String PRODUCT = Config.PACKAGE_NAME + "product";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "com.project.markodobrinic1gmailcom.kupnjam.";
    }


    /** SQLlite local DB constants **/
    public static final class DATABASE {

        public static final String DB_NAME = "products";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "product";

        public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;

        public static final String GET_PRODUCTS_QUERY = "SELECT * FROM " + TABLE_NAME;

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DISC_PRICE = "discounted_price";
        public static final String PRICE = "price";
        public static final String IMAGE_URL = "image_url";
        public static final String IMAGE = "image";
        public static final String STORE_ID = "product_id";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String DESCRIPTION = "description";

        public static final String GET_ID = "SELECT " + ID +  " FROM " + TABLE_NAME ;
        //public static final String[] ALL_FIELDS = new String[] {ID, NAME};


    //PRIMARY KEY
        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + ID + " INTEGER PRIMARY KEY NOT NULL, " +
                NAME + " TEXT not null, " +
                DISC_PRICE + " TEXT not null, " +
                PRICE + " TEXT not null, " +
                STORE_ID + " INTEGER not null, " +
                START_DATE + " TEXT not null, " +
                END_DATE + " TEXT not null, " +
                DESCRIPTION + " TEXT not null, " +
                IMAGE_URL + " TEXT not null, " +
                IMAGE + " blob not null)";
    }



}
