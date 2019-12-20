package com.example.td2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.td2.network.Api
import androidx.lifecycle.viewModelScope
import com.example.td2.network.UserInfo
import kotlinx.android.synthetic.main.new_user_info_act.*
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

    fun update(firstname: String, lastname: String, email: String) {
        viewModelScope.launch {
            val userInfo = UserInfo(null, firstname,lastname , userLiveData.value?.avatar )
            val newInfo = repo.update(userInfo)
            if (newInfo != null) {
                userLiveData.postValue(newInfo)
            }
        }
    }

}