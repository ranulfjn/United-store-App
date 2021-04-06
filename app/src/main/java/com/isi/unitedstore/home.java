package com.isi.unitedstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class home extends AppCompatActivity {
    CarouselView carouselView;
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
                    Intent intent3=new Intent(home.this, MainActivity.class);
                    startActivity(intent3);
                    return true;
            }


            return false;
        }
    };
}