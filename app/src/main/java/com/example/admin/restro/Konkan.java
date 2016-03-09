package com.example.admin.restro;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Konkan extends AppCompatActivity {

    private int myClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konkan);

        final ImageView iv1 = (ImageView)findViewById(R.id.i1);
        final ImageView iv2 = (ImageView)findViewById(R.id.i2);
        final ImageView iv3 = (ImageView)findViewById(R.id.i3);
        final ImageView iv4 = (ImageView)findViewById(R.id.i4);
         iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickCount++;
                if(myClickCount%2==1) {
                    int color = Color.parseColor("#c62828");
                    iv1.setColorFilter(color);
                }
                else
                {
                    int color = Color.parseColor("#8bc34a");
                    iv1.setColorFilter(color);
                }
            }
        });
    }



}

