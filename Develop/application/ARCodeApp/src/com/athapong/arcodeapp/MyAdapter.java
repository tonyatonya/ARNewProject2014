package com.athapong.arcodeapp;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.athapong.arcodeapp.Item;
 
public class MyAdapter extends ArrayAdapter<Item> {
 
        private final Context context;
        private final ArrayList<Item> itemsArrayList;
        ImageView logo ;
        
        public MyAdapter(Context context, ArrayList<Item> itemsArrayList) {
 
            super(context, R.layout.row, itemsArrayList);
 
            this.context = context;
            this.itemsArrayList = itemsArrayList;
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            // 1. Create inflater 
            LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.row, parent, false);
 
            // 3. Get the two text view from the rowView
            TextView labelView = (TextView) rowView.findViewById(R.id.label);
            TextView valueView = (TextView) rowView.findViewById(R.id.value);
            logo =(ImageView) rowView.findViewById(R.id.logoImg);
 
            // 4. Set the text for textView 
            labelView.setText(itemsArrayList.get(position).getTitle());
            valueView.setText(itemsArrayList.get(position).getBusUrl());
            Bitmap imageLogo = BitmapFactory.decodeFile(itemsArrayList.get(position).getLogoPath());
            logo.setImageBitmap(imageLogo);
            // 5. return rowView
            return rowView;
        }
}
