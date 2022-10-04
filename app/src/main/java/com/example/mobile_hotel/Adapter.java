package com.example.mobile_hotel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context mContext;

    public Adapter(Context mContext, List<Hotel> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    List<Hotel> maskList;
    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getID();
    }
    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return null;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = View.inflate(mContext,R.layout.item_hotel,null);

        TextView Country = v.findViewById(R.id.Country);
        TextView City = v.findViewById(R.id.City);
        TextView Title = v.findViewById(R.id.Title);
        TextView NumberOfStars = v.findViewById(R.id.NumberOfStars);
        ImageView Image = v.findViewById(R.id.Image);

        Hotel mask = maskList.get(position);
        Title.setText(mask.getTitle());
        NumberOfStars.setText(Integer.toString(mask.getNumberOfStars()));

        Image.setImageBitmap(getUserImage(mask.getImage()));

        return v;
    }

}