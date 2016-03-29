package com.example.admin.restro.Tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.restro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 13/03/2016.
 */
public class Tab1 extends Fragment {
    String[] location1;
    String[] name;
    String getlocation,getname;
    String insertURL = "http://restro.esy.es/hotelinfo.php";
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
        BackgroundTask bt = new BackgroundTask(getContext());
        int x = bundle.getInt("number");
        getData();
        String[] ahotel = getResources().getStringArray(R.array.Hotels);
        String[] alocation = getResources().getStringArray(R.array.Location);
        String[] acost = getResources().getStringArray(R.array.Cost);
        if(bundle!=null)
        {
            hotel.setText(name[x] + "");
            location.setText(location1[x] + "");
            cost.setText(acost[x] + "");
        }
        return v;
    }

    public void getData()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("HotelData", Context.MODE_PRIVATE);
        getlocation = sharedPreferences.getString("Location", "Default");
        getname = sharedPreferences.getString("Restaurant", "Default");
        name = getname.split(",");
        location1 = getlocation.split(",");
    }
}