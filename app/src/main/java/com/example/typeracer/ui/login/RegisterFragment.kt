package com.example.typeracer.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.typeracer.R
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.data_repository.response.ResponseStatus
import com.example.typeracer.data_repository.viewmodel.UserViewModel
import com.example.typeracer.databinding.FragmentRegisterBinding
import com.example.typeracer.ui.activity.LoginActivity
import com.example.typeracer.ui.activity.MainActivity
import com.example.typeracer.utils.Utils
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val userViewModel: UserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForLogin()
        listenForRegister()
    }

    private fun listenForRegister() {
        binding.btnRegister.setOnClickListener{
            val email = binding.email.text.toString()
            val name = binding.name.text.toString()
            val password = binding.password.toString()
            var isValid=true
            if(email.isNullOrEmpty() || !Utils.isEmailValid(email)){
                binding.email.error="Please insert a valid email!"
                isValid=false
            }
            if(name.isNullOrEmpty()){
                binding.name.error="Please insert a name!"
                isValid=false
            }
            if(password.isNullOrEmpty() || password.length<8){
                binding.password.error="Password too short!"
                isValid=false
            }
            if(isValid){
                userViewModel.createUserByBasic(email,name,password)
                    .observe(viewLifecycleOwner,getUserObserver())
            }
        }
    }

    private fun getUserObserver(): Observer<ResponseData<Boolean>> {
        return Observer<ResponseData<Boolean>> { response ->
            if(response.status == ResponseStatus.Success){
                (activity as LoginActivity).startMainActivity()
            }else{
                Toast.makeText(requireActivity(),response.errorMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun listenForLogin() {
        binding.loginButtonLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }


}