package com.example.agorademo.presentation.home.userChat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agorademo.business.domain.model.UserData
import com.example.agorademo.business.domain.repository.UserRepository
import com.example.agorademo.data.source.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

    private val _userListing = MutableLiveData<List<UserData>>()
    val userListing : LiveData<List<UserData>> get() = _userListing


    fun getUserList() {
        viewModelScope.launch {
            userRepository
                .getUsersList()
                .collect{ resource ->
                when(resource)
                {
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                    is Resource.Success -> {
                        _loading.value = false
                        val data = resource.data
                        data?.let {
                            _userListing.postValue(it)
                        }
                    }
                    is Resource.Error -> {
                        _loading.value = false

                    }
                }
            }
        }
    }

    fun addUser(userId:String) {
        viewModelScope.launch {
            userRepository.addUser(UserData(userId))
        }
    }
}