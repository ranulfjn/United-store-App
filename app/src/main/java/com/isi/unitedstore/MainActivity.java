package com.isi.unitedstore;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {

    ArrayList<CustomArrayList> menu = new ArrayList<>();
    ArrayList<Order> order= new ArrayList<>();



    ListView listView;
    GridView gridview;
    private DataBaseHelper db;
    int qty=1;
    FloatingActionButton see_order;
    customAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


      //  listView = findViewById(R.id.listView);
        gridview = findViewById(R.id.gridview2);

        db = new DataBaseHelper(MainActivity.this);
        try {
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDatabase();
        menu= db.getAppCategoryDetail();
        customAdapter = new customAdapter(MainActivity.this, menu);
        gridview.setAdapter(customAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);





        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Activity", "Click");


                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.setContentView(R.layout.custom_dialog);

                TextView customText = (TextView) dialog.findViewById(R.id.customText);
                customText.setText(menu.get(position).getName());

                TextView customText2 = (TextView) dialog.findViewById(R.id.customText2);
                customText2.setText(String.valueOf(menu.get(position).getPrice())+"$");

                ImageView image = (ImageView) dialog.findViewById(R.id.customImage);
                image.setImageBitmap(menu.get(position).getImage());

                TextView quantity=(TextView) dialog.findViewById(R.id.qty);



                Button plus = (Button) dialog.findViewById(R.id.plus);
                if(qty==0||qty!=0){
                    qty=1;
                    plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            qty++;
                            quantity.setText(String.valueOf(qty));
                        }
                    });
                }


                Button minus = (Button) dialog.findViewById(R.id.minus);
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(qty>0){
                            qty--;
                            quantity.setText(String.valueOf(qty));
                        }
                    }
                });


                Button add = (Button) dialog.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (qty > 0) {
                            int id =menu.get(position).getId();
                            Bitmap image=menu.get(position).getImage();
                            String name=menu.get(position).getName();
                            int price=menu.get(position).getPrice();
                            order.add(new Order(id,null,name,price,qty));
                            Toast.makeText(MainActivity.this,""+name+" added to cart",Toast.LENGTH_SHORT).show();
                        }

                        }

                });

                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }

        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent=new Intent(MainActivity.this, home.class);
                    startActivity(intent);
                    return true;
                case R.id.store:
                    Intent intent1=new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent1);

                    return true;
                case R.id.cartBottom:
                    Intent intent2=new Intent(MainActivity.this, OrderPage.class);
                    Bundle args = new Bundle();
                    args.putSerializable("Order_list",(Serializable)order);
                    intent2.putExtra("bundel",args);
                    startActivity(intent2);
                    return true;
                case R.id.profile:
                    Intent intent3=new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent3);
                    return true;
            }

            return false;
        }
    };
}