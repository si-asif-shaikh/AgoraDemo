package com.example.agorademo.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.util.Log
import android.widget.Toast

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun Context.showToast(messsage: String){
        Toast.makeText(this,messsage,Toast.LENGTH_LONG).show()
}

fun Activity.LOGD(messsage: String){
        Log.d(""+this.componentName,""+messsage)
}