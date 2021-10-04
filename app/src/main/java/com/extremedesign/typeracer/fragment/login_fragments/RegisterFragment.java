package com.extremedesign.typeracer.fragment.login_fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.extremedesign.typeracer.activity.MainActivity;
import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.fragment.UI.EmailEditTextFragment;
import com.extremedesign.typeracer.fragment.UI.PasswordEditTextFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    RepositoryViewModel repositoryViewModel;
    private EmailEditTextFragment emailEditTextFragment;
    private PasswordEditTextFragment passwordEditTextFragment;
    private TextView nameError,emailError,passError,nameText;
    private EditText inputName;
    private Button btnRegister;
    private ProgressBar progressBar;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_register, container, false);

        repositoryViewModel= ViewModelProviders.of(this).get(RepositoryViewModel.class);


        //TODO BAD IPMPLEMENTATION
        repositoryViewModel.getCurrentUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null){
                    repositoryViewModel.getCurrentUser().removeObservers(RegisterFragment.this);
                    userLogIn();

                }
            }
        });
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


                    repositoryViewModel.getFirebaseRepo().createUserWithEmailAndPassword(email, password, name);

                    progressBar.setVisibility(View.GONE);
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
