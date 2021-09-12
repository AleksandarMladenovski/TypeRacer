package com.example.typeracer.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.typeracer.R
import com.example.typeracer.data_repository.response.ResponseData
import com.example.typeracer.data_repository.response.ResponseStatus
import com.example.typeracer.data_repository.viewmodel.UserViewModel
import com.example.typeracer.databinding.FragmentResetPasswordBinding
import com.example.typeracer.ui.activity.LoginActivity
import com.example.typeracer.utils.Validator
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private val userViewModel: UserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForResetClick()
        listenForBackClick()
    }

    private fun listenForBackClick() {
        binding.actionBarIconBack.setOnClickListener{
            (activity as Activity).onBackPressed()
        }
    }

    private fun listenForResetClick() {
        binding.buttonForgotPassSend.setOnClickListener{
            val email = binding.email.text.toString()
            if(!Validator.isEmailValid(email)){
                    userViewModel.resetUserPassword(email)
                        .observe(viewLifecycleOwner,getUserObserver())
                }else{
                binding.email.error="Please insert a valid email!"
            }
        }
    }

    private fun getUserObserver(): Observer<ResponseData<Boolean>> {
        return Observer<ResponseData<Boolean>> { response ->
            if(response.status == ResponseStatus.Success){
                Toast.makeText(requireActivity(),"Success", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireActivity(),response.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}