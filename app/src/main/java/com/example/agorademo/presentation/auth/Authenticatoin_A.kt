package com.example.agorademo.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.agorademo.R
import com.example.agorademo.databinding.ActivityAuthenticatoinBinding
import com.example.agorademo.presentation.BaseVBActivity
import com.example.agorademo.presentation.MainActivity
import com.example.agorademo.utils.*
import com.example.agorademo.utils.TextConstants.USER_LOGIN_SUCCESSFULLY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Authenticatoin_A : BaseVBActivity<ActivityAuthenticatoinBinding>(R.layout.activity_authenticatoin) {

    private val authViewModel : AuthViewModel by viewModels()

    @Inject
    lateinit var sessionStoreManager: SessionStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAuthView()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            val isLoggedIn = sessionStoreManager.isLoggedIn()
            if (isLoggedIn)
                openMainActivity()
        }
    }

    private fun initAuthView() {

        binding.btnRegister.setOnClickListener { registerUser() }
        binding.btnLogin.setOnClickListener { loginUser() }

        observes()
    }

    private fun observes() {
        authViewModel.loading.observe(this, Observer {
            binding.progressBar.showOrhide { it }
        })
        authViewModel.successLogin.observe(this, Observer { res ->

            res.getContentIfNotHandled()?.let {
                if(it.equals(USER_LOGIN_SUCCESSFULLY))
                {
                    showToast(it)
                    openMainActivity()
                }
            }

        })

        authViewModel.failedLoginMessage.observe(this, Observer { res ->
            res.getContentIfNotHandled()?.let {
                showToast(it)
            }
        })
    }

    private fun registerUser() {
        val userName = binding.edtEmail.text.toString()
        val userPassword = binding.edtPass.text.toString()

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            showToast(getString(R.string.username_or_pwd_miss))
            return
        }

        authViewModel.registerUser(userName,userPassword)
    }

    private fun loginUser(){
        val userName = binding.edtEmail.text.toString()
        val userPassword = binding.edtPass.text.toString()

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            showToast(getString(R.string.username_or_pwd_miss))
            return
        }

        authViewModel.registerUser(userName,userPassword)
    }

    private fun openMainActivity() {
        startActivity(Intent(application,MainActivity::class.java))
        finish()
    }
}