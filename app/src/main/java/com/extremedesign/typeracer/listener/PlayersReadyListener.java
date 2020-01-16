package com.extremedesign.typeracer.listener;

import com.extremedesign.typeracer.model.FriendlyPlayer;

import java.util.List;

public interface PlayersReadyListener {
    void onPlayersReady(List<FriendlyPlayer> playerList);
}
