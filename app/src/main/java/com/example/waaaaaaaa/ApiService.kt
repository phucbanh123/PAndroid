package com.example.waaaaaaaa

import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @GET("products/category/smartphones")
    suspend fun getProducts(): ProductResponse
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
    @POST("users/add")
    suspend fun signup(@Body request: SignupRequest): AuthResponse
}