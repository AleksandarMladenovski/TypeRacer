package com.example.typeracer.ui.game.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.typeracer.data_repository.model.GamePlayer
import com.example.typeracer.databinding.ItemGamePlayerBinding
import kotlinx.android.synthetic.main.item_game_player.view.*

class PlayerAdapter(
    private var players : MutableList<GamePlayer>,
    private val context : Context,
    private val text : String
) : RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {
    private var timeElapsed: Int = 1
    private var gameTextMoveSize = 0f
    class PlayerHolder(val binding: ItemGamePlayerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(player: GamePlayer){
            binding.player=player
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGamePlayerBinding.inflate(inflater,parent,false)
        binding.root.doOnLayout {
            gameTextMoveSize = it.item_player_track.width.toFloat() / text.split(" ").size.toFloat()
        }
        return PlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val currentPlayer = players[position]
        holder.bind(currentPlayer)
        Glide
            .with(context)
            .load(Uri.parse(currentPlayer.car))
            .into(holder.binding.itemPlayerCar)

        ObjectAnimator.ofFloat(holder.binding.itemPlayerCar, "translationX", gameTextMoveSize*currentPlayer.wordCount).apply {
                                duration = 1
                                start()
                            }
        if(currentPlayer.wordCount == 0 ){
            holder.binding.itemPlayerWpm.text = "0\nWPM"
        }else{
            holder.binding.itemPlayerWpm.text = "${(timeElapsed/currentPlayer.wordCount)*60}\nWPM"
        }

    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun update(data : MutableList<GamePlayer>, timeElapsed: Int){
        this.timeElapsed=timeElapsed
        this.players=data
        notifyDataSetChanged()
    }

    fun updatePlayer(id : String, wordCount: Int, timeElapsed: Int){
        this.timeElapsed=timeElapsed
        val player = players.find { it.id == id }
        player?.wordCount = wordCount
        notifyItemChanged(players.indexOf(player))
    }
}