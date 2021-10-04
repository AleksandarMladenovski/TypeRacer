package com.extremedesign.typeracer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.extremedesign.typeracer.FirebaseRepo
import com.extremedesign.typeracer.R
import com.extremedesign.typeracer.activity.LoginActivity
import com.extremedesign.typeracer.activity.TypeRaceActivity
import com.extremedesign.typeracer.data_repository.repository_typeracer.RepositoryViewModel
import com.extremedesign.typeracer.databinding.FragmentHomeBinding
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private val viewModel: RepositoryViewModel by sharedViewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerSignOut()
        registerStart()
    }

    fun registerSignOut() {
        binding.buttonSignOut.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            LoginManager.getInstance().logOut()
            FirebaseRepo.getGoogleSignInClient(requireActivity()).signOut()
            val newIntent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(newIntent)
            requireActivity().finish()
        }
    }

    fun registerStart() {
        binding.buttonStart.setOnClickListener{
            val intent = Intent(requireActivity(), TypeRaceActivity::class.java)
            startActivity(intent)
        }
    }
}