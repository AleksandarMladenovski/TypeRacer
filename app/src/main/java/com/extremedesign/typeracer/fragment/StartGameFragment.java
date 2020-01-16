package com.extremedesign.typeracer.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.extremedesign.typeracer.PlayerListAdapter;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.model.FriendlyPlayer;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartGameFragment extends Fragment {

    List<FriendlyPlayer> playerList;
    PlayerListAdapter listAdapter;
    int correctWordCounter = 0;
    public StartGameFragment() {
        // Required empty public constructor
    }

    public StartGameFragment(List<FriendlyPlayer> playerList) {
        this.playerList = playerList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_start_game, container, false);
        RecyclerView recyclerView = itemView.findViewById(R.id.recyclerView_GameStarted);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new PlayerListAdapter(getContext(),playerList);
        recyclerView.setAdapter(listAdapter);
        TextView post = itemView.findViewById(R.id.textView_textToBeWritten);
        post.setText("Text from network!");
        final String[] words = post.getText().toString().split(" ");
        final EditText editText = itemView.findViewById(R.id.editText_write_Text);
        editText.setTextColor(getResources().getColor(R.color.bg_login));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText.setTextColor(getResources().getColor(R.color.bg_login));
                char[] wordCharacters = words[correctWordCounter].toCharArray();
                for (int index = 0; index < charSequence.length() - 1; index++) {
                    if (charSequence.charAt(index) != wordCharacters[index]) {
                        editText.setTextColor(getResources().getColor(R.color.colorAccent));
                        return;
                    }
                    if (charSequence.charAt(charSequence.length() - 1) == ' ') {
                                correctWordCounter++;
                                editText.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return itemView;
    }

}
