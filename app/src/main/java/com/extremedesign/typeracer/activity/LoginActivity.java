package com.extremedesign.typeracer.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.adapter.AuthenticationPagerAdapter;
import com.extremedesign.typeracer.fragment.LoginActivityFragments.LoginFragment;
import com.extremedesign.typeracer.fragment.LoginActivityFragments.RegisterFragment;
import com.extremedesign.typeracer.fragment.LoginActivityFragments.UserPasswordResetFragment;
import com.extremedesign.typeracer.listener.DisplayCloseListener;


public class LoginActivity extends AppCompatActivity {

    AuthenticationPagerAdapter pagerAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new LoginFragment(getForgetPassListener() ));
        pagerAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        findViewById(R.id.loginActivity_frameLayout).setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pagerAdapter.getItem(0).onActivityResult(requestCode,resultCode,data);
    }
    public DisplayCloseListener getForgetPassListener(){
        return new DisplayCloseListener() {
            @Override
            public void closeDisplay() {
               getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.loginActivity_frameLayout,new UserPasswordResetFragment())
                       .addToBackStack("change pass")
                       .commit();
               viewPager.setVisibility(View.GONE);
               findViewById(R.id.loginActivity_frameLayout).setVisibility(View.VISIBLE);
            }
        };
    }


}
