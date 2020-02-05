package com.extremedesign.typeracer.fragment.UI;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailEditTextFragment extends Fragment {


    private ImageView btn_clear;
    private EditText inputEmail;
    private TextView tv_email;
    public EmailEditTextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_email_edit_text, container, false);
        btn_clear=itemView.findViewById(R.id.fragment_icon_delete_text);
        inputEmail=itemView.findViewById(R.id.fragment_email);
        tv_email=itemView.findViewById(R.id.fragment_tv_email_text);
        setClearButtonListener();
        setInputEmailListener();

        return itemView;
    }
    private void setInputEmailListener() {
        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    btn_clear.setVisibility(View.VISIBLE);
                }
                else{
                    btn_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void setClearButtonListener() {
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputEmail.setText("");
                btn_clear.setVisibility(View.GONE);
                isSendSuccessful(true);
            }
        });

    }
    public void isSendSuccessful(boolean attempt){
        if(attempt){
            tv_email.setTextColor(getResources().getColor(R.color.color_light_blue));
            inputEmail.setBackground(getResources().getDrawable(R.drawable.et_custom_26a69a_color));
        }
        else{
            tv_email.setTextColor(getResources().getColor(R.color.color_red));
            inputEmail.setBackground(getResources().getDrawable(R.drawable.et_custom_red_color));
        }
    }
    public boolean isEmailAddressValid(){
        final String emailAddress = inputEmail.getText().toString().trim();
        return Utils.isEmailValid(emailAddress);
    }
    public String getEmailAddress(){
        return inputEmail.getText().toString().trim();
    }

}
