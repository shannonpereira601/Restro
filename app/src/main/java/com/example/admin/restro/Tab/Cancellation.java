package com.example.admin.restro.Tab;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.restro.CancellationAdapter;
import com.example.admin.restro.R;
import com.example.admin.restro.ReservationAdapter;
import com.example.admin.restro.Signing.SendMail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by PAREKH on 13-04-2016.
 */

public class Cancellation extends Fragment {
    public static Cancellation getInstance(int position) {

        Cancellation myFragmeent = new Cancellation();
        Bundle args = new Bundle();
        args.putInt("number", position);
        myFragmeent.setArguments(args);
        return myFragmeent;

    }

    GridView gv;
    Integer[] table;
    String Hoteltables[], Codes[], Capacities[], Statuses[], Getids[];
    String code = null;
    String hoteltable, capacity, status, getid;
    Context context = getContext();
    //public static Integer[] images = {R.drawable.chicken, R.drawable.chutney, R.drawable.custard, R.drawable.falooda, R.drawable.lasagna, R.drawable.roti,R.drawable.waiter};
    //public static Integer[] images = {R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green,R.drawable.green};
    public static String[] counttable = {"4", "5", "2", "3", "4", "5", "2", "7", "9"};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_table, container, false);
        Log.d("onCreateView", "Inside onCreateView");
        gv = (GridView) v.findViewById(R.id.gridView1);
        gv.setVisibility(View.INVISIBLE);
        final HotelBgTask hotelBgTask = new HotelBgTask(getContext());
        final HotelBgTask1 hotelBgTask1 = new HotelBgTask1();

        Button button1 = (Button) v.findViewById(R.id.cancel);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gv.setVisibility(View.VISIBLE);
                Bundle bundle = getArguments();
                final int x = bundle.getInt("number");
                String[] tables = getResources().getStringArray(R.array.tablevalues);
                getHotData();
                final String ids = Getids[x];
                hotelBgTask.execute(ids);


                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            final int position, long id) {
                        if (table[position] == R.drawable.red
                                ) {
                            new MaterialDialog.Builder(getContext())
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                            gv.setAdapter(new CancellationAdapter(getContext(), counttable, table));
                                            String method = "cancel";
                                            String pos = String.valueOf(position + 1);
                                            hotelBgTask1.execute(pos,ids);
                                            table[position] = R.drawable.green;
                                            //String reservation = "Non_Available";
                                            //hotelBgTask1.execute(reservation);
                                        }
                                    })
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            // TODO
                                        }
                                    }).title("Confirmation")
                                    .content("Are you sure you would like to cancel this table?")
                                    .positiveText("Yes")
                                    .negativeText("No")
                                    .show();
                        } else {
                            Toast.makeText(getContext(), "Sorry! This table seems to be available", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return v;
    }


    public class HotelBgTask1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {

            String reservation = params[0];
            String resid = params[1];

            String url = "http://restro.esy.es/updatetable.php";
            Log.d("new", reservation);
            try {
                URL urltable = new URL(url);

                HttpURLConnection httpConnection = (HttpURLConnection) urltable.openConnection();
                httpConnection.setRequestMethod("POST");
                httpConnection.setDoOutput(true);
                Log.d("new1", reservation);
                OutputStream os = httpConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("reserve", "UTF-8") + "=" + URLEncoder.encode(reservation, "UTF-8") + "&" +
                        URLEncoder.encode("resid", "UTF-8") + "=" + URLEncoder.encode(resid, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS = httpConnection.getInputStream();
                IS.close();
                Log.d("new2", reservation);
                httpConnection.disconnect();
                return "cancellation Success!";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            sendEmail();
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        }

    }


    public class HotelBgTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String json_string;
        String[] Capakitty;
        MaterialDialog dialog;
        StringBuilder sb, sb1, sb2;
        Context context;
        Activity activity;

        public HotelBgTask(Context context) {
            this.context = context;
            activity = (Activity) context;
        }

        @Override
        protected void onPreExecute() {
            dialog = new MaterialDialog.Builder(activity)
                    .title("Loading Tables").titleColor(activity.getResources().getColor(R.color.colorPrimary))
                    .content("Please Wait...")
                    .show();
        }

        @Override
        protected String doInBackground(String... params) {

            String ids = params[0];
            try {
                String jsonstring = "http://restro.esy.es/allhotels.php";
                URL url = new URL(jsonstring);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                httpURLConnection.disconnect();

                json_string = stringBuilder.toString().trim();


                JSONObject jsonObject = new JSONObject(json_string);

                JSONArray jsonArray;
                if (ids.equals("123")) {
                    jsonArray = jsonObject.getJSONArray("5spice");
                } else if (ids.equals("124")) {
                    jsonArray = jsonObject.getJSONArray("kasbah");
                } else if (ids.equals("156")) {
                    jsonArray = jsonObject.getJSONArray("flags");
                } else if (ids.equals("178")) {
                    jsonArray = jsonObject.getJSONArray("5spice");
                } else if (ids.equals("167")) {
                    jsonArray = jsonObject.getJSONArray("breeze");
                } else if (ids.equals("463")) {
                    jsonArray = jsonObject.getJSONArray("bademiya");
                } else if (ids.equals("137")) {
                    jsonArray = jsonObject.getJSONArray("90feet");
                } else {
                    jsonArray = jsonObject.getJSONArray("90feet");
                }
                sb = new StringBuilder();
                sb1 = new StringBuilder();
                sb2 = new StringBuilder();
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    sb.append(JO.getString("Status")).append(",");
                    sb1.append(JO.getString("Reservation_code")).append(",");
                    sb2.append(JO.getString("Capacity")).append(",");
                    count++;
                }
                Log.d("IDCHECK", sb2.toString());
                Capakitty = sb2.toString().split(",");
                String[] Statoos = sb.toString().split(",");
                table = new Integer[Capakitty.length];

                for (int i = 0; i < Capakitty.length; i++) {
                    if (Statoos[i].equals("Non_Available")) {
                        table[i] = R.drawable.red;
                        Thread.sleep(100);
                    } else {
                        table[i] = R.drawable.green;
                        Thread.sleep(100);
                    }
                }

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ReservationData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Status", sb.toString());
                editor.putString("Code", sb1.toString());
                editor.putString("Capacity", sb2.toString());
                editor.commit();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.flyfromcentre);
            gv.setAdapter(new ReservationAdapter(context, Capakitty, table));
            gv.setAnimation(anim);
            anim.start();
            dialog.dismiss();
            super.onPostExecute(result);
        }

    }

    public void getTabData() {
        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("ReservationData", Context.MODE_PRIVATE);
        status = sharedPreferences2.getString("Status", "Default");
        code = sharedPreferences2.getString("Code", "Default");
        capacity = sharedPreferences2.getString("Capacity", "Default");
        Capacities = capacity.split(",");
        Codes = code.split(",");
        Statuses = status.split(",");
        Log.d("IDCHECK2", capacity);
    }

    public void getHotData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("HotelData", Context.MODE_PRIVATE);
        hoteltable = sharedPreferences.getString("Tables", "Default");
        getid = sharedPreferences.getString("TableID", "Default");
        Hoteltables = hoteltable.split(",");
        Getids = getid.split(",");
    }

    private void sendEmail() {
        //Getting content for email
         SharedPreferences sharedPreference = getContext().getSharedPreferences("Profile", Context.MODE_PRIVATE);
           String username = sharedPreference.getString("email","default");
        String email = username;
        //  getTabData();
        String subject = "RESTRO Table Cancellation Notification";
        String message = "Your table has been cancelled successfully on " + DateFormat.getDateTimeInstance().format(new Date()) +"At " ;


        //Creating SendMail object
        SendMail sm = new SendMail(getContext(), email, subject, message);
        //Executing sendmail to send email
        sm.execute();

    }




}

