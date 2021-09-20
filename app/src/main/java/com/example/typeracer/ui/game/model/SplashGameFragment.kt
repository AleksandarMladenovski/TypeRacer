package com.example.typeracer.ui.game.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.typeracer.R
import com.example.typeracer.data_repository.model.GamePlayer
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.data_repository.response.ResponseStatus
import com.example.typeracer.databinding.FragmentSplashGameBinding
import com.example.typeracer.ui.game.viewmodel.SplashGameViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashGameFragment : Fragment() {
    private lateinit var binding: FragmentSplashGameBinding
    private val splashGameViewModel : SplashGameViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllPlayers()
    }

    private fun getAllPlayers() {
        splashGameViewModel.prepareData().observe(viewLifecycleOwner,
            { response ->
                if(response.status == ResponseStatus.Success){
                    navigateToGameFragment(binding.root)
                }
            })
    }

    private fun navigateToGameFragment(view: View){
        Navigation.findNavController(view)
            .navigate(SplashGameFragmentDirections.actionSplashGameFragmentToGameFragment())
    }
}