package com.example.admin.restro.Tab;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.restro.Data;
import com.example.admin.restro.R;
import com.example.admin.restro.RVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 26/03/2016.
 */
public class BackgroundTask extends AsyncTask<Void, Data, Void>{

    Context context;
    Activity activity;
    RecyclerView reclist;
    RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    String json_string;
    List<Data> hotelbt = new ArrayList<>();
    MaterialDialog dialog;
    Spinner spinner;
    List<String> sploc = new ArrayList<String>();
    String Item = "null";
    String Itemsp[];


    public BackgroundTask(Context ctx) {
        context = ctx;
        activity = (Activity) ctx;
    }

    String jsonstring = "http://restro.esy.es/hotelinfo.php";

    @Override
    protected void onPreExecute() {
        reclist = (RecyclerView) activity.findViewById(R.id.rv);
        reclist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(activity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reclist.setLayoutManager(llm);
        adapter = new RVAdapter(hotelbt);
        reclist.setAdapter(adapter);
        spinner = (Spinner) activity.findViewById(R.id.spinner);
       /* progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait Bro...");
        progressDialog.setMessage("List is loading bro...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        dialog = new MaterialDialog.Builder(activity)
                .title("Loading Restaurants").titleColor(activity.getResources().getColor(R.color.colorPrimary))
                .content("Please Wait...")
                .show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(jsonstring);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream1 = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream1));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            httpURLConnection.disconnect();
            json_string = stringBuilder.toString().trim();
            int[] Photo = {R.drawable.roll, R.drawable.fish, R.drawable.chinese, R.drawable.indian, R.drawable.fish, R.drawable.roll, R.drawable.roll, R.drawable.fish, R.drawable.chinese, R.drawable.indian, R.drawable.fish, R.drawable.roll};
            String[] Hotel = activity.getResources().getStringArray(R.array.Hotels);
            String[] Location = activity.getResources().getStringArray(R.array.Location);
            String[] capacity;

            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("Hotel");
            StringBuilder sb = new StringBuilder();
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            int count = 0;
            if(Item.equals("null")) {
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    Data data1 = new Data(Photo[count], JO.getString("res_name"), JO.getString("address"));
                    sb.append(JO.getString("address")).append(",");
                    sb1.append(JO.getString("res_name")).append(",");
                    sb2.append(JO.getString("No Of Tables")).append(",");
                    sb3.append(JO.getString("Res_id")).append(",");
                    sploc.add(JO.getString("address"));
                    publishProgress(data1);
                    count++;
                    Thread.sleep(200);
                }
            }
            else
            {

            }
            Log.d("Capacity", sb2.toString());
            SharedPreferences sharedPreferences = activity.getSharedPreferences("HotelData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Location", sb.toString());
            editor.putString("Restaurant", sb1.toString());
            editor.putString("Tables", sb2.toString());
            editor.putString("TableID", sb3.toString());
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
    protected void onProgressUpdate(Data... values) {
        hotelbt.add(values[0]);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //progressDialog.dismiss();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, sploc);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        dialog.dismiss();
    }




   /* public List<Data> getdata() {
        List<Data> hotels = new ArrayList<>();
        int[] Photo = {R.drawable.roll, R.drawable.fish, R.drawable.chinese, R.drawable.indian, R.drawable.fish, R.drawable.roll, R.drawable.roll, R.drawable.fish, R.drawable.chinese, R.drawable.indian, R.drawable.fish, R.drawable.roll};
        String[] Hotel = activity.getResources().getStringArray(R.array.Hotels);
        String[] Location = activity.getResources().getStringArray(R.array.Location);
        for (int i = 0; i < Hotel.length; i++) {
            Data current = new Data(Photo[i], Hotel[i], Location[i]);
            current.hotelphoto = Photo[i];
            current.hotelname = Hotel[i];
            current.location = Location[i];
            hotels.add(current);
        }
        return hotels;
    }*/
}
