package com.sa.restaurant.mvp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.sa.restaurant.mvp.home.model.FavouriteRestaurant;

import java.util.ArrayList;
import java.util.List;


public class DBUser extends DBHelper
{
    private  SQLiteDatabase db;
    private  Context context;
    static final String TABLE_USER="user";
    private static final String COLUMN_USER_ID="user_id";
    private static final String COLUMN_USER_EMAIL="user_email";
    private static final String COLUMN_USER_PASSWORD="user_password";
    private static  final String COLUMN_USER_FACEBOOK_USERID="user_facebook_id";
    private static final String COLUMN_USER_STATUS="user_status";
    private static  final String COLUMN_USER_MOBILE="user_mobile";
    private static  final String COLUMN_USER_NAME="user_name";

    static  final String TABLE_FAV_RESTAURANT ="restaurant";

    private static final String COLUMN_RESTAURANT_ID="restaurant_id";
    private static final String COLUMN_RESTAURANT_NAME="restaurant_name";
    private static final String COLUMN_RESTAURANT_ADDRESS="restaurant_address";
    private static  final String COLUMN_RESTAURANT_PHOTOREFERENCE="restaurant_photoreference";
    private static final String COLUMN_RESTAURANT_FAV_STATUS="fav_status";

    static final String CREATE_TABLE_USER="CREATE TABLE "
            + TABLE_USER
            + "( "
            +COLUMN_USER_ID
            +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_USER_EMAIL
            +" TEXT, "
            +COLUMN_USER_NAME
            +" TEXT, "
            +COLUMN_USER_PASSWORD
            +" TEXT, "
            +COLUMN_USER_MOBILE
            +" TEXT, "
            +COLUMN_USER_STATUS
            +" TEXT, "
            +COLUMN_USER_FACEBOOK_USERID
            +" TEXT );";


    static final String CREATE_TABLE_RESTAURANT="CREATE TABLE "
            + TABLE_FAV_RESTAURANT
            +"( "
            +COLUMN_RESTAURANT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESTAURANT_NAME
            +" TEXT, "
            +COLUMN_RESTAURANT_ADDRESS
            +" TEXT, "
            + COLUMN_RESTAURANT_PHOTOREFERENCE
            +" TEXT, "
            +COLUMN_RESTAURANT_FAV_STATUS
            +" TEXT );";



    public DBUser(Context context) {
        super(context);
        this.context=context;
        db=getDb();
    }
    protected String getSocialUserId(String email)
    {
         String data=null;
        final String projection[]={COLUMN_USER_FACEBOOK_USERID};
        final String selectionClause="("+COLUMN_USER_EMAIL+" = ? )";
        final String[] selectionArgs={email};
        final Cursor cursor=db.query(TABLE_USER,projection,selectionClause,selectionArgs,null,null,null);
        if(cursor!=null && cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do
                {
                    data =cursor.getString(cursor.getColumnIndex(COLUMN_USER_FACEBOOK_USERID));
                    Toast.makeText(context,data,Toast.LENGTH_SHORT).show();

                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return data;
    }

    protected boolean insertSocialUser(String email,String userFacebookId)
    {
        final ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL,email);
        contentValues.put(COLUMN_USER_PASSWORD,0);
        contentValues.put(COLUMN_USER_STATUS,"YES");
        contentValues.put(COLUMN_USER_FACEBOOK_USERID,userFacebookId);
        return db.insert(TABLE_USER,null,contentValues)>0;
    }

    protected boolean insertUser(String email, String password,String mobile,String name ,String status)
    {
       final ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL,email);
        contentValues.put(COLUMN_USER_PASSWORD,password);
        contentValues.put(COLUMN_USER_STATUS,status);
        contentValues.put(COLUMN_USER_MOBILE,mobile);
        contentValues.put(COLUMN_USER_NAME,name);
        contentValues.put(COLUMN_USER_FACEBOOK_USERID,0);
        return db.insert(TABLE_USER,null,contentValues)>0;
    }

    protected boolean checkUser(String email, String password)
    {
        final String[] projection={COLUMN_USER_EMAIL,COLUMN_USER_PASSWORD};
        final String selectionClause="("+COLUMN_USER_EMAIL+" = ? )AND("+COLUMN_USER_PASSWORD+" = ?)";
        final String[]selectionArgs ={email,password};
        final Cursor cursor=db.query(TABLE_USER,projection,selectionClause,selectionArgs,null,null,null);
        return cursor != null && cursor.getCount() > 0;

    }

    protected  void getUserList()
    {
        final  Cursor cursor=db.query(TABLE_USER,null,null,null,null,null,null);
        if(cursor!=null && cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    Toast.makeText(context,"id "+cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))
                            +"email \n"+cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL))
                           +"password \n" +cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
                           +"status\n" +cursor.getString(cursor.getColumnIndex(COLUMN_USER_STATUS))
                           +"user id \n" +cursor.getString(cursor.getColumnIndex(COLUMN_USER_FACEBOOK_USERID)),Toast.LENGTH_LONG).show();
                }while (cursor.moveToNext());

            }
        }
    }
    protected String getUserStatus(String email, String password)
    {
        String data=null;
        final String[] projection={COLUMN_USER_STATUS};
        final String selectionClause="("+COLUMN_USER_EMAIL+" = ? )AND("+COLUMN_USER_PASSWORD+" = ?)";
        final String[] selectionArgs={email,password};
        final Cursor cursor=db.query(TABLE_USER,projection,selectionClause,selectionArgs,null,null,null);
        if(cursor!=null && cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do
                {
                    data =cursor.getString(cursor.getColumnIndex(COLUMN_USER_STATUS));
                    Toast.makeText(context,data,Toast.LENGTH_SHORT).show();

                }while (cursor.moveToNext());
            }
            cursor.close();
        }


        return data;
    }

    protected  boolean addFavRestaurant(String name,String address,String photoReference)
    {
        Log.e("table",CREATE_TABLE_USER);
        Log.e("table",CREATE_TABLE_RESTAURANT);

        final ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_RESTAURANT_NAME,name);
        contentValues.put(COLUMN_RESTAURANT_ADDRESS,address);
        contentValues.put(COLUMN_RESTAURANT_PHOTOREFERENCE,photoReference);
        contentValues.put(COLUMN_RESTAURANT_FAV_STATUS,"YES");
        return db.insert(TABLE_FAV_RESTAURANT,null,contentValues)>0;
    }

    protected  List getFavRestaurantFromDb()
    {
        List favRestaurantList=new ArrayList();
        final  Cursor cursor=db.query(TABLE_FAV_RESTAURANT,null,null,null,null,null,null);
        if(cursor!=null && cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do
                {
                   // favRestaurantList.add()
                    FavouriteRestaurant favouriteRestaurant=new FavouriteRestaurant();
                    favouriteRestaurant.setRestaurantName( cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_NAME)));
                    favouriteRestaurant.setRestaurantAddress(cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_ADDRESS)));
                    favouriteRestaurant.setRestaurantPhotoreference(cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_PHOTOREFERENCE)));
                    favouriteRestaurant.setFavRestaurantStatus(cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_FAV_STATUS)));
                    favRestaurantList.add(favouriteRestaurant);


                }while (cursor.moveToNext());

            }
        }
        cursor.close();
        return favRestaurantList;

    }
protected  String getFavRestaurantAddress(String name)
{

    String data=null;
    final String[] projection={COLUMN_RESTAURANT_ADDRESS};
    final String selectionClause="("+COLUMN_RESTAURANT_NAME+" = ? )";
    final String[] selectionArgs={name};
    final Cursor cursor=db.query(TABLE_FAV_RESTAURANT,projection,selectionClause,selectionArgs,null,null,null);
    if(cursor!=null && cursor.getCount()>0)
    {
        if(cursor.moveToFirst())
        {
            do
            {
                data =cursor.getString(cursor.getColumnIndex(COLUMN_RESTAURANT_ADDRESS));
              //  Toast.makeText(context,data,Toast.LENGTH_SHORT).show();

            }while (cursor.moveToNext());
        }
        cursor.close();
    }


    return data;

}

}
