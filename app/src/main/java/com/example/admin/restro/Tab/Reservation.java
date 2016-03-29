package com.example.admin.restro.Tab;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.restro.ReservationAdapter;
import com.example.admin.restro.R;

/**
 * Created by admin on 13/03/2016.
 */
public class Reservation extends Fragment {
    public static Reservation getInstance(int position) {

        Reservation myFragmeent = new Reservation();
        Bundle args = new Bundle();
        args.putInt("number", position);
        myFragmeent.setArguments(args);
        return myFragmeent;

    }

    GridView gv;
    Integer[] table;
    Integer[] copy;
    int count;
    Context context = getContext();
    //public static Integer[] images = {R.drawable.chicken, R.drawable.chutney, R.drawable.custard, R.drawable.falooda, R.drawable.lasagna, R.drawable.roti,R.drawable.waiter};
    // public static Integer[] images = {R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green};
    public static String[] counttable = {"4", "5", "2", "3", "4", "5", "2"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_table, container, false);
        gv = (GridView) v.findViewById(R.id.gridView1);
        Bundle bundle = getArguments();
        final int[] x = {bundle.getInt("number")};
        String[] tables = getResources().getStringArray(R.array.tablevalues);
        int length = Integer.parseInt(tables[x[0]]);
        //  int length = 7;
        table = new Integer[length];

        for (int i = 0; i < length; i++) {
            table[i] = R.drawable.green;
        }

        gv.setAdapter(new ReservationAdapter(getContext(), counttable, table));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                if (table[position] == R.drawable.green) {
                    new MaterialDialog.Builder(getContext())
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    table[position] = R.drawable.red;
                                    gv.setAdapter(new ReservationAdapter(getContext(), counttable, table));
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    // TODO
                                }
                            }).title("Confirmation")
                            .content("Are you sure you would like to book this table?")
                            .positiveText("Yes")
                            .negativeText("No")
                            .show();
                } else {
                    Toast.makeText(getContext(), "Sorry! This table seems to be booked", Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Achi", "Destroyed fragment");
    }


    //Here you can restore saved data in onSaveInstanceState Bundle
    private void onRestoreInstanceStae(Bundle savedInstanceState){
        if(savedInstanceState!=null){
           Integer instance = savedInstanceState.getInt("title");
        }
    }

    //Here you Save your data
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("title", R.drawable.red);
    }

}

