package com.isi.unitedstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class home extends AppCompatActivity {
    CarouselView carouselView;
    String session , name;

    private static final int MENU_ITEM_ITEM1 = 1;
    int[] sampleImages = {R.drawable.adidas, R.drawable.adidas2 , R.drawable.adidas3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.home);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        session  = getIntent().getStringExtra("session");


    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent=new Intent(home.this, home.class);
                    startActivity(intent);
                    return true;
                case R.id.store:
                    Intent intent1=new Intent(home.this, MainActivity.class);
                    startActivity(intent1);

                    return true;
                case R.id.cartBottom:
                    Intent intent2=new Intent(home.this, OrderPage.class);
                    startActivity(intent2);
                    return true;
                case R.id.profile:

                    Intent intentLogin = new Intent(home.this, Login.class);
                    startActivity(intentLogin);
//                    if(session!= null){
//                       name= session;
//                    }
//
//                    PopupMenu popup = new PopupMenu(home.this, findViewById(R.id.profile));
//                    MenuInflater inflater = popup.getMenuInflater();
//                    inflater.inflate(R.menu.profile, popup.getMenu());
//                    popup.show();
                    return true;
            }


            return false;
        }

        public boolean onCreateOptionsMenu(Menu menu) {
            menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, session);
            return true;
        }


        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.logout:
                    SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.apply();
                    finish();
                    Toast.makeText(home.this,"Logged Out Succesfully",Toast.LENGTH_SHORT);
                    return true;
                case R.id.login:
                   Intent intent = new Intent(home.this,Login.class);
                   startActivity(intent);
                    return true;

                default:
                    return false;
            }
        }


    };

}