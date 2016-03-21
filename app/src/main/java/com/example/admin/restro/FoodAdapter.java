package com.example.admin.restro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 14/03/2016.
 */
public class FoodAdapter extends BaseAdapter {
    private Context mContext;
    Integer[] something = new Integer[10];
    public FoodAdapter(Context c, Integer[] integer) {
        mContext = c;
        something = integer;
    }

    public int getCount() {
        return something.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
       /* ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            /*
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(something[position]);
        return imageView;*/
        ImageView food;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.foodlist, null);
            food = (ImageView) convertView
                    .findViewById(R.id.imageView1);

            // if it's not recycled, initialize some attributes
            food.setImageResource(something[position]);
            food.setPadding(3, 3, 3, 3);
            //int h = mContext.getResources().getDisplayMetrics().densityDpi;
            // holder.image.setLayoutParams(new LayoutParams(h-50,h-50));
            // holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
            // holder.image.setPadding(20, 20, 20, 20);
            // convertView.setLayoutParams(new GridView.LayoutParams(Utils
            // .getLayoutParameter(), Utils.getLayoutParameter()+50));

            food = new ImageView(mContext);
        } else {

            food = (ImageView)convertView.getTag();

        }

        return convertView;
    }

    // references to our images

}