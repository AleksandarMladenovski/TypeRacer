package com.extremedesign.typeracer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.extremedesign.typeracer.FirebaseRepo;
import com.extremedesign.typeracer.fragment.GatherPlayersFragment;
import com.extremedesign.typeracer.fragment.StartGameFragment;
import com.extremedesign.typeracer.listener.PlayersReadyListener;
import com.extremedesign.typeracer.model.FriendlyPlayer;
import com.extremedesign.typeracer.model.User;
import com.extremedesign.typeracer.PlayerListAdapter;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.model.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TypeRaceActivity extends AppCompatActivity {
//            FirebaseFirestore db = FirebaseFirestore.getInstance();


//        myRef.child("group").
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_race);
//        database = FirebaseDatabase.getInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_TypeRace,new GatherPlayersFragment(getPlayersReadyListener())).commit();
//
    }
    private PlayersReadyListener getPlayersReadyListener(){
        return new PlayersReadyListener() {
            @Override
            public void onPlayersReady(List<FriendlyPlayer> playerList) {
                      startGameFragment(playerList);
            }
        };
    }
    private void startGameFragment(List<FriendlyPlayer>playerList){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_TypeRace,new StartGameFragment(playerList)).commit();
    }

}
