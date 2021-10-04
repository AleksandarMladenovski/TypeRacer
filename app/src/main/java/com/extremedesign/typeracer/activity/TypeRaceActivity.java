package com.extremedesign.typeracer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.extremedesign.typeracer.fragment.GatherPlayersFragment;
import com.extremedesign.typeracer.fragment.StartGameFragment;
import com.extremedesign.typeracer.R;
import com.extremedesign.typeracer.model.FriendlyPlayer;

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
        return this::startGameFragment;
    }
    private void startGameFragment(List<FriendlyPlayer>playerList){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_TypeRace,new StartGameFragment(playerList)).commit();
    }

}
