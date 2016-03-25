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
import android.widget.Toast;

import com.example.admin.restro.ReservationAdapter;
import com.example.admin.restro.R;

/**
 * Created by admin on 13/03/2016.
 */
public class Reservation extends Fragment {
    public static Reservation getInstance(int position) {

        Reservation myFragmeent = new Reservation();
        Bundle args = new Bundle();
        args.putInt("number",position);
        myFragmeent.setArguments(args);
        return myFragmeent;
    }
    GridView gv;
    Integer[] table;
    Context context = getContext();
    //public static Integer[] images = {R.drawable.chicken, R.drawable.chutney, R.drawable.custard, R.drawable.falooda, R.drawable.lasagna, R.drawable.roti,R.drawable.waiter};
    // public static Integer[] images = {R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green};
    public static String[] counttable = {"4", "5", "2", "3", "4", "5", "2"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_table, container, false);
        gv = (GridView) v.findViewById(R.id.gridView1);
        Bundle bundle = getArguments();
        int x = bundle.getInt("number");
        String[] tables = getResources().getStringArray(R.array.tablevalues);
        int length = Integer.parseInt(tables[x]);
      //  int length = 7;
        table = new Integer[length];

        for (int i = 0; i < length; i++) {
            table[i] = R.drawable.green;
        }
        gv.setAdapter(new ReservationAdapter(getContext(), counttable, table));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (table[position] == R.drawable.green) {
                    table[position] = R.drawable.red;
                    gv.setAdapter(new ReservationAdapter(getContext(), counttable, table));
                } else {
                    table[position] = R.drawable.green;
                    gv.setAdapter(new ReservationAdapter(getContext(), counttable, table));
                }
            }
        });
        return v;
    }
}

