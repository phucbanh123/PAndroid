package com.example.waaaaaaaa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_app)

        val btnLogin = findViewById<MaterialButton>(R.id.btn_login)
        val edtUser  = findViewById<TextInputEditText>(R.id.login_ipemail)
        val edtPass  = findViewById<TextInputEditText>(R.id.login_ippass)

        btnLogin.setOnClickListener {
            val user = edtUser.text.toString().trim()
            val pass = edtPass.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.login(LoginRequest(user, pass))
                    if (response.accessToken != null) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Chào ${response.firstName}!",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@LoginActivity, HomeProductActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Sai tài khoản hoặc mật khẩu!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Sai tài khoản hoặc mật khẩu!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}