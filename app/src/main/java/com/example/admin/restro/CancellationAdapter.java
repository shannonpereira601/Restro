package com.example.admin.restro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PAREKH on 14-04-2016.
 */

public class CancellationAdapter extends BaseAdapter {
    private Context mContext;
    private String[] result;
    private Integer[] imageid = new Integer[20];
    int imagecount;

    public CancellationAdapter(Context context, String[] text, Integer[] Images) {
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
            if(imageid[position]==R.drawable.red) {
                holder.text1.setText("Capacity: " + result[position]);
            }
            else if(imageid[position]==R.drawable.green)
            {
                holder.text1.setText("Available");
            }


            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        return convertView;

    }

    static class ViewHolder {
        TextView text1;
        ImageView image;

    }
}