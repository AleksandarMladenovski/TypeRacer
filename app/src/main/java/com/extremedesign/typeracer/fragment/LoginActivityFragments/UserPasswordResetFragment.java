package com.extremedesign.typeracer.fragment.LoginActivityFragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.fragment.UI.CustomActionBarFragment;
import com.extremedesign.typeracer.fragment.UI.EmailEditTextFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserPasswordResetFragment extends Fragment {
    private Button btn_send;
    private TextView tv_confirmation_send_email;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private EmailEditTextFragment emailEditTextFragment;
    private CustomActionBarFragment customActionBarFragment;
    public UserPasswordResetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_user_password_reset, container, false);
        btn_send=itemView.findViewById(R.id.button_forgot_pass_send);
        tv_confirmation_send_email=itemView.findViewById(R.id.tv_confirmation_email);
        auth = FirebaseAuth.getInstance();
        progressBar=itemView.findViewById(R.id.forgot_pass_progressBar);

        emailEditTextFragment=new EmailEditTextFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.email_frame_layout, emailEditTextFragment).commit();

        customActionBarFragment=new CustomActionBarFragment("Forgot Password");
        getFragmentManager().beginTransaction()
                .replace(R.id.custom_action_bar_frame_layout,customActionBarFragment).commit();

        setSendButtonListener();

        return itemView;
    }


    private boolean isEmailValid(){
        if(emailEditTextFragment.isEmailAddressValid()){
            emailEditTextFragment.isSendSuccessful(true);
            tv_confirmation_send_email.setTextColor(getResources().getColor(R.color.color_black));
            tv_confirmation_send_email.setText(getResources().getString(R.string.your_confirmation_link_will_be_sent_to_your_email_address));
            return true;
        }
        else{
            emailEditTextFragment.isSendSuccessful(false);
            tv_confirmation_send_email.setTextColor(getResources().getColor(R.color.color_red));
            tv_confirmation_send_email.setText(getResources().getString(R.string.invalid_email_address));
            return false;
        }

    }
    private void setSendButtonListener() {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmailValid()){
                    final String emailAddress=emailEditTextFragment.getEmailAddress();
                    progressBar.setVisibility(View.VISIBLE);
                    sendPasswordResetEmail(emailAddress);
                }
            }
        });
    }

    private void sendPasswordResetEmail(final String emailAddress) {
        FirebaseRepo.sendPasswordResetEmail(emailAddress ,new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                createSuccessfulDialog(emailAddress);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void createSuccessfulDialog(String emailAddress) {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Reset password");
        alertBuilder.setMessage("Please open the email sent to: "+emailAddress+" and follow further instructions.");
        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder.show();
    }

}
