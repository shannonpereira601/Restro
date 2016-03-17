package com.example.admin.restro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restro.Tab.Tab1;
import com.example.admin.restro.Tab.Tab2;
import com.example.admin.restro.Tab.Tab3;

public class Hotel extends AppCompatActivity {

    private ViewPager mPager;
    private SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        String kpnach = "Test";
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


    class MyPagerAdapter extends FragmentStatePagerAdapter                                                                                          {

        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) // if the position is 0 we are returning the First tab
            {
                Bundle bundle = getIntent().getExtras();
                int x = bundle.getInt("value");
                Tab1 tab1 = Tab1.getInstance(x);
                return tab1;
            }
            else  if(position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
            {
                Tab2 tab2 = new Tab2();
                return tab2;
            }
            else {
                Tab3 tab3 = new Tab3();
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


    public static class MyFragment extends Fragment{
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
    }

}