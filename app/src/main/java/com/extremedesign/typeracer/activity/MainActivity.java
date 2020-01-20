package com.extremedesign.typeracer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.fragment.DisplayPlayerFragment;
import com.extremedesign.typeracer.listener.PlayerDisplayClose;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

//    @Inject
//    RetrofitClient retrofitClient;
private DatabaseReference mDatabase;
private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user =FirebaseRepo.getCurrentUser();

        ImageView photoImage=findViewById(R.id.photoImageButton);
        Glide.with(this).load(Uri.parse(user.getUserInfo().getPhotoUrl())).into(photoImage);
        photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerDisplayClose listener=new PlayerDisplayClose() {
                    @Override
                    public void onPlayerDisplayClosed() {
                        findViewById(R.id.main_FrameLayout).setVisibility(View.GONE);
                    }
                };
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace((R.id.main_FrameLayout),new DisplayPlayerFragment(user,listener))
                        .addToBackStack("Display User")
                        .commit();

                findViewById(R.id.main_FrameLayout).setVisibility(View.VISIBLE);
            }
        });



        findViewById(R.id.SignOutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.signOut();
                LoginManager.getInstance().logOut();
                Intent newIntent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(newIntent);
                finish();
            }
        });

        findViewById(R.id.buttonStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, TypeRaceActivity.class);
                startActivity(intent);
            }
        });


    }
}
