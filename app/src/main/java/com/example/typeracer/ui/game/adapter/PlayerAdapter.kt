package com.example.typeracer.ui.game.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.typeracer.data_repository.model.GamePlayer
import com.example.typeracer.databinding.ItemGamePlayerBinding

class PlayerAdapter(
    private var players : MutableList<GamePlayer>,
    private val context : Context
) : RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {
    private var timeElapsed: Int = 1
    class PlayerHolder(val binding: ItemGamePlayerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: GamePlayer){
            binding.player=player
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGamePlayerBinding.inflate(inflater,parent,false)
        return PlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val currentPlayer = players[position]
        holder.bind(currentPlayer)
        Glide
            .with(context)
            .load(currentPlayer.car)
            .centerCrop()
            .into(holder.binding.itemPlayerCar)
        holder.binding.itemPlayerWpm.text = "${(timeElapsed/currentPlayer.wordCount)*60}\nWPM"
    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun update(data : MutableList<GamePlayer>, timeElapsed: Int){
        this.timeElapsed=timeElapsed
        this.players=data
        notifyDataSetChanged()
    }
}