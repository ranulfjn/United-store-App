package com.isi.unitedstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper implements Serializable {
    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private static final String DATABASE_NAME = "united.db";
    public final static String DATABASE_PATH = "/data/data/com.isi.unitedstore/databases/";
    public static final int DATABASE_VERSION = 1;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;

    }




    //Create a empty database on the system
    public void createDatabase() throws IOException
    {

        boolean dbExist = checkDataBase();

        if(dbExist)
        {
            Log.v("DB Exists", "db exists");
            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        }

        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }

    }
    //Check database already exist or not
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
        }
        return checkDB;
    }
    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() throws IOException
    {

        InputStream mInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[2024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    //delete database
    public void db_delete()
    {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if(file.exists())
        {
            file.delete();
            System.out.println("delete database file.");
        }
    }
    //Open database
    public void openDatabase() throws SQLException
    {
        String myPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase()throws SQLException
    {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
        {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }

    }

    public ArrayList<CustomArrayList> getAppCategoryDetail() {
                ArrayList<CustomArrayList> al_data = new ArrayList<>();
        try {


            final String TABLE_NAME = "products";

            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            //String[] data = null;
         ;

            if (cursor.moveToFirst()) {
                do {
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    //String description=cursor.getString(cursor.getColumnIndex("des"));
                    int price=cursor.getInt(cursor.getColumnIndex("price"));
                    int id=cursor.getInt(cursor.getColumnIndex("id"));

                    byte[] image=cursor.getBlob(cursor.getColumnIndex("image"));
                    Bitmap img = BitmapFactory.decodeByteArray(image,0,image.length);


                    al_data.add(new CustomArrayList(id,img,name,price,null));
                   // Log.e("Working", cursor.getString(cursor.getColumnIndex("title"))+"");
                    // get the data into array, or class variable
                } while (cursor.moveToNext());
            }
            cursor.close();
            return al_data;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // insert to db Register
    public String registerUser(String name ,String password ,String email ) {
        try {

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("email", email);
            values.put("password", password);

            int val = (int) database.insert("users", null, values);
            if(val != -1){

                Log.e("Success","Added User to DB "+ values);
            }
            Log.e("Success","DB "+ val);
            database.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //Test
    public void getDetails() {
        ArrayList<CustomArrayList> al_data = new ArrayList<>();
        try {


            final String TABLE_NAME = "users";

            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            //String[] data = null;
            ;

            if (cursor.moveToFirst()) {
                do {
                    String name=cursor.getString(cursor.getColumnIndex("email"));
                  //  al_data.add(new CustomArrayList(id,img,name,price,description));
                     Log.e("Working", cursor.getString(cursor.getColumnIndex("email"))+"");
                    // get the data into array, or class variable
                } while (cursor.moveToNext());
            }
            cursor.close();
            //return al_data;

        } catch (Exception e) {
            e.printStackTrace();
        }

      //  return null;
    }


    public boolean emailExists(String email){

        try {


            final String TABLE_NAME = "users";

            String selectQuery = "SELECT  email FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String emailDb=cursor.getString(cursor.getColumnIndex("email"));
                    if(emailDb.equals(email)){
                        return true;
                    }
                    // get the data into array, or class variable
                } while (cursor.moveToNext());
            }

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

            return false;
    }

    public boolean credCheck(String email , String password){

        try {


            final String TABLE_NAME = "users";

            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    String emailDb=cursor.getString(cursor.getColumnIndex("email"));
                    String passwordDb=cursor.getString(cursor.getColumnIndex("password"));
                    if(emailDb.equals(email) && passwordDb.equals(password)){
                        return true;
                    }
                    // get the data into array, or class variable
                } while (cursor.moveToNext());
            }

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public void addCart(String name , String product ,int id ,int qty, Bitmap image, int price){

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("product", product);
            values.put("id", id);
            values.put("qty", qty);
            values.put("price", price);
            values.put("image", outputStream.toByteArray());

            int val = (int) database.insert("cart", null, values);
            if(val != -1){

                Log.e("Success","Added to cart "+ values);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ArrayList<cart> getCart() {
        ArrayList<cart> cartData = new ArrayList<>();
        try {


            final String TABLE_NAME = "cart";

            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            //String[] data = null;
            ;

            if (cursor.moveToFirst()) {
                do {
                    String userName=cursor.getString(cursor.getColumnIndex("name"));
                    String name=cursor.getString(cursor.getColumnIndex("product"));
                    int price=cursor.getInt(cursor.getColumnIndex("price"));
                    int id=cursor.getInt(cursor.getColumnIndex("id"));
                    int qty = cursor.getInt(cursor.getColumnIndex("qty"));


                    byte[] image=cursor.getBlob(cursor.getColumnIndex("image"));
                    Bitmap img = BitmapFactory.decodeByteArray(image,0,image.length);


                    cartData.add(new cart(id,userName,img,name,price,qty));
                    // Log.e("Working", cursor.getString(cursor.getColumnIndex("title"))+"");
                    // get the data into array, or class variable
                } while (cursor.moveToNext());
            }
            cursor.close();
            return cartData;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void removeItem(){
        String TABLE_NAME= "cart";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        Log.e("Removed", "from DB");

    }

}