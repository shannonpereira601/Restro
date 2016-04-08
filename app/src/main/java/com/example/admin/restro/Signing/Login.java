package com.example.admin.restro.Signing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.restro.MainActivity;
import com.example.admin.restro.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private TextView textView;
    private Boolean check;
    private CallbackManager callbackManager;
    String emailtext;
    EditText email, pass;
    Boolean failed;
    RequestQueue requestQueue;
    String insertURL = "http://restro.esy.es/login.php";
    String checking;
    TextView datavalues;
    //boolean check=true;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            final AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            check();
            if (!check) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }

            GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                    String email = user.optString("email");
                    String name = user.optString("name");
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    check();
                }
            }).executeAsync();

        }


        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        //textView = (TextView) findViewById(R.id.facebooktv);
        /*ImageView iv1 = (ImageView) findViewById(R.id.restroimage);
        iv1.buildDrawingCache();
        Bitmap bitmap = iv1.getDrawingCache();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();
        String img_str = Base64.encodeToString(image, 0);*/


//        Button database = (Button) findViewById(R.id.database);

        LoginButton loginButton = (LoginButton) findViewById(R.id.facebookloginbutton);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
        loginButton.registerCallback(callbackManager, callback);
    /*    requestQueue = Volley.newRequestQueue(getApplicationContext());

        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ImageView", "Clicked here");
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, insertURL, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("List");
                            Toast.makeText(getBaseContext(), "value " + list.length(), Toast.LENGTH_LONG).show();
                            TextView kp = (TextView) findViewById(R.id.result);
                            for (int i = 0; i < list.length(); i++) {

                                JSONObject data = list.getJSONObject(i);
                                String jname = data.getString("Name");
                                String jmail = data.getString("Email_id");
                                kp.append(jname + " " + jmail + " " + "\n");
                            }
                            kp.append("===\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });

        /*database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ImageView", "Clicked here");

            }
        });*/
        email = (EditText) findViewById(R.id.e1);
        pass = (EditText) findViewById(R.id.e2);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailtext = email.getText().toString();
                final String password = pass.getText().toString();
                if (emailtext.length() == 0) {
                    email.setError("Enter an Email Bro");
                }
                if (!isValidEmail(emailtext) && emailtext.length() != 0) {
                    email.setError("Invalid Email Bro");
                }

                if (password.length() == 0) {
                    pass.setError("Enter a Password Bro");

                }
                if (password.length() > 12) {
                    pass.setError("Password should be lesser than 12 characters");

                }

                if (isValidEmail(emailtext) && (password.length() != 0 && password.length() < 13)) {
                    //sendEmail();
                    if (haveNetworkConnection()) {
                        login();
                    } else {
                        Toast.makeText(getBaseContext(), "Please turn your Network On", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        TextView signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("EmailId");
        passwordWrapper.setHint("Password");
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void check() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        check = true;
        editor.putBoolean("check", check);
        editor.commit();
    }

    public boolean passdetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String email = emailtext;
        editor.putString("email",email);
        editor.commit();
        return failed;
    }

    public void login() {

        final String username = email.getText().toString();
        final String password = pass.getText().toString();
        String method = "login";
        RegisterUserClass ru = new RegisterUserClass(this);
        //   String abc="method" + method +"username" + username +"pass"+password;
        ru.execute(method, username, password);
    //    passdetails();
        //  checktest();
    }

    private boolean isValidEmail(String emailtext) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailtext);
        return matcher.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void sendEmail() {
        //Getting content for email
        String email = emailtext;
        String subject = "Thank You For using Restro";
        String message = "THANKS BUDDY!";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();

    }

    @Override
    public void onBackPressed() {
        return;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}