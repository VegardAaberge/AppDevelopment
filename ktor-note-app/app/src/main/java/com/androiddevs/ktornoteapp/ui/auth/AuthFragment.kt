package com.androiddevs.ktornoteapp.ui.auth

import android.content.SharedPreferences
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.androiddevs.ktornoteapp.R
import com.androiddevs.ktornoteapp.data.remote.BasicAuthInterceptor
import com.androiddevs.ktornoteapp.other.Constants.KEY_LOGGED_IN_EMAIL
import com.androiddevs.ktornoteapp.other.Constants.KEY_PASSWORD
import com.androiddevs.ktornoteapp.other.Constants.NO_EMAIL
import com.androiddevs.ktornoteapp.other.Constants.NO_PASSWORD
import com.androiddevs.ktornoteapp.other.Resource
import com.androiddevs.ktornoteapp.other.Status
import com.androiddevs.ktornoteapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var basicAuthInterceptor: BasicAuthInterceptor

    private var currentEmail: String? = null
    private var currentPassword: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isLoggedIn()){
            authenticateApi()
            redirectLogin()
        }

        requireActivity().requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
        subscribeToObservers()

        btnRegister.setOnClickListener {
            val email = etRegisterEmail.text.toString()
            val password = etRegisterPassword.text.toString()
            val confirmedPassword = etRegisterPasswordConfirm.text.toString()
            viewModel.register(email, password, confirmedPassword)
        }

        btnLogin.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()
            currentEmail = email
            currentPassword = password
            viewModel.login(email, password)
        }
    }

    private fun subscribeToObservers() {
        subscribeToObserver(viewModel.loginStatus){ result ->
            showSnackbar(result.data ?: "Successfully logged in")

            sharedPref.edit {
                putString(KEY_LOGGED_IN_EMAIL, currentEmail)
                putString(KEY_PASSWORD, currentPassword)
                apply()
            }

            authenticateApi()

            redirectLogin()
        }
        subscribeToObserver(viewModel.registerStatus){ result ->
            showSnackbar(result.data ?: "Successfully registered an account")
        }
    }

    private fun authenticateApi()
    {
        basicAuthInterceptor.email = currentEmail ?: ""
        basicAuthInterceptor.password = currentPassword ?: ""
    }

    private fun redirectLogin() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.authFragment, true)
            .build()
        findNavController().navigate(
            AuthFragmentDirections.actionAuthFragmentToNotesFragment(),
            navOptions
        )
    }

    private fun isLoggedIn() : Boolean {
        currentEmail = sharedPref.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL) ?: NO_EMAIL
        currentPassword = sharedPref.getString(KEY_PASSWORD, NO_PASSWORD) ?: NO_PASSWORD
        return currentEmail != NO_EMAIL && currentPassword != NO_PASSWORD
    }


    private fun subscribeToObserver(
        liveDataItem: LiveData<Resource<String>>,
        successAction: (Resource<String>) -> Unit,
    ){
        liveDataItem.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    registerProgressBar.visibility = View.GONE
                    successAction(result)
                }
                Status.ERROR -> {
                    registerProgressBar.visibility = View.GONE
                    showSnackbar(result.message ?: "An unknown error occurred")
                }
                Status.LOADING -> {
                    registerProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}