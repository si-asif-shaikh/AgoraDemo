package com.example.agorademo.presentation

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.agorademo.R
import com.example.agorademo.databinding.ActivityMainBinding
import com.example.agorademo.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVBActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        binding.tvLog.apply {
            movementMethod = ScrollingMovementMethod()
        }
        initNavigations()
        observer()

    }

    private fun initNavigations(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFrag) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun observer() {

        mainViewModel.toolbarHeading.observe(this, Observer {
            supportActionBar?.setTitle(it)
        })

        mainViewModel.getLogMessage().observe(this, Observer { events ->
            events.getContentIfNotHandled()?.let {
                binding.tvLog.text = mainViewModel.showLog(it,false)
            }
        })
        mainViewModel.loading.observe(this, Observer {
            if(it)
            {
                showLoading()
            }
            else{
                dismissLoading()
            }
        })

        mainViewModel.agoraConnectionListener().observe(this, Observer {  })

        mainViewModel.agoraMessageListener().observe(this, Observer {  })
    }
}