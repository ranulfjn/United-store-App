package com.isi.unitedstore;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderPage extends AppCompatActivity implements Serializable {
    int finalTotal=0,price=0;
    ArrayList<Order> order= new ArrayList<>();
    ListView list;
    ArrayList<Integer>  priceArray= new ArrayList<>();
    OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundel");
         order = (ArrayList<Order>) args.getSerializable("Order_list");

        list= findViewById(R.id.listv);

        for(int i=0;i<order.size();i++){

            price+=order.get(i).getQty()*order.get(i).getPrice();
        }

        TextView fin=findViewById(R.id.finalTotal);
        fin.setText("Total :"+String.valueOf(price));

        orderAdapter = new OrderAdapter(OrderPage.this, order);
        list.setAdapter(orderAdapter);




    }
}