package com.example.mobile_hotel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context mContext;
    List<Hotel> list_h;
    String Img="";

    public Adapter(Context mContext, List<Hotel> hotelList) {
        this.mContext = mContext;
        this.list_h = hotelList;
    }


    @Override
    public int getCount() {
        return list_h.size();
    }

    @Override
    public Object getItem(int i) {
        return list_h.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return list_h.get(i).getID();
    }

    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else {
            return BitmapFactory.decodeResource(Adapter.this.mContext.getResources(), R.drawable.photo);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_hotel, null);

        TextView Country = v.findViewById(R.id.tx_Country);
        TextView City = v.findViewById(R.id.tx_City);
        TextView Title = v.findViewById(R.id.tx_Title);
        TextView NumberOfStars = v.findViewById(R.id.tx_NumberOfStars);
        ImageView Image = v.findViewById(R.id.Img);

        Hotel mask = list_h.get(position);
        Country.setText(mask.getCountry());
        City.setText(mask.getCity());
        Title.setText(mask.getTitle());
        NumberOfStars.setText(Integer.toString(mask.getNumberOfStars()));
        Image.setImageBitmap(getUserImage(mask.getImage()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,update_data.class);
                intent.putExtra("Hotel",mask);
                mContext.startActivity(intent);
            }
        });

        return v;
    }

}