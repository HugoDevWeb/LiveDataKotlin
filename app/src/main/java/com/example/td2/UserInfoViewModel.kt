package com.example.td2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.td2.network.Api
import androidx.lifecycle.viewModelScope
import com.example.td2.network.UserInfo
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UserInfoViewModel: ViewModel() {


    val userLiveData = MutableLiveData<UserInfo>()
    private val repo = UserInfoRepository()

    fun loadInfos(){
        viewModelScope.launch {
            val userInfo = repo.loadInfos()
            userLiveData.postValue(userInfo)
        }
    }

    fun updateAvatar(avatar: MultipartBody.Part)
    {
        viewModelScope.launch {
            val newAvatar = repo.updateAvatar(avatar)
            if (newAvatar != null) {
                userLiveData.postValue(newAvatar)
            }
        }
    }

    fun update(userInfo: UserInfo) {
        viewModelScope.launch {
            val newInfo = repo.update(userInfo)
            if (newInfo != null) {
                userLiveData.postValue(newInfo)
            }
        }
    }

}