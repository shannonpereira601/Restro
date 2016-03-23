package com.example.admin.restro.Signing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.restro.MainActivity;
import com.example.admin.restro.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private static final String REGISTER_URL = "http://restro.ey.es/signinreg.php";
    Button done;
    EditText username, email, pass, verpassword, phoneno, addressloc;
    String name1, username1, password1, address1, phone1;
    private Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = (EditText) findViewById(R.id.s1);
        email = (EditText) findViewById(R.id.s2);
        pass = (EditText) findViewById(R.id.s3);
        verpassword = (EditText) findViewById(R.id.s4);
        phoneno = (EditText) findViewById(R.id.s5);
        addressloc = (EditText) findViewById(R.id.s6);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                String subject = "Confirmation Email";
                String message = "Thanking you for Signing up for Restro";
                Intent mail = new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL, emaildid);
                mail.putExtra(Intent.EXTRA_SUBJECT, subject);
                mail.putExtra(Intent.EXTRA_TEXT,message);
                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail,"Choose an Email Client: "));*/

                final String emailtext = email.getText().toString();
                final String password = pass.getText().toString();
                final String verify = verpassword.getText().toString();
                final String user = username.getText().toString();
                final String number = phoneno.getText().toString();
                final String home = addressloc.getText().toString();
                if (user.length() == 0) {
                    username.setError("Enter a username");
                }
                if (emailtext.length() == 0) {
                    email.setError("Enter an Email");
                }
                if (password.length() == 0) {
                    pass.setError("Enter a Password");
                }
                if (verify.length() == 0) {
                    verpassword.setError("Enter a Password Above");
                }
                if (number.length() == 0) {
                    phoneno.setError("Enter your Phone Number");
                }
                if (home.length() == 0) {
                    addressloc.setError("Enter your Address");
                }
                if (!isValidEmail(emailtext) && emailtext.length() != 0) {
                    email.setError("Invalid Email");
                }
                if (password.length() > 12) {
                    pass.setError("Password should be lesser than 12 characters");
                }
                boolean a = password.equalsIgnoreCase(verify);
                if (!a && password.length() != 0) {
                    verpassword.setError("Password do not match Bro");
                }
                if (!isValidNumber(number)) {
                    phoneno.setError("Invalid Phone Number");
                }

                if (isValidEmail(emailtext)&& (password.length() != 0 && password.length() < 13) /*&& (password == verify)*/) {
                    registerUser();
                    register(name1, username1, password1, address1, phone1);
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    check();
                }
            }
        });
    }

    private void registerUser() {

        name1 = username.getText().toString().trim().toLowerCase();
        username1 = email.getText().toString().trim().toLowerCase();
        password1 = pass.getText().toString().trim().toLowerCase();
        address1 = addressloc.getText().toString().trim().toLowerCase();
        phone1 = phoneno.getText().toString().trim().toLowerCase();
    }

    private void register(String name, String username, String password, String address, String phone) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Signup.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("username", params[1]);
                data.put("password", params[2]);
                data.put("address", params[3]);
                data.put("phone", params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }
    }


    private boolean isValidEmail(String emailtext) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailtext);
        return matcher.matches();
    }

    private boolean isValidNumber(String number) {
        String NUMBER_PATTERN = "^[789]\\d{9}$";

        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public void check() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        check = true;
        editor.putBoolean("check", check);
        editor.commit();
    }
}

