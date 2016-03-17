package com.example.admin.restro.Tab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restro.ImageAdapter;
import com.example.admin.restro.ImageAdapter2;
import com.example.admin.restro.R;

/**
 * Created by admin on 14/03/2016.
 */
public class Tab3 extends Fragment {
    GridView gv;
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_3, container, false);
        gv = (GridView) v.findViewById(R.id.gridView);
        Integer[] values = {
                R.drawable.green,R.drawable.green,R.drawable.green,
        };
        gv.setAdapter(new ImageAdapter2(getContext(),values));

       // int number = Integer.parseInt(editText.getText().toString());

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
