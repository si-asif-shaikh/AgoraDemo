package com.example.agorademo.presentation

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.agorademo.common.Inflate

open class BaseVBFragment<VB:ViewDataBinding>(private val inflate: Inflate<VB>) :
    Fragment() {

     val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater,container,false)
        _binding?.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    open fun finish() {
        findNavController().popBackStack()
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private val progressBar by lazy {
        ProgressDialog(this.requireContext()).apply {
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