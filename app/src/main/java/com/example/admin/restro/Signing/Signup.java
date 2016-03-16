package com.example.admin.restro.Signing;

import android.content.Intent;
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

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText username = (EditText)findViewById(R.id.s1);
        final EditText email = (EditText)findViewById(R.id.s2);
        final EditText pass = (EditText)findViewById(R.id.s3);
        final EditText verpassword = (EditText)findViewById(R.id.s4);
        final EditText phone = (EditText)findViewById(R.id.s5);
        final EditText address = (EditText)findViewById(R.id.s6);
        Button done = (Button)findViewById(R.id.done);

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
                final String number = phone.getText().toString();
                final String home = address.getText().toString();
                if(user.length()==0)
                {
                    username.setError("Enter a username");
                }
                if(emailtext.length()==0)
                {
                    email.setError("Enter an Email");
                }
                if (password.length()==0)
                {
                    pass.setError("Enter a Password");

                }
                if(verify.length()==0)
                {
                    verpassword.setError("Enter a Password Above");
                }
                if(number.length()==0)
                {
                    phone.setError("Enter your Phone Number");
                }
                if(home.length()==0)
                {
                    address.setError("Enter your Address");
                }
                if (!isValidEmail(emailtext) && emailtext.length()!=0) {
                    email.setError("Invalid Email");
                }
                if (password.length()>12)
                {
                    pass.setError("Password should be lesser than 12 characters");
                }
                boolean a = password.equalsIgnoreCase(verify);
                if (!a && password.length()!=0)
                {
                    verpassword.setError("Password do not match Bro");
                }
                if (!isValidNumber(number))
                {
                    phone.setError("Invalid Phone Number");
                }
                if(isValidEmail(emailtext)&&(password.length()!=0 && password.length()<13) && (password==verify))
                {
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
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
}
