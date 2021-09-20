package com.example.typeracer.ui.game.model

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.typeracer.R
import com.example.typeracer.data_repository.FirebaseNetwork
import com.example.typeracer.data_repository.model.GamePlayer
import com.example.typeracer.data_repository.response.ResponseStatus
import com.example.typeracer.databinding.FragmentGameBinding
import com.example.typeracer.ui.game.adapter.PlayerAdapter
import com.example.typeracer.ui.game.viewmodel.GameViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val text = "yes i will go now in a minute of your time"
    private var textCounter = 0
    private var gameTextMoveSize: Float = 0f
    private val gameViewModel: GameViewModel by viewModel()
    private lateinit var playerAdapter: PlayerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        getAllPlayers()

        binding.gameInputField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val currentWord = text.split(" ")[textCounter]
                    val wordCharEnd=p0.toString().endsWith(" ")
                    val inputWord = p0.toString().trim()
                    if(inputWord.length>currentWord.length){
                            //TODO DISPLAY RED
                    }else{
                        for((index,input) in inputWord.toCharArray().withIndex()){
                            if(input != currentWord[index]){
                                //TODO DISPLAY RED
                                return
                            }
                        }
                        if(wordCharEnd){
                            binding.gameInputField.setText("")
                            textCounter++
                            FirebaseNetwork.getFirebaseAuth().currentUser?.let {
                                playerAdapter.updatePlayer(
                                    it.uid,textCounter,1)
                            }
//
                        }
                    }


            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    private fun initRecycler(){
        playerAdapter = PlayerAdapter(mutableListOf(),requireContext(),text)
        binding.recyclerViewGamePlayers.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            if (0 == binding.recyclerViewGamePlayers.itemDecorationCount) {
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            adapter = playerAdapter
        }
    }

    private fun getAllPlayers() {
        gameViewModel.getAllPlayers().observe(viewLifecycleOwner,{ response ->
            if(response.status == ResponseStatus.Success){
                    playerAdapter.update(response.data as MutableList<GamePlayer>,1)
            }else{

            }
        })
    }

}