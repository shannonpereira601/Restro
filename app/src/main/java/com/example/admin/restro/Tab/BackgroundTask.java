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
public class BackgroundTask extends AsyncTask<Void, Data, Void> {

    Context context;
    Activity activity;
    RecyclerView reclist;
    RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    String json_string;
    List<Data> hotelbt = new ArrayList<>();
    MaterialDialog dialog;


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
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject.getJSONArray("Hotel");
            StringBuilder sb = new StringBuilder();
            StringBuilder sb1 = new StringBuilder();
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                Data data1 = new Data(Photo[count], JO.getString("res_name"), JO.getString("address"));
                sb.append(JO.getString("address")).append(",");
                sb1.append(JO.getString("res_name")).append(",");
                publishProgress(data1);
                count++;
                Thread.sleep(200);
            }

            SharedPreferences sharedPreferences = activity.getSharedPreferences("HotelData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Location",sb.toString());
            editor.putString("Restaurant",sb1.toString());
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
