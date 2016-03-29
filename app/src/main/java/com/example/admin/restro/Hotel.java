package com.example.admin.restro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.restro.Tab.BackgroundTask;
import com.example.admin.restro.Tab.Tab1;
import com.example.admin.restro.Tab.Reservation;
import com.example.admin.restro.Tab.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Hotel extends AppCompatActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    Reservation tab3;
    RequestQueue requestQueue;
    private String URL = "http://restro.esy.es/hotelinfo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(myPagerAdapter);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorWhite);
            }
        });
        mTabs.setViewPager(mPager);
    }


    class MyPagerAdapter extends FragmentStatePagerAdapter {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) // if the position is 0 we are returning the First tab
            {
                Bundle bundle = getIntent().getExtras();
                int x = bundle.getInt("value");
                Tab1 tab1 = Tab1.getInstance(x);
                return tab1;
            } else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
            {
                Food tab2 = new Food();
                return tab2;
            } else {
                Bundle bundle = getIntent().getExtras();
                int x = bundle.getInt("value");
                tab3 = Reservation.getInstance(x);
               // tab3.setRetainInstance(true);
                return tab3;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return tabs.length;
        }
    }


  /*  public static class MyFragment extends Fragment{
        public static MyFragment getInstance(int position) {

            MyFragment myFragmeent = new MyFragment();
            Bundle args = new Bundle();
            args.putInt("position",position);
            myFragmeent.setArguments(args);
            return myFragmeent;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragmentmy, container, false);
            TextView textView = (TextView)layout.findViewById(R.id.frag);
            Bundle bundle = getArguments();
            if(bundle!=null)
            {
                textView.setText("The page currently selected is " + bundle.getInt("position"));
            }
            return layout;
        }
    }*/

}