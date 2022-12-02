package com.example.agorademo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agorademo.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel


abstract class BaseViewModel : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    fun emitMessage(message: String) {
        _message.value = Event(message)
    }
}