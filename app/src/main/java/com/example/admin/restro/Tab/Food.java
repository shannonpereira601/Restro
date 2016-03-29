package com.example.admin.restro.Tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restro.FoodAdapter;
import com.example.admin.restro.R;

/**
 * Created by admin on 14/03/2016.
 */
public class Food extends Fragment {
    GridView gv;
    TextView tv;
    Context context = getContext();
    Integer a = R.drawable.green;
    public static Integer[] images = {R.drawable.chicken, R.drawable.chutney, R.drawable.custard, R.drawable.falooda, R.drawable.lasagna, R.drawable.roti,R.drawable.waiter};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_food, container, false);
        gv = (GridView) v.findViewById(R.id.gridView);
       /* int x = a.intValue();
        Integer[] trial = new Integer[3];
        for(int i = 0; i<3 ; i++) {
            trial[i] = getResources().getIdentifier("green", "drawable", context.getPackageName());
        }
        //int[] oldarray;
        Integer[] newarray = new Integer[oldarray.length];
        */
        int length = 10;
        Integer[] green = new Integer[length];

        for(int i=0;i<length;i++)
        {
            green[i] = R.drawable.green;
        }

        gv.setAdapter(new FoodAdapter(getContext(),images));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getContext(), "something" + a +"some" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
