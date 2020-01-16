package com.extremedesign.typeracer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.extremedesign.typeracer.model.FriendlyPlayer;
import com.extremedesign.typeracer.model.User;

import java.util.LinkedList;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerInfo> {
    List<FriendlyPlayer> userList;
    Context context;

    public PlayerListAdapter(Context context) {
        this.context = context;
        userList =new LinkedList<>();

    }
    public PlayerListAdapter(Context context,List<FriendlyPlayer> userList) {
        this.userList = userList;
        this.context=context;
    }

    @NonNull
    @Override
    public PlayerInfo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new PlayerInfo(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerInfo holder, int position) {
        int playerPosition=holder.getAdapterPosition();
        FriendlyPlayer user = userList.get(playerPosition);
        holder.playerName.setText(user.getName());
        Glide.with(context).load(user.getPhoto()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addPlayer(FriendlyPlayer user){
        userList.add(user);
        notifyDataSetChanged();
    }

    public void replaceAllPlayers(List<FriendlyPlayer> playerList) {
        userList=playerList;
        notifyDataSetChanged();
    }


    class PlayerInfo extends RecyclerView.ViewHolder {
        private TextView playerName;
        private ImageView image;
        public PlayerInfo(@NonNull View itemView) {
            super(itemView);
            playerName=itemView.findViewById(R.id.playerName);
            image=itemView.findViewById(R.id.item_player_image);
        }
    }

    public List<FriendlyPlayer> getUserList() {
        return userList;
    }

    public void setUserList(List<FriendlyPlayer> userList) {
        this.userList = userList;
    }
}
