package com.example.mobileoilstation.api

import androidx.lifecycle.Observer
import com.example.mobileoilstation.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiRequest {
    @GET("/")
    fun getMain(): Call<Message>

    @POST("/api/signin")
    fun logIn(@Body body: Login): Call<Token>

    @POST("/api/signup/user")
    fun createUser(@Body body: User): Call<Message>

    @Headers("User-Agent: Mozilla/5.0")
    @POST("/api/signup/company")
    fun createCompany(@Body body: Company): Call<Message>

    @Headers("User-Agent: Mozilla/5.0")
    @POST("/api/signout")
    fun logOut(): Call<Message>

    @Headers("User-Agent: Mozilla/5.0")
    @GET("/api/profile")
    fun getProfileInfo(): Call<User>

    @Headers("User-Agent: Mozilla/5.0")
    @GET("/api/car")
    fun getCars(): Call<MutableList<Car>>

    @Headers("User-Agent: Mozilla/5.0")
    @POST("/api/car")
    fun createCar(@Body body: Car): Call<Message>

    @Headers("User-Agent: Mozilla/5.0")
    @GET("/api/orders")
    fun getOrders(): Call<MutableList<Order>>

    @Headers("User-Agent: Mozilla/5.0")
    @POST("/api/orders")
    fun postOrder(@Body body: Order): Call<Message>

    @Headers("User-Agent: Mozilla/5.0")
    @GET("/api/oil")
    fun getOils(): Call<MutableList<Oil>>
}