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
import com.extremedesign.typeracer.listener.JobWorker;
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
                final String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String rePassword = inputRePassword.getText().toString();


                if (inputIsCorrect(name, email, password, rePassword)) {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseRepo.createUserWithEmailAndPassword(email, password, name, new JobWorker() {
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

            }
        });
    }

    private boolean inputIsCorrect(String name,String email,String password,String rePassword) {
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

        return canContinue;
    }
    private void userLogIn() {
        Intent i = new Intent(getContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
