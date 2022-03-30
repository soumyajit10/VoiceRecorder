package com.example.voicerecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.voicerecorder.Adapters.ViewPagerAdapter;
import com.example.voicerecorder.Fragments.RecorderFragment;
import com.example.voicerecorder.Fragments.RecordingsFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.ViewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setSupportActionBar(toolbar);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private  void setUpViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new RecorderFragment(),"Recorder");
        viewPagerAdapter.addFragment(new RecordingsFragment(),"Recordings");
        viewPager.setAdapter(viewPagerAdapter);

    }
}