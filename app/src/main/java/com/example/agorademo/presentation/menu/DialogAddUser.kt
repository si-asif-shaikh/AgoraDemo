package com.example.agorademo.presentation.menu

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import com.example.agorademo.R
import com.example.agorademo.databinding.DialogAddRoomBinding
import com.example.agorademo.databinding.DialogAddUserBinding
import com.example.agorademo.utils.showToast

class DialogAddUser(context: Context): Dialog(context) {

    private var _binding: DialogAddUserBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStyleOnAlertDialog()
    }

    init {
        _binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.dialog_add_user,null,false)
        setContentView(binding.getRoot())
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        manageActions()
    }

    private fun manageActions() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.btnSubmit.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("user_name",getStringFromEdit(binding.etToUsername))
            onDialogActionClickListener?.let {
                if(isValide())
                it.invoke(bundle)
            }
            dismiss()
        }
    }

    private fun isValide(): Boolean {
        if(getStringFromEdit(binding.etToUsername).isEmpty())
        {
            context.showToast("Please Enter User Name");
            return false
        }

        return true
    }

    private fun getStringFromEdit(editText: EditText) = editText.text.toString()


    private fun applyStyleOnAlertDialog() {
        window?.let { window ->
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setDimAmount(0.4f);
//            window.setWindowAnimations(R.style.bg_transparent.xml)
            window.setBackgroundDrawableResource(R.drawable.bg_transparent)
        }
    }


    private var onDialogActionClickListener: ((Bundle) -> Unit)? = null

    public fun setOnDialogActionClickListener(listener: (Bundle) -> Unit){
        onDialogActionClickListener = listener
    }

}