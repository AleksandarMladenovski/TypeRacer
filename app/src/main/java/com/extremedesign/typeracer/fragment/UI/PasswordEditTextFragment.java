package com.extremedesign.typeracer.fragment.UI;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.extremedesign.typeracer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordEditTextFragment extends Fragment {
    private TextView tv_password;
    private ImageView btnPassVisibility;
    private EditText inputPassword;


    public PasswordEditTextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_password_edit_text, container, false);
        tv_password=itemView.findViewById(R.id.fragment_tv_password_text);
        btnPassVisibility=itemView.findViewById(R.id.fragment_icon_pass_visibility);
        inputPassword=itemView.findViewById(R.id.fragment_password);

        listenForPassVisibility();

        return itemView;
    }
    private void listenForPassVisibility() {
        btnPassVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable currentDrawable = btnPassVisibility.getDrawable();
                if(currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp).getConstantState())){
                    btnPassVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_black_24dp));
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    btnPassVisibility.setImageDrawable(getResources().getDrawable(R.drawable.ic_visibility_off_black_24dp));
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public boolean isPasswordValid(){
        String password=inputPassword.getText().toString().trim();
        return !TextUtils.isEmpty(password);
    }

    public void isSendSuccessful(boolean attempt){
        if(attempt){
            tv_password.setTextColor(getResources().getColor(R.color.color_light_blue));
            inputPassword.setBackground(getResources().getDrawable(R.drawable.et_custom_26a69a_color));
        }
        else{
            tv_password.setTextColor(getResources().getColor(R.color.color_red));
            inputPassword.setBackground(getResources().getDrawable(R.drawable.et_custom_red_color));
        }
    }
    public String getPassword(){
        return inputPassword.getText().toString().trim();
    }

}
