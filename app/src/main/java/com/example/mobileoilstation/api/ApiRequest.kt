package com.example.mobileoilstation.api

import com.example.mobileoilstation.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRequest {
    @GET("/")
    fun getMain(): Message

    @GET("/api/signin")
    fun logIn(): Message

    @POST("/api/signup/user")
    fun createUser(@Body body: User): Message

    @POST("/api/signup/company")
    fun createCompany(@Body body: Company): Message

    @POST("/api/signout")
    fun logOut(): Message

    @GET("/api/profile")
    fun getProfileInfo(): User

    @GET("/api/car")
    fun getCars(): Call<MutableList<Car>>

    @POST("/api/car")
    fun createCar(@Body body: Car): Message

    @GET("/api/orders")
    fun getOrders(): Call<MutableList<Order>>

    @POST("/api/orders")
    fun postOrder(@Body body: Order): Message

    @GET("/api/oil")
    fun getOils(): Call<MutableList<Oil>>
}