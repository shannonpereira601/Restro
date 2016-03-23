package com.example.admin.restro;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 14/03/2016.
 */
public class ReservationAdapter extends BaseAdapter {
    private Context mContext;
    private String[] result;
    private Integer[] imageid = new Integer[20];
    int imagecount;

    public ReservationAdapter(Context context, String[] text, Integer[] Images) {
        mContext = context;
        imageid = Images;
        result = text;
    }

    public int getCount() {
        return imageid.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {

       /* ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageid[position]);
        return imageView;*/
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.imagelist, null);
            holder = new ViewHolder();
            holder.text1 = (TextView) convertView
                    .findViewById(R.id.textView1);

            holder.image = (ImageView) convertView
                    .findViewById(R.id.imageView1);

            // if it's not recycled, initialize some attributes
            holder.image.setImageResource(imageid[position]);
            if(imageid[position]==R.drawable.green) {
                holder.text1.setText("Capacity: " + result[position]);
            }
            else if(imageid[position]==R.drawable.red)
            {
                holder.text1.setText("Booked");
            }
            //int h = mContext.getResources().getDisplayMetrics().densityDpi;
            // holder.image.setLayoutParams(new LayoutParams(h-50,h-50));
            // holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
            // holder.image.setPadding(20, 20, 20, 20);
            // convertView.setLayoutParams(new GridView.LayoutParams(Utils
            // .getLayoutParameter(), Utils.getLayoutParameter()+50));

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }
        /*
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageid[position]==R.drawable.green)
                {
                    holder.image.setImageResource(R.drawable.red);
                    holder.text1.setText("Booked");
                }
                if(imageid[position]==R.drawable.red)
                {
                    holder.image.setImageResource(R.drawable.green);
                    holder.text1.setText("Capacity: " + result[position]);
                   // imageid[position]=R.drawable.green;
                }*/

               /* if (imagecount % 2 == 0) {
                    holder.image.setImageResource(R.drawable.red);
                    holder.text1.setText("Booked");
                } else {
                    holder.image.setImageResource(R.drawable.green);
                    holder.text1.setText(result[position]);
                }
            }
        });*/

        return convertView;

    }

    static class ViewHolder {
        TextView text1;
        ImageView image;

    }
}