package com.example.td2

import com.example.td2.network.Api
import com.example.td2.network.UserInfo
import okhttp3.MultipartBody

class UserInfoRepository {


    private val userService = Api.INSTANCE.userService

    suspend fun loadInfos(): UserInfo? {
        val userResponse = userService.getInfo()
        return if (userResponse.isSuccessful) userResponse.body() else null
    }


    suspend fun updateAvatar(avatar: MultipartBody.Part): UserInfo? {
        val userReponse = userService.updateAvatar(avatar)
        return userReponse.body()
    }

    suspend fun update(userInfo: UserInfo): UserInfo? {
        val userResponse = userService.update(userInfo)
        return userResponse.body()
    }
}
