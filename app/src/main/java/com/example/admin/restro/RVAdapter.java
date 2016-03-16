package com.example.admin.restro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restro.Signing.Signup;
import com.example.admin.restro.Tab.Tab1;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.content.ContextCompat.startActivities;
import static android.widget.Toast.LENGTH_SHORT;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private List<Data> rvadapter;

    public RVAdapter(List<Data> List) {
        this.rvadapter = List;
    }

    @Override
    public int getItemCount() {
        return rvadapter.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder ViewHolder, int i) {
        Data hi = rvadapter.get(i);
        ViewHolder.vphoto.setImageResource(hi.hotelphoto);
        ViewHolder.vhotel.setText(hi.hotelname);
        ViewHolder.vlocation.setText(hi.location);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView vphoto;
        protected TextView vhotel;
        protected TextView vlocation;
        private Context context;


        public MyViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            vphoto = (ImageView) v.findViewById(R.id.hotelphoto);
            vhotel = (TextView) v.findViewById(R.id.hotelname);
            vlocation = (TextView) v.findViewById(R.id.location);
        }

        @Override
        public void onClick(View v) {
            final Intent intent;
            context = v.getContext();

            Toast.makeText(context, "Clicked here " + getAdapterPosition(), LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putInt("value", getAdapterPosition());
            intent = new Intent(context, Hotel.class);
            intent.putExtras(bundle);
            context.startActivity(intent);

        }
    }
}

