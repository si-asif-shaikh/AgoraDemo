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
import com.example.agorademo.utils.showToast

class DialogAddRoom(context: Context): Dialog(context) {

    private var _binding: DialogAddRoomBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyStyleOnAlertDialog()
    }

    init {
        _binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.dialog_add_room,null,false)
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
            if(!isValide())
                return@setOnClickListener

            val bundle = Bundle()
            bundle.putString("subject",getStringFromEdit(binding.etSubject))
            bundle.putString("description",getStringFromEdit(binding.etDescription))
            bundle.putString("welcomMessage",getStringFromEdit(binding.etWelcomeMessage))
            bundle.putInt("maxUserCount",getStringFromEdit(binding.etMaxMembers).toInt())
            bundle.putStringArrayList("members", arrayListOf(getStringFromEdit(binding.etMemberId)))
            onDialogActionClickListener?.let {
                it.invoke(bundle)
            }
            dismiss()
        }
    }

    private fun isValide(): Boolean {
        if(getStringFromEdit(binding.etSubject).isEmpty())
        {
            context.showToast("Please Enter Subject");
            return false
        }
        else if(getStringFromEdit(binding.etDescription).isEmpty())
        {
            context.showToast("Please Enter Description");
            return false
        }
        else if(getStringFromEdit(binding.etWelcomeMessage).isEmpty())
        {
            context.showToast("Please Enter Welcome");
            return false
        }
        else if(getStringFromEdit(binding.etMaxMembers).isEmpty())
        {
            context.showToast("Please Enter Max Members");
            return false
        }
        else if(getStringFromEdit(binding.etMemberId).isEmpty())
        {
            context.showToast("Please Enter Member id");
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