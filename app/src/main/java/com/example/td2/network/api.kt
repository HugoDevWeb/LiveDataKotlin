package com.example.td2.network

import android.content.Context
import com.example.td2.LoginForm
import com.example.td2.SignupForm
import com.example.td2.TaskService
import com.example.td2.TokenResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


class Api(private val context: Context) {
    companion object{
        private const val BASE_URL = "https://android-tasks-api.herokuapp.com/api/"
        const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1NSwiZXhwIjoxNjA4MjExNjQzfQ.sVppuez0fq_c3h-3wuBNJntA3rXHQNFW5aqhnxYutpY"
        lateinit var INSTANCE: Api
    }


    private val moshi = Moshi.Builder().build()

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor{
                chain -> val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Baerer $TOKEN").build()
                chain.proceed(newRequest)
            } .build()


    }



    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()



    val userService: UserService by lazy { retrofit.create(UserService::class.java) }
    val taskService: TaskService by lazy { retrofit.create(TaskService::class.java) }


}


interface UserService{
    @GET("users/info")
    suspend fun getInfo() : Response<UserInfo>

    @Multipart
    @PATCH("users/update_avatar")
    suspend fun updateAvatar(@Part avatar: MultipartBody.Part): Response<UserInfo>

    @PATCH("users")
    suspend fun update(@Body user: UserInfo): Response<UserInfo>

    @POST("users/login")
    suspend fun login(@Body user: LoginForm): Response<TokenResponse>

    @POST("users/sign_up")
    suspend fun signUp(@Body user: SignupForm): Response<TokenResponse>
}

