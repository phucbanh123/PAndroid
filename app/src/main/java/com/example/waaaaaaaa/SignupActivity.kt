package com.example.waaaaaaaa

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.signup_app)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etEmail  = findViewById<TextInputEditText>(R.id.signup_ipemail)
        val etPass   = findViewById<TextInputEditText>(R.id.signup_ippass)
        val etCfPass = findViewById<TextInputEditText>(R.id.signup_ipcfpass)
        val btnSignup = findViewById<MaterialButton>(R.id.btn_signup)

        btnSignup.setOnClickListener {
            val email  = etEmail.text.toString().trim()
            val pass   = etPass.text.toString()
            val cfPass = etCfPass.text.toString()
            when {
                email.isEmpty() || pass.isEmpty() || cfPass.isEmpty() -> {
                    Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                pass.length < 6 -> {
                    Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                pass != cfPass -> {
                    Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            lifecycleScope.launch {
                try {
                    val username = email.substringBefore("@")
                    val response = RetrofitClient.apiService.signup(
                        SignupRequest(username = username, password = pass, email = email)
                    )
                    if (response.id != null) {
                        Toast.makeText(
                            this@SignupActivity,
                            "Đăng ký thành công! Hãy đăng nhập.",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@SignupActivity,
                        "Đăng ký thất bại: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}