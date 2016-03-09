package com.example.admin.restro.Signing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restro.MainActivity;
import com.example.admin.restro.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText email = (EditText) findViewById(R.id.e1);
        final EditText pass = (EditText) findViewById(R.id.e2);
        TextView mailname = (TextView)findViewById(R.id.navmail);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final String emailtext = email.getText().toString();
                final String password = pass.getText().toString();
                if(emailtext.length()==0)
                {
                    email.setError("Enter an Email Bro");
                }
                if (!isValidEmail(emailtext) && emailtext.length()!=0) {
                    email.setError("Invalid Email Bro");
                }

                if (password.length()==0)
                    {
                        pass.setError("Enter a Password Bro");

                    }
                if (password.length()>12)
                {
                    pass.setError("Password should be lesser than 12 characters");

                }

                if(isValidEmail(emailtext)&&(password.length()!=0 && password.length()<13))
                {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                }



            }
        });
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView signup = (TextView)findViewById(R.id.signup);
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

    private boolean isValidEmail(String emailtext) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailtext);
        return matcher.matches();
    }

    public String isValidPassword(String pass) {
        if (pass != null && pass.length() > 12)
        {
          return pass;
        }
        return null;
    }
}