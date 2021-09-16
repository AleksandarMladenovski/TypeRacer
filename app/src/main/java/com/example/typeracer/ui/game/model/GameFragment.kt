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
import com.example.typeracer.R
import com.example.typeracer.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val text = "yes i will go now in a minute of your time"
    private var textCounter = 0
    private var gameTextMoveSize: Float = 0f
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
        binding.itemPlayerTrack.doOnLayout {
             gameTextMoveSize = binding.itemPlayerTrack.width.toFloat() / text.split(" ").size.toFloat()
        }

        binding.gameText.addTextChangedListener(object : TextWatcher{
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
                            binding.gameText.setText("")
                            textCounter++
                            ObjectAnimator.ofFloat(binding.itemPlayerCar, "translationX", gameTextMoveSize*textCounter).apply {
                                duration = 1
                                start()
                            }
                        }
                    }


            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

}