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
import android.widget.Toast;

import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.Utils;
import com.extremedesign.typeracer.activity.MainActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private EditText inputName,inputEmail,inputPassword,inputRePassword;
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
        inputEmail=itemView.findViewById(R.id.register_email);
        inputPassword=itemView.findViewById(R.id.register_password);
        inputRePassword=itemView.findViewById(R.id.register_re_password);
        btnRegister=itemView.findViewById(R.id.btn_register);
        progressBar=itemView.findViewById(R.id.register_progressBar);
         auth = FirebaseRepo.getAuthINSTANCE();
        onRegisterButtonPressed();

        return itemView;
    }

    private void onRegisterButtonPressed() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=inputName.getText().toString();
                String email=inputEmail.getText().toString();
                String password=inputPassword.getText().toString();
                String rePassword=inputRePassword.getText().toString();
                boolean canContinue=true;
                if(name.length()==0){
                    inputName.setError("Please enter a name");
                    canContinue=false;
                }
                if(Utils.isEmailValid(email)){
                    inputEmail.setError("Please enter a valid email address");
                    canContinue=false;
                }
                if(password.length()<6){
                    inputPassword.setError("Your password must be more than 6 characters");
                    canContinue=false;
                }
                if(!password.equals(rePassword)){
                    inputRePassword.setError("Passwords didn't match. Try again");
                    canContinue=false;
                }
                if(!canContinue){
                    return;
                }
                    progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    storage.getReference().child("baloon.jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()){
                                                FirebaseUser user = auth.getCurrentUser();
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(name)
                                                        .setPhotoUri(task.getResult())
                                                        .build();
                                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            checkUserStatus();
                                                        }
                                                        else{
                                                            //TODO
                                                            //update failed
                                                            //do something
                                                        }
                                                    }
                                                });
                                            }
                                            else{
                                                //cannot acces photo in storage for some reason
                                                //do something
                                            }
                                        }
                                    });



                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

                }


        });
    }
    private void checkUserStatus(){
        FirebaseUser user=auth.getCurrentUser();
        if (user != null) {
            final String user_id=user.getUid();
            String name=user.getDisplayName();
            String email=user.getEmail();
            String photo=String.valueOf(user.getPhotoUrl());
            boolean isEmailValid=user.isEmailVerified();
            final User player=new User(user_id,new UserInfo(name,email,photo,isEmailValid));
            FirebaseRepo.setCurrentUser(player);
            final DatabaseReference db=FirebaseRepo.getDatabaseInstance();
            db.child("users").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue()==null) {
                        db.child("users").child(user_id).setValue(player.getUserInfo());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            userLogIn();
        }
        progressBar.setVisibility(View.GONE);

    }
    private void userLogIn() {
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
