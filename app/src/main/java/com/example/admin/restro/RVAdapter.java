package com.example.admin.restro;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.content.ContextCompat.startActivities;
import static android.widget.Toast.LENGTH_SHORT;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ContactViewHolder> {

    private List<Data> rvadapter;

    public RVAdapter(List<Data> contactList)
    {
        this.rvadapter = contactList;
    }

    @Override
    public int getItemCount() {
        return rvadapter.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Data hi = rvadapter.get(i);
        contactViewHolder.vphoto.setImageResource(hi.hotelphoto);
        contactViewHolder.vhotel.setText(hi.hotelname);
        contactViewHolder.vlocation.setText(hi.location);
        }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView vphoto;
        protected TextView vhotel;
        protected TextView vlocation;
        private Context context;

        public ContactViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            vphoto =  (ImageView) v.findViewById(R.id.hotelphoto);
            vhotel = (TextView)  v.findViewById(R.id.hotelname);
            vlocation = (TextView)  v.findViewById(R.id.location);
            }

        @Override
        public void onClick(View v) {
            final Intent intent;
            context = v.getContext();
            Toast.makeText(context, "Clicked here" + getAdapterPosition(), LENGTH_SHORT ).show();
            switch (getAdapterPosition())
            {
                case 1:
                    intent = new Intent(context, Konkan.class);
                    context.startActivity(intent);
                    break;

            }
        }


    }}

