package com.example.waaaaaaaa

// Dữ liệu gửi lên để đăng nhập
data class LoginRequest(
    val username: String, // DummyJSON dùng username thay vì email để test
    val password: String
)

// Dữ liệu gửi lên để đăng ký
data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

// Dữ liệu nhận về sau khi đăng nhập/đăng ký thành công
data class AuthResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token: String? // Chuỗi mã hóa để xác thực các bước sau
)