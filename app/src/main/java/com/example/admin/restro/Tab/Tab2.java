package com.example.admin.restro.Tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restro.Hotel;
import com.example.admin.restro.ImageAdapter;
import com.example.admin.restro.MainActivity;
import com.example.admin.restro.R;

import java.util.ArrayList;

/**
 * Created by admin on 13/03/2016.
 */
public class Tab2 extends Fragment {
    GridView gv;
    public static int[] images = {R.drawable.star, R.drawable.call, R.drawable.star, R.drawable.call, R.drawable.star, R.drawable.call};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2, container, false);
        gv = (GridView) v.findViewById(R.id.gridView1);
        gv.setAdapter(new ImageAdapter(getContext()));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}

