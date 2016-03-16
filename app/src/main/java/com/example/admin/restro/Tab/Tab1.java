package com.example.admin.restro.Tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.restro.R;

/**
 * Created by admin on 13/03/2016.
 */
public class Tab1 extends Fragment {
    public static Tab1 getInstance(int position) {

        Tab1 myFragmeent = new Tab1();
        Bundle args = new Bundle();
        args.putInt("number",position);
        myFragmeent.setArguments(args);
        return myFragmeent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);
        TextView hotel = (TextView)v.findViewById(R.id.nameofhotel);
        TextView location = (TextView)v.findViewById(R.id.nameoflocation);
        TextView cost = (TextView)v.findViewById(R.id.amountcost);
        Bundle bundle = getArguments();
        int x = bundle.getInt("number");
        String[] ahotel = getResources().getStringArray(R.array.Hotels);
        String[] alocation = getResources().getStringArray(R.array.Location);
        String[] acost = getResources().getStringArray(R.array.Cost);
        if(bundle!=null)
        {
            hotel.setText(ahotel[x] + "");
            location.setText(alocation[x] + "");
            cost.setText(acost[x] + "");

        }
        return v;
    }
}