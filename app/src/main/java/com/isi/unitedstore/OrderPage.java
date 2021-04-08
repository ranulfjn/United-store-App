package com.isi.unitedstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class OrderPage extends AppCompatActivity implements Serializable {
    int finalTotal=0,price=0;
    ArrayList<Order> order= new ArrayList<>();

    ArrayList<cart> Cart = new ArrayList<>();
    ListView list;
    ArrayList<Integer>  priceArray= new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    private DataBaseHelper db;
    OrderAdapter orderAdapter;
    Button shop , checkout, remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        remove = findViewById(R.id.remove);
        checkout = findViewById(R.id.checkout);
        shop=findViewById(R.id.shop);

       // Intent intent = getIntent();
      //  Bundle args = intent.getBundleExtra("bundel");
        // order = (ArrayList<Order>) args.getSerializable("Order_list");

        list= findViewById(R.id.listv);
        list.setClickable(true);
        db = new DataBaseHelper(OrderPage.this);
        try {
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDatabase();

        if(Cart.size() == 0){
            TextView empty = findViewById(R.id.empty);
            empty.setText("Cart is empty");
        }

        Cart = db.getCart();
        for(int i=0;i<Cart.size();i++){

            price+=Cart.get(i).getQty()*Cart.get(i).getPrice();

            Log.e("name",Cart.get(i).getPrice()+"");
        }


        ///SELECT__________________________________


///____________________________________________
       // for(int i=0;i<order.size();i++){

          //  price+=order.get(i).getQty()*order.get(i).getPrice();
       // }

        TextView fin=findViewById(R.id.finalTotal);
        fin.setText("Total :"+String.valueOf(price));

        orderAdapter = new OrderAdapter(OrderPage.this, Cart); //order
        list.setAdapter(orderAdapter);

list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(OrderPage.this ,"click",Toast.LENGTH_SHORT);
    }
});


        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // db.removeItem("");
                Intent intent = new Intent(OrderPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.removeItem();
                Intent intent = new Intent(OrderPage.this, checkout.class);
                startActivity(intent);
            }
        });











    }
}