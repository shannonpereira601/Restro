package com.example.admin.restro.Tab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.restro.ImageAdapter;
import com.example.admin.restro.R;

/**
 * Created by admin on 14/03/2016.
 */
public class Tab3 extends Fragment {
    GridView gv;
    int count;
    public static int[] images = {R.drawable.star, R.drawable.call, R.drawable.star, R.drawable.call, R.drawable.star, R.drawable.call};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);
        final ImageView table = (ImageView)v.findViewById(R.id.table);
        final int red = Color.parseColor("#b71c1c");
        final int green = Color.parseColor("#4caf50");
        table.setColorFilter(red);
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count % 2 == 0) {
                    table.setColorFilter(red);
                }
                else{
                    table.setColorFilter(green);
                }
            }
        });

        return v;
    }
}
