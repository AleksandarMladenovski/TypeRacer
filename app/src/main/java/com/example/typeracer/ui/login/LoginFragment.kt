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
import com.example.typeracer.databinding.FragmentLoginBinding
import com.example.typeracer.ui.activity.LoginActivity
import com.example.typeracer.utils.Validator
import org.koin.android.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val userViewModel: UserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenForResetPassword()
        listenForRegister()
        listenForLoginBasic()
        listenForLoginYahoo()
        listenForLoginTwitter()
    }

    private fun listenForLoginYahoo() {
        binding.optionLogInYahoo.setOnClickListener {
            userViewModel.loginWithProvider("yahoo.com", requireActivity())
                .observe(viewLifecycleOwner, getUserObserver())
        }
    }

    private fun listenForLoginTwitter() {
        binding.optionLogInTwitter.setOnClickListener {
            userViewModel.loginWithProvider("twitter.com", requireActivity())
                .observe(viewLifecycleOwner, getUserObserver())
        }
    }

    private fun listenForLoginBasic() {
        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            var isValid = true
            if (!Validator.isEmailValid(email)) {
                binding.email.error = "Please insert a valid email!"
                isValid = false
            }
            if (!Validator.isPasswordValid(password)) {
                binding.password.error = "Password too short!"
                isValid = false
            }
            if (isValid) {
                userViewModel.loginUserByBasic(email, password)
                    .observe(viewLifecycleOwner, getUserObserver())
            }
        }
    }

    private fun getUserObserver(): Observer<ResponseData<Boolean>> {
        return Observer<ResponseData<Boolean>> { response ->
            if (response.status == ResponseStatus.Success) {
                (activity as LoginActivity).startMainActivity()
            } else {
                Toast.makeText(requireActivity(), response.errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun listenForRegister() {
        binding.registerButtonLayout.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun listenForResetPassword() {
        binding.btnResetPassword.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
    }


}