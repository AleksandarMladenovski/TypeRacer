package com.extremedesign.typeracer.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.listener.PlayerDisplayClose;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayPlayerFragment extends Fragment {
    private User user;
    private PlayerDisplayClose listener;
    public DisplayPlayerFragment() {
        // Required empty public constructor
    }

    public DisplayPlayerFragment(User user, PlayerDisplayClose listener) {
        this.user = user;
        this.listener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView =inflater.inflate(R.layout.fragment_display_player, container, false);
        TextView name=itemView.findViewById(R.id.user_name);
        TextView email=itemView.findViewById(R.id.user_email);
        ImageView image=itemView.findViewById(R.id.user_photo_image);

        name.setText(user.getUserInfo().getName());
        email.setText(user.getUserInfo().getEmail());
        Glide.with(getContext()).load(user.getUserInfo().getPhotoUrl()).into(image);

        return itemView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener.onPlayerDisplayClosed();
    }
}

