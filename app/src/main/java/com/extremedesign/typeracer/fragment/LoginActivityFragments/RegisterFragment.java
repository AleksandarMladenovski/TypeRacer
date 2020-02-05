package com.extremedesign.typeracer.fragment.LoginActivityFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.extremedesign.typeracer.DataRepository.RepositoryInstance;
import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.Utils;
import com.extremedesign.typeracer.activity.MainActivity;
import com.extremedesign.typeracer.fragment.UI.EmailEditTextFragment;
import com.extremedesign.typeracer.fragment.UI.PasswordEditTextFragment;
import com.extremedesign.typeracer.listener.JobWorker;
import com.extremedesign.typeracer.listener.ProfileImageListener;
import com.extremedesign.typeracer.model.ProfileImage;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private EmailEditTextFragment emailEditTextFragment;
    private PasswordEditTextFragment passwordEditTextFragment;
    private TextView nameError,emailError,passError,nameText;
    private EditText inputName;
    private Button btnRegister;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_register, container, false);
        inputName=itemView.findViewById(R.id.register_name);
        btnRegister=itemView.findViewById(R.id.btn_register);
        progressBar=itemView.findViewById(R.id.register_progressBar);

        nameError=itemView.findViewById(R.id.tv_register_name_error);
        nameText=itemView.findViewById(R.id.register_tv_email_text);
        emailError=itemView.findViewById(R.id.tv_register_email_error);
        passError=itemView.findViewById(R.id.tv_register_password_error);


        emailEditTextFragment= new EmailEditTextFragment();
        getFragmentManager().beginTransaction().replace(R.id.register_email_frame_layout,emailEditTextFragment).commit();
        passwordEditTextFragment=new PasswordEditTextFragment();
        getFragmentManager().beginTransaction().replace(R.id.register_password_frame_layout,passwordEditTextFragment).commit();

         auth = FirebaseRepo.getAuthINSTANCE();
        onRegisterButtonPressed();

        return itemView;
    }

    private void onRegisterButtonPressed() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetColors();
                final String name = inputName.getText().toString();
                final String email = emailEditTextFragment.getEmailAddress();
                final String password = passwordEditTextFragment.getPassword();

                if (inputIsCorrect(name)) {
                    progressBar.setVisibility(View.VISIBLE);
                    RepositoryInstance.getTypeRacerRepository(getContext()).getImageByName("baloon.jpg", new ProfileImageListener() {
                        @Override
                        public void getImages(List<ProfileImage> images) {
                            FirebaseRepo.createUserWithEmailAndPassword(email, password, name,images.get(0).getImageUrl(), new JobWorker() {
                                @Override
                                public void jobFinished(boolean state) {
                                    if(state){
                                        Toast.makeText(getContext(), "Authentication Successful.",
                                                Toast.LENGTH_SHORT).show();
                                        userLogIn();
                                    }
                                    else{
                                        Toast.makeText(getContext(), "Authentication Failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        }
                    });


                }

            }
        });
    }

    private void resetColors(){
        nameText.setTextColor(getResources().getColor(R.color.color_light_blue));
        inputName.setBackground(getResources().getDrawable(R.drawable.et_custom_26a69a_color));
        nameError.setVisibility(View.GONE);
        emailEditTextFragment.isSendSuccessful(true);
        emailError.setVisibility(View.GONE);
        passwordEditTextFragment.isSendSuccessful(true);
        passError.setVisibility(View.GONE);
    }

    private boolean inputIsCorrect(String name) {
        boolean canContinue=true;
        if(name.length()==0){
            nameError.setVisibility(View.VISIBLE);
            nameText.setTextColor(getResources().getColor(R.color.color_red));
            inputName.setBackground(getResources().getDrawable(R.drawable.et_custom_red_color));
            canContinue=false;
        }
        if(!emailEditTextFragment.isEmailAddressValid()){
            emailEditTextFragment.isSendSuccessful(false);
            emailError.setVisibility(View.VISIBLE);
            canContinue=false;
        }
        if(!passwordEditTextFragment.isPasswordValid()){
            passwordEditTextFragment.isSendSuccessful(false);
            passError.setVisibility(View.VISIBLE);
            canContinue=false;
        }

        return canContinue;
    }
    private void userLogIn() {
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
