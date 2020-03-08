package com.extremedesign.typeracer.fragment.UI;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.extremedesign.typeracer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomActionBarFragment extends Fragment {
    private ImageView btn_back;
    private String text;
    public CustomActionBarFragment() {
        // Required empty public constructor
    }
    public CustomActionBarFragment(String text){
        this.text=text;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_custom_action_bar, container, false);
        btn_back=itemView.findViewById(R.id.action_bar_icon_back);
        TextView tv_text=itemView.findViewById(R.id.action_bar_text);
        tv_text.setText(text);
        setBackButtonListener();
        return itemView;
    }

    private void setBackButtonListener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }
}
