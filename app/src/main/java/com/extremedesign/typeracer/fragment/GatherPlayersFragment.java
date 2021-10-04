package com.extremedesign.typeracer.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.extremedesign.typeracer.PlayerListAdapter;
import com.extremedesign.typeracer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GatherPlayersFragment extends Fragment {

    private DatabaseReference databaseReference;
    private ArrayList<String> users;
    private PlayerListAdapter listAdapter;
    private PlayersReadyListener listener;
    public GatherPlayersFragment() {

    }

    public GatherPlayersFragment(PlayersReadyListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_gather_players, container, false);
//        databaseReference = FirebaseRepo.getINSTANCE().getDatabaseInstance();
        databaseReference = null;
        users=new ArrayList<>();

        RecyclerView recyclerView=rootView.findViewById(R.id.recyclerViewPlayers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter=new PlayerListAdapter(getContext());
        recyclerView.setAdapter(listAdapter);


//        User user=FirebaseRepo.getINSTANCE().getCurrentUser();
        User user=null;
        FriendlyPlayer currUser=new FriendlyPlayer(user.getName(),user.getPhotoName());

        listAdapter.addPlayer(currUser);
        users.add(user.getUid());

        searchForAGame();


        return rootView;
    }

    private void searchForAGame() {
        databaseReference.child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count=1;
                for(DataSnapshot group:dataSnapshot.getChildren()){
                    count++;
                    int groupCount=Integer.parseInt(String.valueOf(group.child("timer").getValue()));
                    if(groupCount<4){
                        groupCount++;
                        addPlayerToGroup(group.getKey(),String.valueOf(groupCount));
                        listenForOtherPlayers(group.getKey());
                        return;
                    }
                }
                //no GRoups found
                //create a group
                String groupName="group " + count;
                createGroup(groupName);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void listenForOtherPlayers(String key) {

        databaseReference.child("groups").child(key).child("users").addValueEventListener(getPlayerListener());

        databaseReference.child("groups").child(key).child("count").addValueEventListener(getCountListener());
//
    }
    private void createGroup(String groupName) {
        addPlayerToGroup(groupName,"1");
        listenForOtherPlayers(groupName);
//        dbGroup.child("information").child("count").addValueEventListener(getCountListener(groupName));
    }
    private ValueEventListener getCountListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(String.valueOf(dataSnapshot.getValue()).equals("4")){
                    listener.onPlayersReady(listAdapter.getUserList());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private ValueEventListener getPlayerListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot player:dataSnapshot.getChildren()){
                    String player1 = (String) player.child("uid").getValue();
                    if(users.contains(player1)){
                        continue;
                    }
                    users.add(player1);
                    databaseReference.child("users").child(player1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            FriendlyPlayer userInfo= dataSnapshot.getValue(FriendlyPlayer.class);
                            listAdapter.addPlayer(userInfo);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Log.e("tastsatdjostjio", player1.toString());


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("tagErrortag",databaseError.toString());
            }
        };
    }
    private void addPlayerToGroup(String group,String groupCount){
        //        String uid=FirebaseRepo.getINSTANCE().getCurrentUser().getUid();
        String uid=null;
        databaseReference.child("groups").child(group)
                .child("count").setValue(String.valueOf(groupCount));
        databaseReference.child("groups").child(group)
                .child("users").child("user "+ groupCount).child("uid").setValue(uid);
        databaseReference.child("groups").child(group)
                .child("users").child("user "+ groupCount).child("wpm").setValue("0");
    }


}
