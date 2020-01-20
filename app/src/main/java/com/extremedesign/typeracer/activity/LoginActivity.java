package com.extremedesign.typeracer.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.adapter.AuthenticationPagerAdapter;
import com.extremedesign.typeracer.fragment.LoginActivityFragments.LoginFragment;
import com.extremedesign.typeracer.fragment.LoginActivityFragments.RegisterFragment;


public class LoginActivity extends AppCompatActivity {

    AuthenticationPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewPager viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new LoginFragment());
        pagerAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pagerAdapter.getItem(0).onActivityResult(requestCode,resultCode,data);
    }


}
