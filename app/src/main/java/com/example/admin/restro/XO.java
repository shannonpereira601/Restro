package com.example.admin.restro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class XO extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xo);

        GridView gv = (GridView)findViewById(R.id.gridView);
        int length = 10;
        Integer[] green = new Integer[length];
        /*shifted*/

        Integer[] images = {R.drawable.plus,R.drawable.plus,R.drawable.plus,R.drawable.plus,R.drawable.plus,R.drawable.plus,R.drawable.plus,R.drawable.plus,R.drawable.plus};
        gv.setAdapter(new FoodAdapter(XO.this, images));
    }
}
