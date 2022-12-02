package com.example.agorademo.presentation

import android.Manifest
import android.app.ProgressDialog
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.agorademo.utils.PermissionsManager

open class BaseVBActivity<VB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    /**
     * Check and request permission
     * @param permission
     * @param requestCode
     * @return
     */
     fun checkPermissions(permission: String, requestCode: Int): Boolean {
        if (!PermissionsManager.getInstance().hasPermission(this, permission)) {
            PermissionsManager.getInstance()
                .requestPermissions(this, arrayOf(permission), requestCode)
            return false
        }
        return true
    }


    fun requestPermissions() {
        checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, 110)
    }

    private val progressBar by lazy {
        ProgressDialog(this).apply {
            setTitle("Loading")
        }
    }

    fun showLoading(){
        progressBar.show()
    }

    fun dismissLoading(){
        progressBar.dismiss()
    }

}