package com.example.waaaaaaaa

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String?,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
data class LoginRequest(
    val username: String,
    val password: String
)

data class SignupRequest(
    val username: String,
    val password: String,
    val email: String
)
data class AuthResponse(
    val id: Int?,
    val accessToken: String?,
    val refreshToken: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val username: String?
)