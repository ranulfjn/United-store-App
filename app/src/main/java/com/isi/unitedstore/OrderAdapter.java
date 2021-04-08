package com.isi.unitedstore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter implements Serializable {
    Context context;
    ArrayList<cart>listArray;


    public OrderAdapter(@NonNull Context context, ArrayList<cart>listArray) {
        super(context, R.layout.order_custom);

        this.context=context;
        this.listArray=listArray;
    }

    @Override
    public int getCount() {
        return listArray.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.order_custom,parent,false);



        TextView name1=rowView.findViewById(R.id.name);
        name1.setText(listArray.get(position).getName());

        TextView qty=rowView.findViewById(R.id.qty);
        qty.setText(String.valueOf(listArray.get(position).getQty()));

        //TextView price1=rowView.findViewById(R.id.price);
       // price1.setText(String.valueOf(listArray.get(position).getPrice()));

       ArrayList<Integer>  priceArray= new ArrayList<>();
        int price=listArray.get(position).getQty()*listArray.get(position).getPrice();
        priceArray.add(price);

        int finalTotal=0;
        for(int i=0;i<priceArray.size();i++){
         finalTotal+=priceArray.get(i);
        }

        TextView total=rowView.findViewById(R.id.total);
        total.setText(String.valueOf(price)+"$");


        Log.e("tot",finalTotal+"$");


        ImageView image1=rowView.findViewById(R.id.image);
        image1.setImageBitmap(listArray.get(position).getImage());
        Button btn = rowView.findViewById(R.id.remove);
        
        return rowView;
    }

}
