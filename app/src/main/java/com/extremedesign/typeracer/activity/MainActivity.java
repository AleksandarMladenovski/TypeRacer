package com.extremedesign.typeracer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.extremedesign.typeracer.DataRepository.RepositoryTypeRacer.RepositoryViewModel;
import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.fragment.ChangePhotoFragment;
import com.extremedesign.typeracer.listener.ProfilePictureListener;
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
private RepositoryViewModel repositoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView photoImage=findViewById(R.id.photoImageButton);

        final RepositoryViewModel repositoryViewModel= ViewModelProviders.of(this).get(RepositoryViewModel.class);

        user =repositoryViewModel.getCurrentUser().getValue();

        repositoryViewModel.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                int id = getResources().getIdentifier(user.getPhotoName(), "drawable", MainActivity.this.getPackageName());
                photoImage.setImageResource(id);
                MainActivity.this.user=user;
            }
        });
        photoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerDisplayClose listener=new PlayerDisplayClose() {
                    @Override
                    public void onPlayerDisplayClosed() {
                        findViewById(R.id.main_FrameLayout).setVisibility(View.GONE);

                    }

                    @Override
                    public void openPlayerChangePhoto() {

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_FrameLayout,new ChangePhotoFragment(new ProfilePictureListener() {
                                    @Override
                                    public void onPictureChosen(Drawable drawable,String name) {
//                                        photoImage.setImageDrawable(drawable);
                                        repositoryViewModel.getFirebaseRepo().updateUserProfilePicture(name);

                                    }
                                }))
                                .addToBackStack("Change Photo")
                                .commit();
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
                FirebaseRepo.getGoogleSignInClient(MainActivity.this).signOut();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
