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

    @POST("/api/signup/company")
    fun createCompany(@Body body: Company): Call<Message>

    @POST("/api/signout")
    fun logOut(): Call<Message>

    @GET("/api/profile")
    fun getProfileInfo(): Call<User>

    @GET("/api/car")
    fun getCars(): Call<MutableList<Car>>

    @POST("/api/car")
    fun createCar(@Body body: Car): Call<Message>

    @GET("/api/orders")
    fun getOrders(): Call<MutableList<Order>>

    @POST("/api/orders")
    fun postOrder(@Body body: Order): Call<Message>

    @GET("/api/oil")
    fun getOils(): Call<MutableList<Oil>>
}